package team.zeds.aeternus.provider.core

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item

interface ITabHelper {
    fun addItemToTab(item: Item?, tab: CreativeModeTab?)

    fun removeFromTab(item: Item?, tab: CreativeModeTab?)

    fun removeFromAllTabs(item: Item?)
}