package dev.definedentity.astralgenerators.compat.rei

import dev.definedentity.astralgenerators.blocks.AGBlocks
import dev.definedentity.astralgenerators.blocks.machines.AGMachines
import dev.definedentity.astralgenerators.compat.rei.assembler.AssemblerCategory
import dev.definedentity.astralgenerators.compat.rei.assembler.AssemblerDisplay
import dev.definedentity.astralgenerators.recipes.AssemblerRecipe
import dev.definedentity.astralgenerators.utils.AGIdentifier
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.client.Minecraft

class AstralGeneratorsREIClientPlugin : REIClientPlugin {
    companion object {
        val ASSEMBLER = CategoryIdentifier.of<AssemblerDisplay>(AGIdentifier("assembler"))
    }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(AssemblerCategory())
        registry.addWorkstations(ASSEMBLER, EntryStacks.of(AGMachines.ASSEMBLER.get()))
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        val recipeManager = Minecraft.getInstance().level!!.recipeManager

        val assemblerRecipes = recipeManager.getAllRecipesFor(AssemblerRecipe.Type.INSTANCE).map(AssemblerDisplay::of)
        assemblerRecipes.forEach { registry.add(it) }
    }

    override fun registerScreens(registry: ScreenRegistry) {
        super.registerScreens(registry)
    }
}