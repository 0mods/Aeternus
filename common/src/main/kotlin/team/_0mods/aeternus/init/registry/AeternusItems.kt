package team._0mods.aeternus.init.registry

import net.minecraft.world.item.Item
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.resloc
import java.util.function.Function
import java.util.function.Supplier

object AeternusItems {
    val items = mutableMapOf<String, Function<Item.Properties, out Item>>()

    val testItem = register("test_item", ::Item)

    private fun <T: Item> register(id: String, obj: (Item.Properties) -> T, props: Item.Properties = Item.Properties()): Supplier<T> {
        if (items.putIfAbsent(id, obj) != null)
            throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
        return Supplier { obj.invoke(props) }
    }
}