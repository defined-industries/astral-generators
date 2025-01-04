package dev.definedentity.astralgenerators.blocks

import com.google.common.base.Supplier
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.entry.BlockEntry
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.GlassBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object AGGlasses {

    val ESS_GLASS = create("ess_glass", "ESS Glass").register()
    val STRUCTURAL_GLASS = create("structural_glass", "Structural Glass").register()

    fun create(
        name: String,
        displayName: String
        ): BlockBuilder<GlassBlock, Registrate> {
        return REGISTRATE.block(name, ::GlassBlock)
            .lang(displayName)
            .properties { p -> p.strength(0.3f).sound(SoundType.GLASS).noOcclusion() }
            .initialProperties(Material.GLASS)
            .tag(BlockTags.IMPERMEABLE)
            .simpleItem()
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models()
                        .cubeAll(ctx.name, prov.modLoc("block/glasses/$name"))
                )
            }
            .addLayer { Supplier { RenderType.translucent() } }

    }

    fun init() {}
}
