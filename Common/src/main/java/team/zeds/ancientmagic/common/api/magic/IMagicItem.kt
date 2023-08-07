package team.zeds.ancientmagic.common.api.magic

import net.minecraft.world.item.ItemStack

interface IMagicItem: IMagicObject<ItemStack>, IMagic {
    fun canStackItemsWithManaStorage(stack: ItemStack, stack0: ItemStack): Boolean {
        val item = stack.item
        val item0 = stack0.item
        if (
            item is IMagicItem
            && item == item0
            && (item as IMagicItem).getManaStorage(stack) == (item0 as IMagicItem).getManaStorage(stack0)
            && (item as IMagicItem).getMaxManaStorage(stack) == (item0 as IMagicItem).getMaxManaStorage(stack0)
        ) {
            val x = stack0.count - (stack.maxStackSize - stack.count)
            stack.grow(x)
            stack0.shrink(x)
            return true
        }

        return false
    }

    fun setMaxMana(): Long
}