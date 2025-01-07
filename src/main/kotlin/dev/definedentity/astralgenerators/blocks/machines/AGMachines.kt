package dev.definedentity.astralgenerators.blocks.machines

import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.utils.AGIdentifier
import net.minecraft.core.Direction
import net.minecraftforge.client.model.generators.ConfiguredModel

object AGMachines {
    val ASSEMBLER = REGISTRATE.block("assembler", ::Assembler).lang("Assembler").blockEntity { type, pos, state ->
        AssemblerEntity(type, pos, state)
    }.build().blockstate { ctx, prov ->
        prov.getVariantBuilder(ctx.entry).partialState().with(Assembler.ACTIVE, true)
            .with(Assembler.FACING, Direction.NORTH).modelForState().modelFile(
                prov.models().orientable(
                    "${ctx.name}_active",
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler_active"),
                    prov.modLoc("block/machines/assembler_top_active")
                )
            ).addModel().partialState().with(Assembler.ACTIVE, true)
            .with(Assembler.FACING, Direction.SOUTH).modelForState().modelFile(
                prov.models().orientable(
                    "${ctx.name}_active",
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler_active"),
                    prov.modLoc("block/machines/assembler_top_active")
                )
            ).rotationY(180).addModel()
            .partialState().with(Assembler.ACTIVE, true)
            .with(Assembler.FACING, Direction.EAST).modelForState().modelFile(
                prov.models().orientable(
                    "${ctx.name}_active",
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler_active"),
                    prov.modLoc("block/machines/assembler_top_active")
                )
            ).rotationY(90).addModel().partialState().with(Assembler.ACTIVE, true)
            .with(Assembler.FACING, Direction.WEST).modelForState().modelFile(
                prov.models().orientable(
                    "${ctx.name}_active",
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler_active"),
                    prov.modLoc("block/machines/assembler_top_active")
                )
            ).rotationY(270).addModel().partialState().with(Assembler.ACTIVE, false)
            .with(Assembler.FACING, Direction.NORTH).modelForState().modelFile(
                prov.models().orientable(
                    ctx.name,
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler"),
                    prov.modLoc("block/machines/assembler_top")
                )
            ).addModel().partialState().with(Assembler.ACTIVE, false)
            .with(Assembler.FACING, Direction.SOUTH).modelForState().modelFile(
                prov.models().orientable(
                    ctx.name,
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler"),
                    prov.modLoc("block/machines/assembler_top")
                )
            ).rotationY(180).addModel()
            .partialState().with(Assembler.ACTIVE, false)
            .with(Assembler.FACING, Direction.EAST).modelForState().modelFile(
                prov.models().orientable(
                    ctx.name,
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler"),
                    prov.modLoc("block/machines/assembler_top")
                )
            ).rotationY(90).addModel().partialState().with(Assembler.ACTIVE, false)
            .with(Assembler.FACING, Direction.WEST).modelForState().modelFile(
                prov.models().orientable(
                    ctx.name,
                    prov.modLoc("block/casings/machine/side"),
                    prov.modLoc("block/machines/assembler"),
                    prov.modLoc("block/machines/assembler_top")
                )
            ).rotationY(270).addModel()
    }.simpleItem().register()

    fun init() {}
}