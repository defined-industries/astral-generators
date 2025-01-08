package dev.definedentity.astralgenerators.blocks.coils

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object AGCoils {

    val FUSION_REACTOR_COIL =
        create("fusion_reactor_coil", ::Block).register()

    private fun <T : Block> create(
        name: String,
        displayName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, blockSupplier).lang(displayName).blockstate { ctx, prov ->
            prov.simpleBlock(
                ctx.entry,
                prov.models().cubeAll(ctx.name, prov.modLoc("block/coils/${name}"))
            )
        }.simpleItem().properties {
            BlockBehaviour.Properties.of(Material.METAL).strength(5f, 6f).sound(SoundType.METAL)
                .requiresCorrectToolForDrops()

        }.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
    }

    private fun <T : Block> create(
        name: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return create(name, TextFormatting.toEnglishName(name), blockSupplier)
    }

    fun init() {}
}
