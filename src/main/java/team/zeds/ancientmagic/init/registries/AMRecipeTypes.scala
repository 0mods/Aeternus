package team.zeds.ancientmagic.init.registries

import net.minecraft.world.Container
import net.minecraft.world.item.crafting.{Recipe, RecipeType}
import team.zeds.ancientmagic.recipes.{AltarRecipe, CastRecipeType, ManaGenerationRecipe}

object AMRecipeTypes {
  val ALTAR_RECIPE: RecipeType[AltarRecipe] = register("altar_recipe")
  val MANA_GEN_RECIPE: RecipeType[ManaGenerationRecipe] = register("mana_gen")

  private def register[T <: Recipe[_ <: Container]](id: String): CastRecipeType[T] = {
    new CastRecipeType[T](id)
  }
}
