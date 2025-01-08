package dev.definedentity.astralgenerators.blocks.coils

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import net.minecraft.world.level.block.Block

object AGCoils {

    val FUSION_COIL =
        REGISTRATE.block("fusion_coil", ::Block).lang("Fusion Coil").blockstate { ctx, prov ->
            prov.simpleBlock(
                ctx.entry,
                prov.models().cubeAll(ctx.name, prov.modLoc("block/coils/${ctx.name}"))
            )
        }.simpleItem().register()

    fun init() {}
}
