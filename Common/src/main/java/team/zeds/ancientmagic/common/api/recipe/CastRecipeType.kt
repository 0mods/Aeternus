package team.zeds.ancientmagic.common.api.recipe

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import team.zeds.ancientmagic.common.AMConstant

@JvmRecord
data class CastRecipeType<T: Recipe<*>>(val recipeName: String) : RecipeType<T> {
    override fun toString(): String = ResourceLocation(AMConstant.KEY, recipeName).toString()
}