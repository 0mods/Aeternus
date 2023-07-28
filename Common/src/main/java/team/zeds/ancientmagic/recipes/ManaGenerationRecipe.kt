package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import team.zeds.ancientmagic.api.recipe.AMAbstractChancedRecipe
import team.zeds.ancientmagic.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.platform.AMServices

class ManaGenerationRecipe(id: ResourceLocation, ingredient: Ingredient, result: ItemStack,
                           chance: Int, experience: Float, time: Int
) : AMAbstractChancedRecipe(
    AMRecipeTypes.instance!!.manaRecipe, id,
    ingredient,
    result, chance, experience, time
) {
    override fun getSerializer(): RecipeSerializer<*> = AMServices.PLATFORM.getManaRecipeSerializer()

}