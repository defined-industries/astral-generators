package dev.definedentity.astralgenerators.registry

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor

object CasingBlockRegistry {
    private val DEFAULT_PROPERTIES: BlockBehaviour.Properties =
        BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL)
            .requiresCorrectToolForDrops()
            .strength(5f, 6f)
            .sound(SoundType.METAL)
    private val DEFAULT_TAGS = listOf(BlockTags.NEEDS_STONE_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)

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
                    ctx.entry,
                    prov.models().cubeAll(ctx.name, prov.modLoc("block/casings/${name}"))
                )
            }
    }

    fun <T : Block> block(
        name: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return block(name, TextFormatting.toEnglishName(name), blockSupplier)
    }
}
