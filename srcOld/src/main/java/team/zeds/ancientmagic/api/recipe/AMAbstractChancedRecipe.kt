package team.zeds.ancientmagic.api.recipe

import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
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
    val numberArray: MutableList<Double> = mutableListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)

    override fun assemble(container: Container, access: RegistryAccess): ItemStack {
        val chance: Double = chance / 100.0
        val random = Random.nextInt(numberArray.size)
        val objectFromList = numberArray[random]
        return if (objectFromList >= chance) this.result.copy() else ItemStack.EMPTY.copy()
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