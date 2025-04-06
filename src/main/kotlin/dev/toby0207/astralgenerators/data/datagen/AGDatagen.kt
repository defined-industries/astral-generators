package dev.toby0207.astralgenerators.data.datagen

import dev.toby0207.astralgenerators.data.datagen.lang.EnglishLanguageProvider
import net.neoforged.neoforge.data.event.GatherDataEvent


object AGDatagen {
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val output = generator.packOutput
        val existingFileHelper = event.existingFileHelper

        generator.addProvider(event.includeClient(), AGItemModelProvider(output, existingFileHelper))
        generator.addProvider(event.includeClient(), AGBlockStateProvider(output, existingFileHelper))
        generator.addProvider(event.includeClient(), EnglishLanguageProvider(output))
    }

}