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

class AMChancedRecipeSerializer<Y: Container, T : AMAbstractChancedRecipe<Y>>(val serial: (ResourceLocation, Ingredient, ItemStack, Int, Float?, Int?) -> T) : RecipeSerializer<T> {
    override fun fromJson(resourceLocation: ResourceLocation, jsonObject: JsonObject): T {
        val jsonIngredient = if (GsonHelper.isArrayNode(jsonObject, "ingredients")) GsonHelper.getAsJsonArray(
            jsonObject,
            "ingredients"
        ) else GsonHelper.getAsJsonObject(jsonObject, "ingredients")

        if (!jsonObject.has("result"))
            throw JsonSyntaxException(
                "${AMRecipeSerializer::class.java}; - has a message: \"RECIPE CAN'T BEEN CREATED! MISSING ARGUMENT: 'result'\""
            )

        val itemStack = if (jsonObject.get("result").isJsonObject)
            ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"))
        else {
            val string: String = GsonHelper.getAsString(jsonObject, "result")
            val loc = ResourceLocation(string)
            ItemStack(ForgeRegistries.ITEMS.getValue(loc)!!)
        }
        val ingredient = IngredientHelper.fromJson(jsonIngredient)!!

        val chance = GsonHelper.getAsInt(jsonObject, "chance", 100)
        val xp: Float = GsonHelper.getAsFloat(jsonObject, "xp", 0.0F)
        val time: Int = GsonHelper.getAsInt(jsonObject, "time", 0)

        return this.getSerializer().invoke(resourceLocation, ingredient, itemStack, chance, xp, time)
    }

    override fun fromNetwork(resourceLocation: ResourceLocation, byteBuf: FriendlyByteBuf): T {
        val ingredient = Ingredient.fromNetwork(byteBuf)
        val itemStack = byteBuf.readItem()
        val chance = byteBuf.readInt()
        val xp = byteBuf.readFloat()
        val time = byteBuf.readInt()
        return this.serial.invoke(resourceLocation, ingredient, itemStack, chance, xp, time)
    }

    override fun toNetwork(byteBuf: FriendlyByteBuf, value: T) {
        value.ingredient.toNetwork(byteBuf)
        byteBuf.writeItem(value.result)
        byteBuf.writeInt(value.chance)
        byteBuf.writeFloat(value.experience)
        byteBuf.writeInt(value.time)
    }

    fun getSerializer(): (ResourceLocation, Ingredient, ItemStack, Int, Float?, Int?) -> T = this.serial
}