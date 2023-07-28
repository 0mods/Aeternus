package team.zeds.ancientmagic.items

import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.client.screen.MagicScreen

class MagicBook: MagicItem(of().stacks(1).setMaxMana(0)) {
    override fun useMT(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        return if (level.isClientSide) {
            Minecraft.getInstance().setScreen(MagicScreen())
            InteractionResultHolder.success(stack)
        } else InteractionResultHolder.pass(stack)
    }
}