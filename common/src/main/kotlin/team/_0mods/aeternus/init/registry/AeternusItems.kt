package team._0mods.aeternus.init.registry

import net.minecraft.world.item.Item
import java.util.function.Function

object AeternusItems {
    internal val items = mutableMapOf<String, Function<Item.Properties, out Item>>()

    val testItem = register("test_item", ::Item)

    @JvmStatic
    fun getItemsForRegistry() = this.items
}