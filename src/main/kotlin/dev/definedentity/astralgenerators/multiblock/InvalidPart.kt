package dev.definedentity.astralgenerators.multiblock

import net.minecraft.core.BlockPos

data class InvalidPart(val pos: BlockPos, val expected: MultiBlockPart) {
}