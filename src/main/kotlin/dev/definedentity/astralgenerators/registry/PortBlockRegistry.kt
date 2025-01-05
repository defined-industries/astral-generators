package dev.definedentity.astralgenerators.registry

import com.google.common.base.Supplier
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateRecipeProvider
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object PortBlockRegistry {
    private val DEFAULT_PROPERTIES: BlockBehaviour.Properties =
        BlockBehaviour.Properties.of(Material.GLASS)
            .strength(0.3f, 0.3f)
            .sound(SoundType.GLASS)

    private val DEFAULT_TAGS = listOf(BlockTags.IMPERMEABLE)

    fun <T : Block> block(
        name: String,
        englishName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T,

        ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, blockSupplier)
            .lang(englishName)
            .simpleItem()
            .properties { DEFAULT_PROPERTIES }
            .tag(*DEFAULT_TAGS.toTypedArray())
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry, prov.models().cubeAll(ctx.name, prov.modLoc("block/ports/$name"))
                )
            }
    }

    fun <T : Block> block(
        name: String,
        blockSupplier: (BlockBehaviour.Properties) -> T,

        ): BlockBuilder<T, Registrate> {
        return block(name, TextFormatting.toEnglishName(name), blockSupplier)
    }
}
