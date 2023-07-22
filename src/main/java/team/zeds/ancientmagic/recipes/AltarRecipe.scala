package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.{Ingredient, RecipeSerializer}
import team.zeds.ancientmagic.api.recipe.AMAbstractRecipe
import team.zeds.ancientmagic.init.registries.{AMRecipeTypes, AMRegister}

class AltarRecipe(id: ResourceLocation, ingredient: Ingredient, result: ItemStack, exp: Float, time: Int)
  extends AMAbstractRecipe[Container](AMRecipeTypes.ALTAR_RECIPE, id, ingredient, result, exp, time) {
  override def getSerializer: RecipeSerializer[_] = AMRegister.ALTAR_RECIPE_SERIAL.get()
}
