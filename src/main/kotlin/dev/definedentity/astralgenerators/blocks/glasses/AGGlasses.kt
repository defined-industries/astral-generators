package dev.definedentity.astralgenerators.blocks.glasses

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.GlassBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import java.util.function.Supplier

object AGGlasses {

    val FUSION_REACTOR_GLASS =
        create("fusion_reactor_glass", ::GlassBlock).register()

    private fun <T : GlassBlock> create(
        name: String,
        displayName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, blockSupplier).lang(displayName).blockstate { ctx, prov ->
            prov.simpleBlock(
                ctx.entry,
                prov.models().cubeAll(ctx.name, prov.modLoc("block/glasses/${name}"))
            )
        }.simpleItem().properties {
            BlockBehaviour.Properties.of(Material.GLASS).strength(0.3f, 0.3f).sound(SoundType.GLASS)
                .requiresCorrectToolForDrops().noOcclusion()

        }.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.IMPERMEABLE)
            .addLayer { Supplier { RenderType.translucent() } }
    }

    private fun <T : GlassBlock> create(
        name: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return create(name, TextFormatting.toEnglishName(name), blockSupplier)
    }

    fun init() {}
}
