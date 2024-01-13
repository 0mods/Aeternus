package team.zeds.aeternus.api.block.blockentity

import net.minecraft.world.item.ItemStack

interface IStorable {
    fun fromStorageToStack(stack: ItemStack): ItemStack
}