package dev.definedentity.astralgenerators.blocks

import dev.definedentity.astralgenerators.registry.CasingBlockRegistry
import net.minecraft.world.level.block.Block

object AGCasings {
    val STEEL_CASING = CasingBlockRegistry.block("steel_casing", "Steel Casing", ::Block).register()
    val STEEL_PIPE_CASING = CasingBlockRegistry.block("steel_pipe_casing", "Steel Pipe Casing", ::Block).register()

    fun init() {}
}