package dev.definedentity.astralgenerators.multiblock

import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.tags.TagKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import java.util.stream.Collectors

class TagPart(val tag: TagKey<Block>) : MultiBlockPart {
    private var blockCache: List<Block>? = null

    override fun isMatch(level: Level, pos: BlockPos): Boolean {
        val state = level.getBlockState(pos)

        if (state.block is StructurePart) {
            return (state.block as StructurePart).`is`(level, pos, tag)
        }

        return state.`is`(tag)
    }

    override fun validBlocks(): Collection<Block> {
        if (blockCache == null) {
            blockCache =
                Registry.BLOCK.stream().filter { it -> it.defaultBlockState().`is`(tag) }.collect(Collectors.toList())
        }

        return blockCache!!
    }
}