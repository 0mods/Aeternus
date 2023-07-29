package team.zeds.ancientmagic.common.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import team.zeds.ancientmagic.common.api.recipe.AMAbstractChancedRecipe
import team.zeds.ancientmagic.common.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.common.platform.AMServices

class ManaGenerationRecipe(id: ResourceLocation, ingredient: Ingredient, result: ItemStack,
                           chance: Int, experience: Float, time: Int
) : AMAbstractChancedRecipe(
    AMRecipeTypes.instance!!.manaRecipe, id,
    ingredient,
    result, chance, experience, time
) {
    override fun getSerializer(): RecipeSerializer<*> = AMServices.PLATFORM.getIAMRegistryEntry().getManaRecipeSerializer()

}