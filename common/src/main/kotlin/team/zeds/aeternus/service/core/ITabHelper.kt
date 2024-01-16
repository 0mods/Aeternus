package team.zeds.aeternus.service.core

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike

interface ITabHelper {
    fun addItemToTab(item: ItemLike?, tab: ResourceKey<CreativeModeTab>?)

    fun removeFromTab(item: ItemLike?, tab: ResourceKey<CreativeModeTab>?)

    fun removeFromAllTabs(item: ItemLike?)
}