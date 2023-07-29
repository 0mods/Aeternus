package team.zeds.ancientmagic.common.api.cap

import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.Nullable
import team.zeds.ancientmagic.common.api.magic.MagicType

interface ItemStackMagic {
    fun getMagicType(): MagicType
    @Nullable fun getMagicSubtype(): MagicType
    fun getMaxMana(): Int
    fun getManaStorages(stack: ItemStack): Int
    fun setManaStorages(mana: Int, stack: ItemStack)
    fun addMana(count: Int, stack: ItemStack)
    fun subMana(count: Int, stack: ItemStack)
}