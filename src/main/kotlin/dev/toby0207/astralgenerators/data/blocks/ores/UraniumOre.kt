package dev.toby0207.astralgenerators.data.blocks.ores

import net.minecraft.core.BlockPos
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class UraniumOre(properties: Properties) : DropExperienceBlock(ConstantInt.ZERO, properties) {
    companion object {
        val ID = "uranium_ore"
    }

    override fun getExpDrop(
        state: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        blockEntity: BlockEntity?,
        breaker: Entity?,
        tool: ItemStack
    ): Int {
        // TODO: Silktouch drops 0, only drops with Fortune
        val randomSource = RandomSource.create()
        return Mth.nextInt(randomSource, 5, 12)
    }
}