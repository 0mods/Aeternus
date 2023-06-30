package team.zeds.ancientmagic.item

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraftforge.common.capabilities.ICapabilityProvider
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.item.MagicItemBuilder
import team.zeds.ancientmagic.api.atomic.KAtomicUse
import team.zeds.ancientmagic.api.magic.MagicTypes
import team.zeds.ancientmagic.compact.CompactInitializer
import team.zeds.ancientmagic.compact.curios.AMCurio

class RetraceStone : MagicItem(
    MagicItemBuilder.get()
        .setMagicType(MagicTypes.PRE_HIGH_MAGIC)
        .fireProof()
        .setRarity(Rarity.RARE)
) {
    override fun use(use: KAtomicUse<ItemStack>) {
        if (use.player.isShiftKeyDown) setActive(use.stack, !getActive(use.stack))
    }

    override fun isFoil(p_41453_: ItemStack): Boolean {
        return getActive(p_41453_)
    }

    override fun initCapabilities(stack: ItemStack?, nbt: CompoundTag?): ICapabilityProvider? {
        return if (CompactInitializer.getCuriosLoaded()) AMCurio.RetraceStoneEventProvider else super.initCapabilities(
            stack,
            nbt
        )
    }

    companion object {
        @JvmStatic
        fun getActive(stack: ItemStack): Boolean {
            val active = stack.getOrCreateTag().getBoolean("RetraceStoneIsActivated")
            return !stack.hasTag() || active
        }
        @JvmStatic
        fun setActive(stack: ItemStack, active: Boolean) {
            stack.getOrCreateTag().putBoolean("RetraceStoneIsActivated", active)
        }
    }
}