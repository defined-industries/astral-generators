package dev.definedentity.astralgenerators.blockentities.controllers

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class SteamTurbineControllerEntity(type: BlockEntityType<SteamTurbineControllerEntity>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state), MenuProvider {
    override fun getDisplayName(): Component {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu? {
        return null
    }
}
