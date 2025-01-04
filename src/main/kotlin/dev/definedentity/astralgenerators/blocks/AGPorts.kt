package dev.definedentity.astralgenerators.blocks

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import net.minecraft.world.level.block.Block

object AGPorts {

    val ESS_INPUT_HATCH = create("ess_input_hatch", "ESS Input Hatch").register()
    val ESS_OUTPUT_HATCH = create("ess_output_hatch", "ESS Output Hatch").register()

    fun create(id: String, displayName: String): BlockBuilder<Block, Registrate> {
        return REGISTRATE.block(id, ::Block)
            .lang(displayName)
            .simpleItem()
            .properties { p ->
                p.requiresCorrectToolForDrops().strength(1.0f, 6.0f)
            }
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry, prov.models().cubeAll(ctx.name, prov.modLoc("block/ports/$id"))
                )
            }
    }

    fun init() {}
}