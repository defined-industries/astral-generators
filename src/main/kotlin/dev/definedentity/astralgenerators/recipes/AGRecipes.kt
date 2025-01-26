package dev.definedentity.astralgenerators.recipes

import dev.definedentity.astralgenerators.utils.AGIdentifier
import net.minecraft.core.Registry

object AGRecipes {
    fun init() {
        Registry.register(
            Registry.RECIPE_SERIALIZER,
            AGIdentifier(AssemblerRecipe.Serializer.ID()),
            AssemblerRecipe.Serializer.INSTANCE()
        )

        Registry.register(
            Registry.RECIPE_TYPE,
            AGIdentifier(AssemblerRecipe.Type.ID()),
            AssemblerRecipe.Type.INSTANCE()
        )
    }
}