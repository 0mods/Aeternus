package team._0mods.aeternus.init.registry

import net.minecraft.world.item.Item
import java.util.function.Function

object AeternusItems {
    internal val items = mutableMapOf<String, Function<Item.Properties, out Item>>()

    val knowledgeBook = registerItem("knowledge_book", ::Item)

    @JvmStatic
    fun getItemsForRegistry() = this.items
}