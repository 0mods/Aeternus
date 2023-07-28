package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import team.zeds.ancientmagic.api.recipe.AMAbstractRecipe
import team.zeds.ancientmagic.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.platform.AMServices

class AltarRecipe( id: ResourceLocation, ingredient: Ingredient, result: ItemStack,
                  experience: Float, time: Int
) : AMAbstractRecipe(
    AMRecipeTypes.instance!!.altarRecipe, id,
    ingredient,
    result, experience, time
) {
    override fun getSerializer(): RecipeSerializer<*> = AMServices.PLATFORM.getAltarRecipeSerializer()
}