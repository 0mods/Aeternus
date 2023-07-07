package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import team.zeds.ancientmagic.api.recipe.AMAbstractChancedRecipe
import team.zeds.ancientmagic.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.init.registries.AMRegister

class ManaGenerationRecipe(
    idChanced: ResourceLocation,
    ingredientChanced: Ingredient,
    resultChanced: ItemStack,
    chance: Int,
    experienceChanced: Float,
    timeChanced: Int
) : AMAbstractChancedRecipe(
    AMRecipeTypes.MANA_GEN_RECIPE,
    idChanced,
    ingredientChanced,
    resultChanced,
    chance,
    experienceChanced,
    timeChanced
) {
    override fun getSerializer(): RecipeSerializer<*> = AMRegister.MANA_RECIPE_SERIAL.get()
}