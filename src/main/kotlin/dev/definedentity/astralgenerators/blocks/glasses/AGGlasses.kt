package dev.definedentity.astralgenerators.blocks.glasses

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.GlassBlock
import java.util.function.Supplier

object AGGlasses {

    val FUSION_GLASS =
        REGISTRATE.block("fusion_glass", ::GlassBlock).lang("Fusion Glass").blockstate { ctx, prov ->
            prov.simpleBlock(
                ctx.entry,
                prov.models().cubeAll(ctx.name, prov.modLoc("block/glasses/${ctx.name}"))
            )
        }.simpleItem().properties { p -> p.noOcclusion() }.addLayer { Supplier { RenderType.translucent() } }.register()

    fun init() {}
}
