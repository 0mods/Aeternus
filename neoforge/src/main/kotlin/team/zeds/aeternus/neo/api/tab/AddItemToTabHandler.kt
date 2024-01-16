package team.zeds.aeternus.neo.api.tab

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import team.zeds.aeternus.init.LOGGER
import team.zeds.aeternus.neo.api.forgeEvent
import team.zeds.aeternus.neo.api.modEvent

object AddItemToTabHandler {
    val mapOfItemsAndTabs: MutableMap<ItemLike?, ResourceKey<CreativeModeTab>?> = mutableMapOf()

    fun init() {
        modEvent<BuildCreativeModeTabContentsEvent> {
            mapOfItemsAndTabs.forEach { entry ->
                val tab = entry.value
                val item = entry.key

                if (tab != null && it.tabKey == tab) {
                    if (item != null)
                        it.accept(item)
                    else LOGGER.warn("Failed add item to tab {}, because item is null!", tab.registry())
                }
            }
        }
    }
}