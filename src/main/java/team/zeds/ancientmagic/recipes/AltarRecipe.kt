package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import team.zeds.ancientmagic.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.init.registries.AMRegister
import team.zeds.ancientmagic.api.recipe.AMAbstractRecipe

class AltarRecipe(
    id: ResourceLocation,
    ingredient: Ingredient,
    result: ItemStack,
    exp: Float,
    time: Int
) : AMAbstractRecipe<Container>(
    AMRecipeTypes.ALTAR_RECIPE,
    id,
    ingredient,
    result,
    exp,
    time
) {
    override fun getSerializer(): RecipeSerializer<*> = AMRegister.ALTAR_RECIPE_SERIAL.get()
}