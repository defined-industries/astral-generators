package dev.definedentity.astralgenerators.fluids

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.AGIdentifier
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.material.FlowingFluid

object AGFluids {

    val STEAM =
        REGISTRATE.fluid("steam", AGIdentifier("fluid/steam"), AGIdentifier("fluid/steam_flow")).noBucket().register()

    fun init() {}
}
