package dev.definedentity.astralgenerators.recipes

import com.google.gson.Gson
import com.google.gson.JsonObject
import dev.definedentity.astralgenerators.utils.AGContainer
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.level.Level

class AssemblerRecipe(val recipeId: ResourceLocation, val output: ItemStack, val inputs: NonNullList<Ingredient>) :
    Recipe<AGContainer> {

    override fun matches(container: AGContainer, level: Level): Boolean {
        if (inputs[0].test(container.getItem(0))) {
            return inputs[1].test(container.getItem(1))
        }

        // TODO: Browse all slots

        return false
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

    class Type private constructor() : RecipeType<AssemblerRecipe> {
        companion object {
            val INSTANCE: Type = Type()
            const val ID = "assembler"
        }
    }

    class Serializer private constructor() : RecipeSerializer<AssemblerRecipe> {
        companion object {
            val INSTANCE: Serializer = Serializer()
            const val ID = "assembler"
        }

        override fun fromJson(recipeId: ResourceLocation, serializedRecipe: JsonObject): AssemblerRecipe {
            val output = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "output"))
            val ingredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients")
            val inputs = NonNullList.withSize(2, Ingredient.EMPTY)

            for (i in inputs.indices) {
                inputs[i] = Ingredient.fromJson(ingredients.get(i))
            }

            return AssemblerRecipe(recipeId, output.defaultInstance, inputs)
        }

        override fun fromNetwork(recipeId: ResourceLocation, buffer: FriendlyByteBuf): AssemblerRecipe {
            val inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY)

            for (i in inputs.indices) {
                inputs[i] = Ingredient.fromNetwork(buffer)
            }

            val output = buffer.readItem()

            return AssemblerRecipe(recipeId, output, inputs)
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: AssemblerRecipe) {
            buffer.writeInt(recipe.ingredients.size)

            for (ingredient in recipe.ingredients) {
                ingredient.toNetwork(buffer)
            }
            buffer.writeItem(recipe.output)
        }

    }
}