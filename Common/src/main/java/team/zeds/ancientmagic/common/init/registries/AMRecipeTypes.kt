package team.zeds.ancientmagic.common.init.registries

import net.minecraft.world.Container
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import team.zeds.ancientmagic.common.api.recipe.CastRecipeType
import team.zeds.ancientmagic.common.recipes.AltarRecipe
import team.zeds.ancientmagic.common.recipes.ManaGenerationRecipe

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