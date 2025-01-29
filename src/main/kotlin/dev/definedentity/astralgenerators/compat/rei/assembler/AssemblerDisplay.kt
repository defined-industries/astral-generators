package dev.definedentity.astralgenerators.compat.rei.assembler

import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import java.util.Collections

class AssemblerDisplay(
    val id: ResourceLocation,
    inputs: List<EntryIngredient>,
    outputs: List<EntryIngredient>
) :
    BasicDisplay(inputs, outputs) {
    override fun getCategoryIdentifier(): CategoryIdentifier<*>? {
        TODO("Not yet implemented")
    }

    fun getRecipeId(): ResourceLocation {
        return id
    }

    companion object {
        fun of(recipe: Recipe<*>): AssemblerDisplay {
            val inputs = EntryIngredients.ofIngredients(recipe.ingredients)
            val outputs = Collections.singletonList(EntryIngredients.of(recipe.resultItem))

            return AssemblerDisplay(recipe.id, inputs, outputs)
        }
    }
}