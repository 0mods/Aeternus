package team._0mods.aeternus.init.registry

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.resloc
import java.util.function.BiConsumer
import java.util.function.Supplier

object AeternusItems {
    private val items = mutableMapOf<ResourceLocation, Item>()

    val testItem = register("test_item", Item(Item.Properties()))

    fun handleItems(cons: BiConsumer<Item, ResourceLocation>) {
        items.entries.forEach { i -> cons.accept(i.value, i.key) }
    }

    private fun <T: Item> register(id: String, obj: T): T {
        val old = items.put(resloc(ModId, id), obj)
        if (old != null)
            throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")
        return obj
    }
}