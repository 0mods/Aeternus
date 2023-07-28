package team.zeds.ancientmagic.api.recipe

import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeType
import kotlin.random.Random

abstract class AMAbstractChancedRecipe(
    recipeType: RecipeType<*>,
    id: ResourceLocation,
    private val ingredientC: Ingredient,
    private val resultC: ItemStack,
    private val chance: Int,
    private val experienceC: Float,
    private val timeC: Int

): AMAbstractRecipe(
    recipeType,
    id,
    ingredientC,
    resultC,
    experienceC,
    timeC
) {
    val numberArray: MutableList<Double> = mutableListOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0)

    override fun assemble(container: Container, access: RegistryAccess): ItemStack {
        val chance: Double = this.chance / 100.0
        val random = Random.nextInt(numberArray.size)
        val objs = numberArray[random]
        return if (objs >= chance) super.assemble(container, access) else ItemStack.EMPTY
    }

    fun getChance(): Int = this.chance

    override fun toString(): String = "AMChanceRecipe{" +
            "type=${type}" +
            ", id=${resloc}" +
            ", ingredient=${ingredientC}" +
            ", result=${resultC}" +
            ", chance=${chance}" +
            ", experience=${experienceC}" +
            ", time=${timeC}" +
            "}"
}