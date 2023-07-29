package team.zeds.ancientmagic.common.api.helper

import net.minecraft.world.item.ItemStack

interface IStackHelper {
    fun iWithSize(stack: ItemStack, size: Int, container: Boolean): ItemStack

    fun iGrow(stack: ItemStack, amount: Int): ItemStack

    fun iShrink(stack: ItemStack, amount: Int, container: Boolean): ItemStack

    fun iAreItemsEqual(stack1: ItemStack, stack2: ItemStack): Boolean

    fun iAreStacksEqual(stack1: ItemStack, stack2: ItemStack): Boolean

    fun iCanCombineStacks(stack1: ItemStack, stack2: ItemStack): Boolean

    fun iCombineStacks(stack1: ItemStack, stack2: ItemStack): ItemStack

    fun iCompareTags(stack1: ItemStack, stack2: ItemStack): Boolean
}