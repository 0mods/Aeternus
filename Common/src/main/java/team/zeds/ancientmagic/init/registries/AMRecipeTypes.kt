package team.zeds.ancientmagic.init.registries

import net.minecraft.world.Container
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import team.zeds.ancientmagic.api.recipe.CastRecipeType
import team.zeds.ancientmagic.recipes.AltarRecipe
import team.zeds.ancientmagic.recipes.ManaGenerationRecipe

class AMRecipeTypes {
    val altarRecipe: RecipeType<AltarRecipe> = register("altar")
    val manaRecipe: RecipeType<ManaGenerationRecipe> = register("mana_generation")

    fun <T: Recipe<Container>> register(id: String): CastRecipeType<T> = CastRecipeType(id)

    companion object {
        @get:JvmStatic
        var instance: AMRecipeTypes? = null
            get() {
                if (field == null) field = AMRecipeTypes()
                return field
            }
            private set
    }
}