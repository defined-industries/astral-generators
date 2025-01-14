package dev.definedentity.astralgenerators.blocks.machines

import dev.definedentity.astralgenerators.blockentities.AGBlockEntities
import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult

class Assembler(properties: Properties) : BaseEntityBlock(properties) {
    companion object {
        val FACING = BlockStateProperties.HORIZONTAL_FACING
        val ACTIVE = BooleanProperty.create("active")
    }

    init {
        registerDefaultState(with(defaultBlockState()) { setValue(FACING, Direction.NORTH) })
        registerDefaultState(with(defaultBlockState()) { setValue(ACTIVE, false) })
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T?>
    ): BlockEntityTicker<T?>? {
        return super.getTicker(level, state, blockEntityType)
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (state.block != newState.block) {
            val blockEntity = level.getBlockEntity(pos)

            if (blockEntity is AssemblerEntity) {
                Containers.dropContents(level, pos, blockEntity.getItems())
                level.updateNeighbourForOutputSignal(pos, this)
            }
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(FACING, ACTIVE)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, placer, stack)

        if (placer !is Player) return
        level.setBlock(pos, level.getBlockState(pos).setValue(FACING, placer.direction.opposite), 3)
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult
    ): InteractionResult {
        player.openMenu(state.getMenuProvider(level, pos))
        return InteractionResult.SUCCESS
    }

    override fun getRenderShape(state: BlockState): RenderShape? {
        return RenderShape.MODEL
    }

    override fun newBlockEntity(
        pos: BlockPos,
        state: BlockState
    ): BlockEntity {
        return AGBlockEntities.ASSEMBLER_ENTITY.create(pos, state)
    }
}