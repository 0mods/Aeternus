package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.{Ingredient, RecipeSerializer}
import team.zeds.ancientmagic.api.recipe.AMAbstractChancedRecipe
import team.zeds.ancientmagic.init.registries.{AMRecipeTypes, AMRegister}

class ManaGenerationRecipe(id: ResourceLocation, ingr: Ingredient, result: ItemStack, chance: Int, exp: Float, time: Int)
  extends AMAbstractChancedRecipe[Container](AMRecipeTypes.MANA_GEN_RECIPE, id, ingr, result, chance, exp, time){
  override def getSerializer: RecipeSerializer[_] = AMRegister.MANA_RECIPE_SERIAL.get()
}
