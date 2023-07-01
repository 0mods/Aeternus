package team.zeds.ancientmagic.api.cap

import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.Nullable
import team.zeds.ancientmagic.api.magic.MagicType

interface ItemStackMagic {
    fun getMagicType(): MagicType

    @Nullable
    fun getMagicSubtype(): MagicType

    fun getMaxMana(): Int

    fun getStorageMana(stack: ItemStack): Int

    fun setStorageMana(mana: Int, stack: ItemStack)

    fun addMana(count: Int, stack: ItemStack)

    fun subMana(count: Int, stack: ItemStack)
}