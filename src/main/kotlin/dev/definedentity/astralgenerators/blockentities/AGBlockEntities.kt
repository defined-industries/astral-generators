package dev.definedentity.astralgenerators.blockentities

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.definedentity.astralgenerators.blockentities.controllers.BoilerControllerEntity
import dev.definedentity.astralgenerators.blockentities.controllers.FusionReactorControllerEntity
import dev.definedentity.astralgenerators.blockentities.controllers.SteamTurbineControllerEntity
import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.blockentities.machines.DisassemblerEntity
import dev.definedentity.astralgenerators.blocks.AGBlocks
import dev.definedentity.astralgenerators.blocks.machines.AGMachines
import net.minecraft.core.Registry
import team.reborn.energy.api.EnergyStorage

object AGBlockEntities {

    val ASSEMBLER_ENTITY =
        BlockEntityEntry.cast<AssemblerEntity>(AGMachines.ASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    val DISASSEMBLER_ENTITY =
        BlockEntityEntry.cast<DisassemblerEntity>(AGMachines.DISASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    val BOILER_CONTROLLER_ENTITY =
        BlockEntityEntry.cast<BoilerControllerEntity>(AGMachines.BOILER_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    val STEAM_TURBINE_CONTROLLER_ENTITY =
        BlockEntityEntry.cast<SteamTurbineControllerEntity>(AGMachines.STEAM_TURBINE_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    val FUSION_REACTOR_CONTROLLER_ENTITY =
        BlockEntityEntry.cast<FusionReactorControllerEntity>(AGMachines.FUSION_REACTOR_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    fun init() {
    }
}
