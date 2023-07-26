package team.zeds.ancientmagic.api.helper

import net.minecraft.world.item.ItemStack

interface IStackHelper {
    fun withSize(stack: ItemStack, size: Int, container: Boolean): ItemStack

    fun grow(stack: ItemStack, amount: Int): ItemStack

    fun shrink(stack: ItemStack, amount: Int, container: Boolean): ItemStack

    fun areItemsEqual(stack1: ItemStack, stack2: ItemStack): Boolean

    fun areStacksEqual(stack1: ItemStack, stack2: ItemStack): Boolean

    fun canCombineStacks(stack1: ItemStack, stack2: ItemStack): Boolean

    fun combineStacks(stack1: ItemStack, stack2: ItemStack): ItemStack

    fun compareTags(stack1: ItemStack, stack2: ItemStack): Boolean
}