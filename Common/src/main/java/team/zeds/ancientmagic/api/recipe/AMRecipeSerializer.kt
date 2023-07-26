package team.zeds.ancientmagic.api.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapedRecipe
import team.zeds.ancientmagic.api.recipe.ingredient.IngredientHelper

class AMRecipeSerializer<T: AMAbstractRecipe>(private val serial: (ResourceLocation, Ingredient, ItemStack, Float?, Int?) -> T):
    RecipeSerializer<T> {
    override fun fromJson(resourceLocation: ResourceLocation, jsonObject: JsonObject): T {
        val jsonIngredient = if (GsonHelper.isArrayNode(jsonObject, "ingredients")) GsonHelper.getAsJsonArray(
            jsonObject,
            "ingredients"
        ) else GsonHelper.getAsJsonObject(jsonObject, "ingredients")
        val ingredient = IngredientHelper.fromJson(jsonIngredient)!!

        if (!jsonObject.has("result"))
            throw JsonSyntaxException(
                "RECIPE HASN'T BEEN CREATED! MISSING ARGUMENT: 'result'"
            )
        val itemStack = if (jsonObject.get("result").isJsonObject)
            ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"))
        else {
            val string: String = GsonHelper.getAsString(jsonObject, "result")
            val loc = ResourceLocation(string)
            ItemStack(BuiltInRegistries.ITEM.get(loc))
        }

        val xp: Float = GsonHelper.getAsFloat(jsonObject, "xp", 0.0F)
        val time: Int = GsonHelper.getAsInt(jsonObject, "time", 0)
        return this.getSerializer().invoke(resourceLocation, ingredient, itemStack, xp, time)
    }

    override fun fromNetwork(p0: ResourceLocation, p1: FriendlyByteBuf): T {
        TODO("Not yet implemented")
    }

    override fun toNetwork(p0: FriendlyByteBuf, p1: T) {
        TODO("Not yet implemented")
    }

    fun getSerializer(): (ResourceLocation, Ingredient, ItemStack, Float?, Int?) -> T = this.serial
}