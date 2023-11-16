package team.zeds.aeternus.api

import net.minecraft.world.item.ItemStack

interface IStackHandler {
    fun slots(): Int

    fun stackInSlot(slot: Int): ItemStack

    fun insertItems(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack

    fun extractItems(slot: Int, amount: Int, simulate: Boolean): ItemStack

    fun slotLimit(slot: Int): Int

    fun isItemsValid(slot: Int, stack: ItemStack): Boolean
}