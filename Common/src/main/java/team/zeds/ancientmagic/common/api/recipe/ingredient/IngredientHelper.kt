package team.zeds.ancientmagic.common.api.recipe.ingredient

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.Container
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.stream.Stream
import java.util.stream.StreamSupport

object IngredientHelper {
    fun <T: Container> test(ingredient: Ingredient, inventory: T): Boolean {
        if (ingredient.items.size != inventory.containerSize) return false
        for (i in 0 until inventory.containerSize) {
            val ingredientItem = ingredient.items[i]
            val inputItem: ItemStack = inventory.getItem(i)
            if (ingredientItem.item != inputItem.item || ingredientItem.count > inputItem.count) {
                return false
            }
        }
        return true
    }

    fun getStack(`object`: JsonObject): ItemStack {
        var count = 1
        if (`object`.has("count")) {
            count = GsonHelper.getAsInt(`object`, "count")
        }
        val itemLocation = ResourceLocation(GsonHelper.getAsString(`object`, "item"))
        val item: Item = BuiltInRegistries.ITEM.get(itemLocation)
        return ItemStack(item, count)
    }

    fun fromJson(jsonElement: JsonElement): Ingredient? {
        if (jsonElement.isJsonArray) {
            return Ingredient.fromValues(
                StreamSupport.stream(jsonElement.asJsonArray.spliterator(), false)
                    .map { `object` ->
                        val stack = getStack(`object`.asJsonObject)
                        Ingredient.ItemValue(stack)
                    }
            )
        } else if (jsonElement.isJsonObject) {
            val `object`: JsonObject = jsonElement.asJsonObject
            return Ingredient.fromValues(Stream.of(Ingredient.ItemValue(getStack(`object`))))
        }
        return null
    }
}