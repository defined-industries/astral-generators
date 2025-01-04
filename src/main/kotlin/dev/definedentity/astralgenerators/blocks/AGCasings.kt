package dev.definedentity.astralgenerators.blocks

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.entry.BlockEntry
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraftforge.client.model.generators.ModelFile

object AGCasings {

    val ESS_CASING = create("ess_casing", "ESS Casing").register()
    val STEEL_CASING = create("steel_casing", "Steel Casing").register()

    val STEEL_PIPE_CASING = create("steel_pipe_casing", "Steel Pipe Casing").register()

    private fun create(id: String, displayName: String): BlockBuilder<Block, Registrate> {
        return REGISTRATE.block(id, ::Block)
            .lang(displayName)
            .simpleItem()
            .properties { p ->
                p.requiresCorrectToolForDrops().strength(1.0f, 6.0f)
            }
            .blockstate { ctx, prov ->
                prov.simpleBlock(ctx.entry, prov.models().cubeAll(ctx.name, prov.modLoc("block/casings/$id")))
            }
    }

    fun init() {}
}