package team.zeds.ancientmagic.api.recipe

import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeType
import kotlin.random.Random

abstract class AMAbstractChancedRecipe(
    recipeTypeChanced: RecipeType<*>,
    idChanced: ResourceLocation,
    ingredientChanced: Ingredient,
    resultChanced: ItemStack,
    val chance: Int,
    experienceChanced: Float,
    timeChanced: Int

): AMAbstractRecipe(
    recipeTypeChanced,
    idChanced,
    ingredientChanced,
    resultChanced,
    experienceChanced,
    timeChanced
) {
    override fun getResultItem(access: RegistryAccess): ItemStack {
        val chance: Double = chance / 100.0
        val randomized = Random.nextDouble(0.0, 1.0)
        return if (randomized >= chance) this.result.copy() else ItemStack.EMPTY
    }

    override fun toString(): String = "AMRecipe{" +
            "type=${type}" +
            ", id=${id}" +
            ", ingredient=${ingredient}" +
            ", result=${result}" +
            ", chance=${chance}" +
            ", experience=${experience}" +
            ", time=${time}" +
            "}"
}