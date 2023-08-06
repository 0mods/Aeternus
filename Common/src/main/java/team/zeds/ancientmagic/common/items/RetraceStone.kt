package team.zeds.ancientmagic.common.items

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import team.zeds.ancientmagic.common.api.item.MagicItem
import team.zeds.ancientmagic.common.api.magic.type.MagicTypes

class RetraceStone: MagicItem(
    of()
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