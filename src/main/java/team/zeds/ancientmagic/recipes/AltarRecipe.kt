package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import team.zeds.ancientmagic.api.recipe.AMAbstractRecipe
import team.zeds.ancientmagic.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.init.registries.AMRegister

class AltarRecipe(id: ResourceLocation, ingr: Ingredient, result: ItemStack, exp: Float, time: Int): AMAbstractRecipe(
    AMRecipeTypes.instance!!.altarRecipe,
    id,
    ingr,
    result,
    exp,
    time
) {
    override fun getSerializer(): RecipeSerializer<*> = AMRegister.ALTAR_RECIPE_SERIAL.get()
}