package team.zeds.ancientmagic.init.registries

import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import team.zeds.ancientmagic.recipes.*

object AMRecipeTypes {
    val ALTAR_RECIPE: RecipeType<AltarRecipe> = register("altar_recipe")

    private fun <T: Recipe<*>> register(recipeId: String): CastRecipeType<T> {
        return CastRecipeType(recipeId)
    }
}