package dev.definedentity.astralgenerators.blocks.ports

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.blocks.ports.hatch.BoilerInputHatch
import dev.definedentity.astralgenerators.blocks.ports.hatch.BoilerOutputHatch
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor

object AGPorts {

    val BOILER_INPUT_HATCH =
        create("boiler_input_hatch", ::BoilerInputHatch).register()
    val BOILER_OUTPUT_HATCH =
        create("boiler_output_hatch", ::BoilerOutputHatch).register()

    val FUSION_REACTOR_INPUT_HATCH =
        create("fusion_reactor_input_hatch", ::BoilerInputHatch).register()
    val FUSION_REACTOR_OUTPUT_HATCH =
        create("fusion_reactor_output_hatch", ::BoilerInputHatch).register()

    val STEAM_TURBINE_INPUT_HATCH =
        create("steam_turbine_input_hatch", ::BoilerInputHatch).register()
    val STEAM_TURBINE_OUTPUT_HATCH =
        create("steam_turbine_output_hatch", ::BoilerInputHatch).register()

    /**
     *
     *     val STEAM_TURBINE_ENERGY_OUTPUT_HATCH =
     *         register("boiler_energy_output_hatch", ::BoilerInputHatch).register()
     *
     *
     *     val FUSION_REACTOR_INPUT_BUS =
     *         register("fusion_reactor_input_bus", ::BoilerInputHatch).register()
     *     val FUSION_REACTOR_OUTPUT_BUS =
     *         register("fusion_reactor_output_bus", ::BoilerInputHatch).register()
     *     val FUSION_REACTOR_ENERGY_INPUT_HATCH =
     *         register("fusion_reactor_energy_input_hatch", ::BoilerInputHatch).register()
     *     val FUSION_REACTOR_ENERGY_OUTPUT_HATCH =
     *         register("fusion_reactor_energy_output_hatch", ::BoilerInputHatch).register()
     */

    private fun <T : Block> create(
        name: String,
        displayName: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
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
                    prov.models().cubeAll(ctx.name, prov.modLoc("block/ports/${ctx.name}"))
                )
            }.simpleItem()
    }

    private fun <T : Block> create(
        name: String,
        blockSupplier: (BlockBehaviour.Properties) -> T
    ): BlockBuilder<Block, Registrate> {
        return create(name, TextFormatting.toEnglishName(name), blockSupplier)
    }

    fun init() {}
}
