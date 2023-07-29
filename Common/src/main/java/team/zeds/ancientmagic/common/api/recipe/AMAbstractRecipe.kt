package team.zeds.ancientmagic.common.api.recipe

import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import org.jetbrains.annotations.Nullable
import team.zeds.ancientmagic.common.api.recipe.ingredient.IngredientHelper

abstract class AMAbstractRecipe(
    val recipeType: RecipeType<*>,
    val resloc: ResourceLocation,
    val ingredient: Ingredient,
    val result: ItemStack,
    @Nullable val experience: Float,
    @Nullable val time: Int
): Recipe<Container> {
    override fun matches(container: Container, level: Level): Boolean = IngredientHelper.test(this.ingredient, container)

    override fun assemble(container: Container, access: RegistryAccess): ItemStack = this.result.copy()

    override fun canCraftInDimensions(p_43999_: Int, p_44000_: Int): Boolean = true

    override fun getResultItem(access: RegistryAccess): ItemStack = this.result.copy()

    override fun getIngredients(): NonNullList<Ingredient> {
        val nonNullList: NonNullList<Ingredient> = NonNullList.create()
        nonNullList.add(this.ingredient)
        return nonNullList
    }

    override fun getId(): ResourceLocation = this.resloc

    override fun getType(): RecipeType<*> = this.recipeType

    override fun toString(): String = "AMRecipe{" +
            "type=${type}" +
            ", id=${resloc}" +
            ", ingredient=${ingredient}" +
            ", result=${result}" +
            ", experience=${experience}" +
            ", time=${time}" +
            "}"
}