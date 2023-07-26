package team.zeds.ancientmagic.api.recipe

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import team.zeds.ancientmagic.AMConstant

@JvmRecord
data class CastRecipeType<T: Recipe<*>>(val recipeName: String) : RecipeType<T> {
    override fun toString(): String = ResourceLocation(AMConstant.KEY, recipeName).toString()
}