package dev.definedentity.astralgenerators.blocks.ports

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.blocks.ports.hatch.BoilerInputHatch
import dev.definedentity.astralgenerators.blocks.ports.hatch.BoilerOutputHatch
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import java.util.function.Supplier

object AGPorts {

    val BOILER_INPUT_HATCH =
        createInput("boiler_input_hatch", "boiler_casing", PortType.FLUID, ::BoilerInputHatch).register()
    val BOILER_OUTPUT_HATCH =
        createOutput("boiler_output_hatch", "boiler_casing", PortType.FLUID, ::BoilerOutputHatch).register()

    val FUSION_REACTOR_INPUT_HATCH =
        createInput(
            "fusion_reactor_input_hatch",
            "fusion_reactor_casing",
            PortType.FLUID,
            ::BoilerInputHatch
        ).register()
    val FUSION_REACTOR_OUTPUT_HATCH =
        createOutput(
            "fusion_reactor_output_hatch",
            "fusion_reactor_casing",
            PortType.FLUID,
            ::BoilerInputHatch
        ).register()
    val FUSION_REACTOR_ENERGY_INPUT_HATCH =
        createInput(
            "fusion_reactor_energy_input_hatch",
            "fusion_reactor_casing",
            PortType.ENERGY,
            ::BoilerInputHatch
        ).register()
    val FUSION_REACTOR_ENERGY_OUTPUT_HATCH =
        createOutput(
            "fusion_reactor_energy_output_hatch",
            "fusion_reactor_casing",
            PortType.ENERGY,
            ::BoilerInputHatch
        ).register()
    val FUSION_REACTOR_INPUT_BUS =
        createInput("fusion_reactor_input_bus", "fusion_reactor_casing", PortType.ITEM, ::BoilerInputHatch).register()
    val FUSION_REACTOR_OUTPUT_BUS =
        createOutput("fusion_reactor_output_bus", "fusion_reactor_casing", PortType.ITEM, ::BoilerInputHatch).register()

    val STEAM_TURBINE_INPUT_HATCH =
        createInput("steam_turbine_input_hatch", "steam_turbine_casing", PortType.FLUID, ::BoilerInputHatch).register()
    val STEAM_TURBINE_OUTPUT_HATCH =
        createOutput(
            "steam_turbine_output_hatch",
            "steam_turbine_casing",
            PortType.FLUID,
            ::BoilerInputHatch
        ).register()
    val STEAM_TURBINE_ENERGY_OUTPUT_HATCH =
        createOutput(
            "steam_turbine_energy_output_hatch",
            "steam_turbine_casing",
            PortType.ENERGY,
            ::BoilerInputHatch
        ).register()

    private fun <T : Block> createInput(
        name: String,
        casingName: String,
        displayName: String,
        type: PortType,
        blockSupplier: (BlockBehaviour.Properties) -> T,
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, blockSupplier).lang(displayName)
            .properties {
                BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(1f, 1f)
                    .sound(SoundType.METAL)
            }.blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models().withExistingParent(ctx.name, prov.modLoc("block/cube_2_layer/all"))
                        .texture("bot_all", prov.modLoc("block/casings/${casingName}"))
                        .texture(
                            "top_all",
                            prov.modLoc("block/overlays/overlay_${type}_input")
                        )
                )
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }
    }

    private fun <T : Block> createInput(
        name: String,
        casingName: String,
        type: PortType,
        blockSupplier: (BlockBehaviour.Properties) -> T,

        ): BlockBuilder<Block, Registrate> {
        return createInput(name, casingName, TextFormatting.toEnglishName(name), type, blockSupplier)
    }

    private fun <T : Block> createOutput(
        name: String,
        casingName: String,
        displayName: String,
        type: PortType,
        blockSupplier: (BlockBehaviour.Properties) -> T,
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, blockSupplier).lang(displayName)
            .properties {
                BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(1f, 1f)
                    .sound(SoundType.METAL)
            }.blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models().withExistingParent(ctx.name, prov.modLoc("block/cube_2_layer/all"))
                        .texture("bot_all", prov.modLoc("block/casings/${casingName}"))
                        .texture(
                            "top_all",
                            prov.modLoc("block/overlays/overlay_${type}_output")
                        )
                )
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }
    }

    private fun <T : Block> createOutput(
        name: String,
        casingName: String,
        type: PortType,
        blockSupplier: (BlockBehaviour.Properties) -> T,

        ): BlockBuilder<Block, Registrate> {
        return createOutput(name, casingName, TextFormatting.toEnglishName(name), type, blockSupplier)
    }

    fun init() {}
}
