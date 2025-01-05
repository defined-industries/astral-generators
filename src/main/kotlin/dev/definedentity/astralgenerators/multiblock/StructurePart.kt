package dev.definedentity.astralgenerators.multiblock

import net.minecraft.core.BlockPos
import net.minecraft.tags.TagKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

interface StructurePart {
    fun `is`(level: Level, pos: BlockPos, key: TagKey<Block>): Boolean

    fun `is`(level: Level, pos: BlockPos, block: Block): Boolean
}