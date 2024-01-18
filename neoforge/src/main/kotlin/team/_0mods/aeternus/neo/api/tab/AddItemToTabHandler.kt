package team._0mods.aeternus.neo.api.tab

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import team._0mods.aeternus.init.LOGGER
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object AddItemToTabHandler {
    val mapOfItemsAndTabs: MutableMap<ItemLike?, ResourceKey<CreativeModeTab>?> = mutableMapOf()

    fun init() {
        MOD_BUS.addListener(this::handleAndPutItems)
    }

    private fun handleAndPutItems(it: BuildCreativeModeTabContentsEvent) {
        if (mapOfItemsAndTabs.isNotEmpty()) {
            mapOfItemsAndTabs.forEach { entry ->
                val tab = entry.value
                val item = entry.key

                if (tab != null && it.tabKey == tab) {
                    if (item != null) it.accept(item)
                    else LOGGER.warn("Failed add item to tab {}, because item is null!", tab.registry())
                }
            }
        }
    }
}
