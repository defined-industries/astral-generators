package dev.definedentity.astralgenerators.blocks

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.entry.BlockEntry
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock

object AGBatteries {

    val TIER_1 = create("1").register()
    val TIER_2 = create("2").register()
    val TIER_3 = create("3").register()

    private fun create(tier: String): BlockBuilder<RotatedPillarBlock, Registrate> {
        val fullName = "battery_tier_$tier"
        return REGISTRATE.block(fullName, ::RotatedPillarBlock)
            .lang(TextFormatting.toEnglishName(fullName))
            .simpleItem()
            .properties { p ->
                p.requiresCorrectToolForDrops().strength(1.0f, 6.0f)
            }
            .blockstate { ctx, prov ->
                prov.axisBlock(
                    ctx.entry,
                    prov.modLoc("block/batteries/tier_$tier/side"),
                    prov.modLoc("block/batteries/tier_$tier/top")
                )
            }


    }

    fun init() {}

}
