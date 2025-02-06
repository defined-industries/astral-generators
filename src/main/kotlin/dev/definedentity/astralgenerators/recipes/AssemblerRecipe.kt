package dev.definedentity.astralgenerators.recipes

import com.google.common.collect.HashMultiset
import com.google.gson.JsonObject
import dev.definedentity.astralgenerators.utils.AGContainer
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class AssemblerRecipe(val recipeId: ResourceLocation, val output: ItemStack, val inputs: NonNullList<Ingredient>) : Recipe<AGContainer> {



    override fun matches(container: AGContainer, level: Level): Boolean {
        val containerItems = (0..8).map { container.getItem(it) }
        val containerMultiset = HashMultiset.create<ItemStack>()

        containerItems.filter { !it.isEmpty }.forEach { containerMultiset.add(it) }

        for (input in inputs) {
            val match = containerMultiset.elementSet().find { item -> input.test(item) }
            if (match != null && containerMultiset.count(match) > 0) {
                containerMultiset.remove(match)  // Remove one occurrence
            } else {
                return false  // Input not found
            }
        }
        return true
    }

    override fun assemble(container: AGContainer): ItemStack {
        return output.copy()
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return true
    }

    override fun getResultItem(): ItemStack {
        return output.copy()
    }

    override fun getId(): ResourceLocation {
        return recipeId
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return Serializer.INSTANCE
    }

    override fun getType(): RecipeType<*> {
        return Type.INSTANCE
    }

    fun getOutputItem(): ItemStack {
        return output
    }

    fun getInputItems(): NonNullList<Ingredient> {
        return inputs
    }

    class Type private constructor() : RecipeType<AssemblerRecipe> {
        companion object {
            val INSTANCE: Type = Type()
            const val ID = "assembler"
        }
    }

    object Serializer : RecipeSerializer<AssemblerRecipe> {
        override fun fromJson(recipeId: ResourceLocation, json: JsonObject): AssemblerRecipe {
            val output = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "output"))

            val inputsJson = GsonHelper.getAsJsonArray(json, "inputs")
            val inputs = NonNullList.create<Ingredient>().apply {
                inputsJson.forEach { add(Ingredient.fromJson(it)) }
            }

            return AssemblerRecipe(recipeId, output.defaultInstance, inputs)
        }

        override fun fromNetwork(recipeId: ResourceLocation, buffer: FriendlyByteBuf): AssemblerRecipe {
            val inputCount = buffer.readInt()
            val inputs = NonNullList.withSize(inputCount, Ingredient.EMPTY)
            repeat(inputCount) { inputs[it] = Ingredient.fromNetwork(buffer) }
            val output = buffer.readItem()

            return AssemblerRecipe(recipeId, output, inputs)
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: AssemblerRecipe) {
            buffer.writeInt(recipe.inputs.size)
            recipe.inputs.forEach { it.toNetwork(buffer) }
            buffer.writeItem(recipe.output)
        }

        val INSTANCE = this
        const val ID = "assembler"
    }
}