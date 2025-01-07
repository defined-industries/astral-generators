package dev.definedentity.astralgenerators.blockentities

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.blocks.AGBlocks
import dev.definedentity.astralgenerators.blocks.machines.AGMachines
import net.minecraft.core.Registry

object AGBlockEntities {

    val ASSEMBLER_ENTITY =
        BlockEntityEntry.cast<AssemblerEntity>(AGMachines.ASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    val DISASSEMBLER_ENTITY =
        BlockEntityEntry.cast<AssemblerEntity>(AGMachines.DISASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY))

    fun init() {}
}