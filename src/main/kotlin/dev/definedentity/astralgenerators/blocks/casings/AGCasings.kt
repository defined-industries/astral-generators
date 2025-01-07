package dev.definedentity.astralgenerators.blocks.casings

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import net.minecraft.world.level.block.Block

object AGCasings {
    val STELLITE_CASING = REGISTRATE.block("stellite_casing", ::Block).lang("Stellite Casing").blockstate { ctx, prov ->
        prov.simpleBlock(
            ctx.entry, prov.models().withExistingParent(ctx.name, "astralgenerators:block/cube/tinted/bottom_top")
                .texture("side", prov.modLoc("block/casings/machine/side"))
                .texture("top", prov.modLoc("block/casings/machine/top"))
                .texture("bottom", prov.modLoc("block/casings/machine/bottom"))
        )
    }.simpleItem().register()

    fun init() {}
}