package team.zeds.ancientmagic.api.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraftforge.registries.ForgeRegistries
import team.zeds.ancientmagic.api.recipe.ingredient.IngredientHelper

class AMRecipeSerializer<T : AMAbstractRecipe>(val serial: SerializerFactory<T>): RecipeSerializer<T> {
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
            ItemStack(ForgeRegistries.ITEMS.getValue(loc)!!)
        }

        val xp: Float = GsonHelper.getAsFloat(jsonObject, "xp", 0.0F)
        val time: Int = GsonHelper.getAsInt(jsonObject, "time", 0)
        return this.getSerializer().create(resourceLocation, ingredient, itemStack, xp, time)
    }

    override fun fromNetwork(resourceLocation: ResourceLocation, byteBuf: FriendlyByteBuf): T {
        val ingredient = Ingredient.fromNetwork(byteBuf)
        val itemStack: ItemStack = byteBuf.readItem()
        val xp: Float = byteBuf.readFloat()
        val time: Int = byteBuf.readInt()
        return this.getSerializer().create(resourceLocation, ingredient, itemStack, xp, time)
    }

    override fun toNetwork(byteBuf: FriendlyByteBuf, value: T) {
        value.ingredient.toNetwork(byteBuf)
        byteBuf.writeItem(value.result)
        byteBuf.writeFloat(value.experience)
        byteBuf.writeInt(value.time)
    }

    fun getSerializer(): SerializerFactory<T> {
        return this.serial
    }

    @FunctionalInterface
    interface SerializerFactory<T : AMAbstractRecipe> {
        fun create(id: ResourceLocation, ingredient: Ingredient, result: ItemStack, xp: Float?, time: Int?): T
    }
}