package dev.definedentity.astralgenerators.blocks.ess

import dev.definedentity.astralgenerators.blockentities.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState

class ESSCasing(properties: Properties) : BaseEntityBlock(properties) {

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun newBlockEntity(
        pos: BlockPos,
        state: BlockState
    ): BlockEntity {
        return ESSBlocks.CASING_ENTITY.create(pos, state)
    }
}