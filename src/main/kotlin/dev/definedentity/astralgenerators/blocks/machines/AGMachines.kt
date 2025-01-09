package dev.definedentity.astralgenerators.blocks.machines

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.blockentities.controllers.BoilerControllerEntity
import dev.definedentity.astralgenerators.blockentities.controllers.FusionReactorControllerEntity
import dev.definedentity.astralgenerators.blockentities.controllers.SteamTurbineControllerEntity
import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.blockentities.machines.DisassemblerEntity
import dev.definedentity.astralgenerators.blocks.machines.controllers.BoilerController
import dev.definedentity.astralgenerators.blocks.machines.controllers.FusionReactorController
import dev.definedentity.astralgenerators.blocks.machines.controllers.SteamTurbineController
import dev.definedentity.astralgenerators.utils.AGDirections
import dev.definedentity.astralgenerators.utils.AGIdentifier
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.client.model.generators.ConfiguredModel
import java.util.function.Supplier

object AGMachines {
    val ASSEMBLER = REGISTRATE.block("assembler", ::Assembler).lang("Assembler").blockEntity { type, pos, state ->
        AssemblerEntity(type, pos, state)
    }.build().blockstate { ctx, prov ->
        for ((direction, rotationY) in AGDirections.directions) {
            createModelWithTopActive(ctx, prov, true, direction, rotationY)
            createModelWithTopActive(ctx, prov, false, direction, rotationY)
        }
    }.simpleItem().register()

    val DISASSEMBLER =
        REGISTRATE.block("disassembler", ::Disassembler).lang("Disassembler").blockEntity { type, pos, state ->
            DisassemblerEntity(type, pos, state)
        }.build().blockstate { ctx, prov ->
            for ((direction, rotationY) in AGDirections.directions) {
                createModelWithTopActive(ctx, prov, true, direction, rotationY)
                createModelWithTopActive(ctx, prov, false, direction, rotationY)
            }
        }.simpleItem().register()

    val STEAM_TURBINE_CONTROLLER =
        REGISTRATE.block("steam_turbine_controller", ::SteamTurbineController).lang("Steam Turbine Controller")
            .blockEntity { type, pos, state ->
                SteamTurbineControllerEntity(type, pos, state)
            }.build().blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    createControllerModel("steam_turbine_casing", ctx, prov, true, direction, rotationY)
                    createControllerModel("steam_turbine_casing", ctx, prov, false, direction, rotationY)
                }
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }.register()

    val BOILER_CONTROLLER =
        REGISTRATE.block("boiler_controller", ::BoilerController).lang("Boiler Controller")
            .blockEntity { type, pos, state ->
                BoilerControllerEntity(type, pos, state)
            }.build().blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    createControllerModel("boiler_casing", ctx, prov, true, direction, rotationY)
                    createControllerModel("boiler_casing", ctx, prov, false, direction, rotationY)
                }
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }.register()

    val FUSION_REACTOR_CONTROLLER =
        REGISTRATE.block("fusion_reactor_controller", ::FusionReactorController).lang("Fusion Reactor Controller")
            .blockEntity { type, pos, state ->
                FusionReactorControllerEntity(type, pos, state)
            }.build().blockstate { ctx, prov ->
                for ((direction, rotationY) in AGDirections.directions) {
                    createControllerModel("fusion_reactor_casing", ctx, prov, true, direction, rotationY)
                    createControllerModel("fusion_reactor_casing", ctx, prov, false, direction, rotationY)
                }
            }.simpleItem().addLayer { Supplier { RenderType.translucent() } }.register()

    private fun <T : Block> createControllerModel(
        casingName: String,
        ctx: DataGenContext<Block, T>,
        prov: RegistrateBlockstateProvider,
        active: Boolean,
        direction: Direction,
        rotationY: Int
    ) {
        val modelName = if (active) "${ctx.name}_active" else ctx.name
        val sideTexture = prov.modLoc("block/casings/${casingName}")
        val frontTexture = prov.modLoc(
            if (active) "block/machines/${ctx.name}_active" else "block/machines/${ctx.name}"
        )

        prov.getVariantBuilder(ctx.entry).partialState()
            .with(Assembler.ACTIVE, active)
            .with(Assembler.FACING, direction)
            .modelForState()
            .modelFile(
                prov.models().withExistingParent(modelName, "astralgenerators:block/cube_2_layer/default")
                    .texture("particle", sideTexture)
                    .texture("bot_down", sideTexture)
                    .texture("bot_up", sideTexture)
                    .texture("bot_east", sideTexture)
                    .texture("bot_west", sideTexture)
                    .texture("bot_south", sideTexture)
                    .texture("bot_north", sideTexture)
                    .texture("top_down", sideTexture)
                    .texture("top_up", sideTexture)
                    .texture("top_east", sideTexture)
                    .texture("top_west", sideTexture)
                    .texture("top_south", sideTexture)
                    .texture("top_north", frontTexture)
            )
            .rotationY(rotationY)
            .addModel()
    }

    private fun <T : Block> createModelWithTopActive(
        ctx: DataGenContext<Block, T>,
        prov: RegistrateBlockstateProvider,
        active: Boolean,
        direction: Direction,
        rotationY: Int
    ) {
        val modelName = if (active) "${ctx.name}_active" else ctx.name
        val sideTexture = prov.modLoc("block/casings/machine/side")
        val frontTexture = prov.modLoc(
            if (active) "block/machines/${ctx.name}_active" else "block/machines/${ctx.name}"
        )
        val topTexture = prov.modLoc(
            if (active) "block/machines/${ctx.name}_top_active" else "block/machines/${ctx.name}_top"
        )

        prov.getVariantBuilder(ctx.entry).partialState()
            .with(Assembler.ACTIVE, active)
            .with(Assembler.FACING, direction)
            .modelForState()
            .modelFile(prov.models().orientable(modelName, sideTexture, frontTexture, topTexture))
            .rotationY(rotationY)
            .addModel()
    }

    fun init() {}
}
