package dev.definedentity.astralgenerators.multiblock

import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import java.util.Collections

class BlockPart(id: ResourceLocation) : MultiBlockPart {
    var block: Block = Registry.BLOCK.getOptional(id).orElseThrow {
        IllegalStateException("Specified block could not be found: $id")
    }

    override fun isMatch(level: Level, pos: BlockPos): Boolean {
        val state = level.getBlockState(pos)

        if (state.block is StructurePart) {
            return (state.block as StructurePart).`is`(level, pos, block)
        }

        return state.`is`(block)
    }

    override fun validBlocks(): Collection<Block> {
        return Collections.singleton(block)
    }
}