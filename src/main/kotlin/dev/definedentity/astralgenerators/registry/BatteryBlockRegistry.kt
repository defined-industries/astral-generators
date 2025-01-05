package dev.definedentity.astralgenerators.registry

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor

object BatteryBlockRegistry {
    private val DEFAULT_PROPERTIES: BlockBehaviour.Properties =
        BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL)
            .requiresCorrectToolForDrops()
            .strength(5f, 6f)
            .sound(SoundType.METAL)
    private val DEFAULT_TAGS = listOf(BlockTags.NEEDS_STONE_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)

    fun block(
        tier: String,
        blockSupplier: (BlockBehaviour.Properties) -> RotatedPillarBlock,
    ): BlockBuilder<RotatedPillarBlock, Registrate> {
        val fullName = "battery_tier_$tier"

        return REGISTRATE.block(fullName, blockSupplier)
            .lang(TextFormatting.toEnglishName(fullName))
            .simpleItem()
            .properties { DEFAULT_PROPERTIES }
            .tag(*DEFAULT_TAGS.toTypedArray())
            .blockstate { ctx, prov ->
                prov.axisBlock(
                    ctx.entry,
                    prov.modLoc("block/batteries/tier_$tier/side"),
                    prov.modLoc("block/batteries/tier_$tier/top")
                )
            }
    }
}
