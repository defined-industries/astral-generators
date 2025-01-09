package dev.definedentity.astralgenerators.blockentities.machines

import dev.definedentity.astralgenerators.blocks.machines.AbstractMachineBlock
import dev.definedentity.astralgenerators.utils.AGContainer
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerChunkCache
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.ContainerHelper
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.reborn.energy.api.EnergyStorage
import team.reborn.energy.api.EnergyStorageUtil
import team.reborn.energy.api.base.SimpleSidedEnergyContainer

abstract class AbstractMachineBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state), ExtendedScreenHandlerFactory, AGContainer, WorldlyContainer {
    var inventory: NonNullList<ItemStack>
    val energyStorage: SimpleSidedEnergyContainer = object : SimpleSidedEnergyContainer() {
        override fun getCapacity(): Long {
            return getMaxGeneration();
        }

        override fun getMaxInsert(side: Direction?): Long {
            return getMaxEnergyInsert();
        }

        override fun getMaxExtract(side: Direction?): Long {
            return getMaxEnergyInsert();
        }

        override fun onFinalCommit() {
            setChanged()
        }
    }

    init {
        inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY)
    }

    abstract fun tick()

    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu? {
        return null
    }

    open fun usesEnergy(): Boolean {
        return false
    }

    open fun getMaxGeneration(): Long {
        return 0
    }

    open fun getEnergyPerTick(): Long {
        return 0
    }

    fun getMaxEnergyInsert(): Long {
        return 0
    }

    fun getMaxEnergyExtract(): Long {
        return 0
    }

    open fun getInventorySize(): Int {
        return 0
    }

    fun cumulateEnergy() {
        if (energyStorage.amount < getMaxGeneration()) {
            energyStorage.amount += getEnergyPerTick()
        } else if (energyStorage.amount > getMaxGeneration()) {
            energyStorage.amount = getMaxGeneration()
        }
        setChanged()
    }

    fun drainEnergy(): Boolean {
        return drainEnergy(getEnergyPerTick())
    }

    fun drainEnergy(amount: Long): Boolean {
        if (level?.isClientSide == false) {
            if (energyStorage.amount - amount > 0) {
                energyStorage.amount -= amount
                setChanged()
                return true
            } else {
                energyStorage.amount = 0
                setChanged()
                return false
            }
        }
        return false
    }

    fun canDrainEnergy() {
        return canDrainEnergy(getEnergyPerTick())
    }

    fun canDrainEnergy(amount: Long) {
        energyStorage.amount - amount > 0
    }

    // Distribute energy around
    fun energyOut() {
        if (usesEnergy()) {
            for (direction in Direction.entries) {
                EnergyStorageUtil.move(
                    getSideEnergyStorage(direction), EnergyStorage.SIDED.find(level, blockPos, direction.opposite),
                    Long.MAX_VALUE, null
                )
            }
        }
    }

    fun getSideEnergyStorage(side: Direction?): EnergyStorage {
        return energyStorage.getSideStorage(side)
    }

    fun getEnergy(): Long {
        return energyStorage.amount
    }

    fun hasEnergy(): Boolean {
        return usesEnergy() && getEnergy() > getEnergyPerTick()
    }

    override fun getDisplayName(): Component? {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        buf.writeBlockPos(blockPos)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (getInventorySize() > 0) {
            ContainerHelper.loadAllItems(tag, inventory)
        }
        if (usesEnergy()) {
            energyStorage.amount = tag.getLong("energy")
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        if (getInventorySize() > 0) {
            ContainerHelper.saveAllItems(tag, inventory)
        }
        if (usesEnergy()) {
            tag.putLong("energy", getEnergy())
        }
    }

    override fun setChanged() {
        super<BlockEntity>.setChanged()

        if (level is ServerLevel) {
            (level?.chunkSource as ServerChunkCache).blockChanged(blockPos)
        }
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return IntArray(getItems().size) { it }
    }

    override fun canPlaceItemThroughFace(index: Int, itemStack: ItemStack, direction: Direction?): Boolean {
        val item = this.getItem(index)

        return item.isEmpty || (item.`is`(item.item) && item.count <= item.maxStackSize)
    }

    override fun canTakeItemThroughFace(index: Int, stack: ItemStack, direction: Direction): Boolean {
        return true
    }

    override fun getItems(): NonNullList<ItemStack> {
        return inventory
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(): CompoundTag {
        return saveWithoutMetadata()
    }
}
