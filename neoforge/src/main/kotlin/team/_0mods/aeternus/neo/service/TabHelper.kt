package team._0mods.aeternus.neo.service

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.level.ItemLike
import team._0mods.aeternus.neo.api.tab.AddItemToTabHandler
import team._0mods.aeternus.service.core.ITabHelper

class TabHelper: ITabHelper {
    override fun addItemToTab(item: ItemLike?, tab: ResourceKey<CreativeModeTab>?) {
        AddItemToTabHandler.mapOfItemsAndTabs[item] = tab
    }

    override fun removeFromTab(item: ItemLike?, tab: ResourceKey<CreativeModeTab>?) {}

    override fun removeFromAllTabs(item: ItemLike?) {}
}