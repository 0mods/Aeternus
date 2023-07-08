package team.zeds.ancientmagic.item

import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.screen.MagicBookScreen
import team.zeds.ancientmagic.client.MagicBookMainScreen

class MagicBook : MagicItem(callBuilder().stacks(1).setMaxMana(0)) {
    override fun useMT(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        return if (level.isClientSide) {
            Minecraft.getInstance().setScreen(MagicBookMainScreen())
            InteractionResultHolder.success(player.getItemInHand(hand))
        } else InteractionResultHolder.pass(player.getItemInHand(hand))
    }
}