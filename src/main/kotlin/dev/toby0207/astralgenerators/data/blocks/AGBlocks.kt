package dev.toby0207.astralgenerators.data.blocks

import dev.toby0207.astralgenerators.data.AGContent
import dev.toby0207.astralgenerators.data.blocks.ores.DeepslateUraniumOre
import dev.toby0207.astralgenerators.data.blocks.ores.UraniumOre
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

object AGBlocks {
    // Ores
    val ORE = Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE)

    val URANIUM_ORE = AGContent.BLOCKS.registerBlock(UraniumOre.ID) { UraniumOre(ORE) }
    val URANIUM_ORE_ITEM = AGContent.ITEMS.registerSimpleBlockItem(URANIUM_ORE)

    val DEEPSLATE_URANIUM_ORE = AGContent.BLOCKS.registerBlock(DeepslateUraniumOre.ID) { DeepslateUraniumOre(ORE) }
    val DEEPSLATE_URANIUM_ORE_ITEM = AGContent.ITEMS.registerSimpleBlockItem(DEEPSLATE_URANIUM_ORE)

    fun init() {}
}