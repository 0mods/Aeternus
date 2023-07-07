package team.zeds.ancientmagic.api.recipe

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
import team.zeds.ancientmagic.api.recipe.ingredient.IngredientHelper

abstract class AMAbstractRecipe(
    @JvmField val recipeType: RecipeType<*>,
    @JvmField val id: ResourceLocation,
    @JvmField val ingredient: Ingredient,
    @JvmField val result: ItemStack,
    @JvmField @Nullable val experience: Float,
    @JvmField @Nullable val time: Int
    ): Recipe<Container> {
    override fun matches(container: Container, level: Level): Boolean {
        return IngredientHelper.test(this.ingredient, container)
    }

    override fun assemble(container: Container, access: RegistryAccess): ItemStack = this.result.copy()

    override fun canCraftInDimensions(p_43999_: Int, p_44000_: Int): Boolean = true

    override fun getResultItem(access: RegistryAccess): ItemStack = this.result.copy()

    override fun getIngredients(): NonNullList<Ingredient> {
        val nonNullList: NonNullList<Ingredient> = NonNullList.create()
        nonNullList.add(this.ingredient)
        return nonNullList
    }

    override fun getId(): ResourceLocation = this.id

    override fun getType(): RecipeType<*> = this.recipeType

    override fun toString(): String = "AMRecipe{" +
            "type=${type}" +
            ", id=${id}" +
            ", ingredient=${ingredient}" +
            ", result=${result}" +
            ", experience=${experience}" +
            ", time=${time}" +
            "}"
}
