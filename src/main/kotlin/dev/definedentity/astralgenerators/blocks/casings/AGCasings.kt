package dev.definedentity.astralgenerators.blocks.casings

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object AGCasings {
    val STELLITE_CASING = REGISTRATE.block("stellite_casing", ::Block).lang("Stellite Casing").blockstate { ctx, prov ->
        prov.simpleBlock(
            ctx.entry, prov.models().withExistingParent(ctx.name, "astralgenerators:block/cube/tinted/bottom_top")
                .texture("side", prov.modLoc("block/casings/machine/side"))
                .texture("top", prov.modLoc("block/casings/machine/top"))
                .texture("bottom", prov.modLoc("block/casings/machine/bottom"))
        )
    }.simpleItem().properties {
        BlockBehaviour.Properties.of(Material.METAL).strength(5f, 6f).sound(SoundType.METAL)
            .requiresCorrectToolForDrops()

    }.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL).register()

    val BOILER_CASING = create("boiler_casing", ::Block).register()

    val STEAM_TURBINE_CASING =
        create("steam_turbine_casing", ::Block).register()

    val FUSION_REACTOR_CASING =
        create("fusion_reactor_casing", ::Block).register()

    private fun <T : Block> create(
        name: String,
        displayName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, blockSupplier).lang(displayName).blockstate { ctx, prov ->
            prov.simpleBlock(
                ctx.entry,
                prov.models().cubeAll(ctx.name, prov.modLoc("block/casings/${name}"))
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
