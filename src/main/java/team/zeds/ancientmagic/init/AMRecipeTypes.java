package team.zeds.ancientmagic.init;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import team.zeds.ancientmagic.recipes.AltarRecipe;
import team.zeds.ancientmagic.recipes.CastRecipeType;

@SuppressWarnings("SameParameterValue")
public class AMRecipeTypes {
    public static final RecipeType<AltarRecipe> ALTAR_RECIPE = register("altar_recipe");

    private static <T extends Recipe<?>> CastRecipeType<T> register(String recipeId) {
        return new CastRecipeType<>(recipeId);
    }
}
