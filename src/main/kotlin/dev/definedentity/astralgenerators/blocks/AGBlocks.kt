package dev.definedentity.astralgenerators.blocks

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.blocks.casings.AGCasings
import dev.definedentity.astralgenerators.blocks.coils.AGCoils
import dev.definedentity.astralgenerators.blocks.glasses.AGGlasses
import dev.definedentity.astralgenerators.blocks.machines.AGMachines
import dev.definedentity.astralgenerators.blocks.machines.Assembler
import dev.definedentity.astralgenerators.blocks.ports.AGPorts
import dev.definedentity.astralgenerators.utils.AGIdentifier

object AGBlocks {
    fun init() {
        AGMachines.init()
        AGCasings.init()
        AGCoils.init()
        AGGlasses.init()
        AGPorts.init()
    }
}