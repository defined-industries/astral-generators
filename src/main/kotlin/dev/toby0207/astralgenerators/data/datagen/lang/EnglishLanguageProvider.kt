package dev.toby0207.astralgenerators.data.datagen.lang

import codechicken.lib.datagen.LanguageProvider
import dev.toby0207.astralgenerators.AstralGenerators
import dev.toby0207.astralgenerators.data.blocks.AGBlocks
import dev.toby0207.astralgenerators.data.blocks.ores.DeepslateUraniumOre
import dev.toby0207.astralgenerators.data.blocks.ores.UraniumOre
import dev.toby0207.astralgenerators.data.items.AGItems
import dev.toby0207.astralgenerators.data.items.Astralnomicon
import dev.toby0207.astralgenerators.utils.TextFormatting
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Item
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.registries.DeferredItem

class EnglishLanguageProvider(output: PackOutput) : LanguageProvider(
    output,
    AstralGenerators.MOD_ID,
    "en_us",
    Side.CLIENT
) {
    override fun addTranslations() {
        // Creative mode tabs
        add("itemGroup.${AstralGenerators.MOD_ID}:general", AstralGenerators.MOD_NAME)

        add(AGItems.ASTRALNOMICON, TextFormatting.toEnglishName(Astralnomicon.ID))

        // Ores
        add(AGBlocks.URANIUM_ORE, TextFormatting.toEnglishName(UraniumOre.ID))
        add(AGBlocks.DEEPSLATE_URANIUM_ORE, TextFormatting.toEnglishName(DeepslateUraniumOre.ID))
    }
}