package dev.toby0207.astralgenerators.data.datagen

import codechicken.lib.datagen.ItemModelProvider
import dev.toby0207.astralgenerators.AstralGenerators
import dev.toby0207.astralgenerators.data.blocks.AGBlocks
import dev.toby0207.astralgenerators.data.items.AGItems
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem

class AGItemModelProvider(output: PackOutput, existingFileHelper: ExistingFileHelper) : ItemModelProvider(
    output,
    AstralGenerators.MOD_ID,
    existingFileHelper
) {
    private val generatedItems = listOf<DeferredItem<*>>(AGItems.ASTRALNOMICON, AGItems.WRENCH)
    private val oresItem = listOf<DeferredBlock<*>>(AGBlocks.URANIUM_ORE, AGBlocks.DEEPSLATE_URANIUM_ORE)

    override fun registerModels() {
        generatedItems.forEach { i -> generated(i.asItem()) }
        oresItem.forEach { i -> simpleItemBlock(i.get()) }
    }
}