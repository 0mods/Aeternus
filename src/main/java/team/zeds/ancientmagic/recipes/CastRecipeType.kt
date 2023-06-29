package team.zeds.ancientmagic.recipes

import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import team.zeds.ancientmagic.api.mod.Constant

@JvmRecord
data class CastRecipeType<T: Recipe<*>>(val recipeName: String) : RecipeType<T> {
    override fun toString(): String {
        return "${Constant.KEY}:${recipeName}"
    }
}