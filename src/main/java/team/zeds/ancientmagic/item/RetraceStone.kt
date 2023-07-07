package team.zeds.ancientmagic.item

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.ICapabilityProvider
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.magic.MagicTypes
import team.zeds.ancientmagic.compact.CompactInitializer
import team.zeds.ancientmagic.compact.curios.AMCurio

class RetraceStone : MagicItem(
    callBuilder()
        .setMagicType(MagicTypes.PRE_HIGH_MAGIC)
        .fireProof()
        .setRarity(Rarity.RARE)
) {
    override fun useMT(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        return if (player.isShiftKeyDown) {
            setActive(player.getItemInHand(hand), !getActive(player.getItemInHand(hand)))
            InteractionResultHolder.success(player.getItemInHand(hand))
        } else {
            InteractionResultHolder.consume(player.getItemInHand(hand))
        }
    }

    override fun isFoil(itemStack: ItemStack): Boolean {
        return getActive(itemStack)
    }

    override fun initCapabilities(stack: ItemStack, nbt: CompoundTag?): ICapabilityProvider? {
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