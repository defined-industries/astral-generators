package dev.definedentity.astralgenerators.blocks.machines

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateBlockstateProvider
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.blockentities.machines.AssemblerEntity
import dev.definedentity.astralgenerators.blockentities.machines.DisassemblerEntity
import dev.definedentity.astralgenerators.utils.AGDirections
import dev.definedentity.astralgenerators.utils.AGIdentifier
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.core.Direction
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.client.model.generators.ConfiguredModel

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
