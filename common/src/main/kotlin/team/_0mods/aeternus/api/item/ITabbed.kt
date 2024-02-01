package team._0mods.aeternus.api.item

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.level.ItemLike

val someTabList: List<ItemLike> = listOf()

interface ITabbed {
    fun addContents(like: ItemLike, output: CreativeModeTab.Output)
}