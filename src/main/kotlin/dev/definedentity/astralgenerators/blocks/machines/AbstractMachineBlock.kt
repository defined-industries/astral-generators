package dev.definedentity.astralgenerators.blocks.machines

import dev.definedentity.astralgenerators.blockentities.machines.AbstractMachineBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.world.ContainerHelper
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.material.PushReaction
import net.minecraft.world.phys.BlockHitResult
import java.util.function.ToIntFunction

abstract class AbstractMachineBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { entityWorld, pos, entityState, blockEntity ->
            if (blockEntity is AbstractMachineBlockEntity) {
                blockEntity.tick()
            }
        }
    }

    open fun useFacing(): Boolean {
        return false;
    }

    open fun useActive(): Boolean {
        return false;
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult
    ): InteractionResult? {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) is AbstractMachineBlockEntity) {
                player.openMenu(state.getMenuProvider(level, pos))
            }
        }
        return InteractionResult.SUCCESS
    }

    fun removeOutput(): Boolean {
        return false
    }

    override fun getPistonPushReaction(state: BlockState): PushReaction? {
        return PushReaction.BLOCK
    }

    override fun hasAnalogOutputSignal(state: BlockState): Boolean {
        return true
    }

    fun doRedstoneCheck(): Boolean {
        return true;
    }

    override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos): Int {
        val blockEntity = level.getBlockEntity(pos)

        return if (blockEntity is AbstractMachineBlockEntity) {
            AbstractContainerMenu.getRedstoneSignalFromBlockEntity(blockEntity)
        } else {
            0
        }
    }
}
