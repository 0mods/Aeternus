package team.zeds.ancientmagic.recipes

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import team.zeds.ancientmagic.api.recipe.AMAbstractChancedRecipe
import team.zeds.ancientmagic.init.registries.AMRecipeTypes
import team.zeds.ancientmagic.init.registries.AMRegister

class ManaGenerationRecipe(id: ResourceLocation, ingr: Ingredient, result: ItemStack, chance: Int, exp: Float, time: Int): AMAbstractChancedRecipe(
    AMRecipeTypes.instance.manaRecipe,
    id,
    ingr,
    result,
    chance,
    exp,
    time
){
    override fun getSerializer(): RecipeSerializer<*> = AMRegister.MANA_RECIPE_SERIAL.get()
}