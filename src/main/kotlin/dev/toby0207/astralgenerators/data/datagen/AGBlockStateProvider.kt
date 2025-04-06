package dev.toby0207.astralgenerators.data.datagen

import dev.toby0207.astralgenerators.AstralGenerators
import dev.toby0207.astralgenerators.data.blocks.AGBlocks
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class AGBlockStateProvider(output: PackOutput, existingFileHelper: ExistingFileHelper) : BlockStateProvider(
    output,
    AstralGenerators.MOD_ID,
    existingFileHelper
) {
    override fun registerStatesAndModels() {
        simpleBlock(AGBlocks.URANIUM_ORE.get())
        simpleBlock(AGBlocks.DEEPSLATE_URANIUM_ORE.get())
    }
}