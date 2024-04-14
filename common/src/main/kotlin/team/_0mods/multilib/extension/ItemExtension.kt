package team._0mods.multilib.extension

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

interface ItemExtension {
    fun tickArmor(stack: ItemStack, player: Player) {}

    fun getCustomEquipSlot(stack: ItemStack): EquipmentSlot? = null
}