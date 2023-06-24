package team.zeromods.ancientmagic.item

import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import team.zeromods.ancientmagic.client.MagicBookScreen

class MagicBook : Item(Properties().stacksTo(1)) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        return if (level.isClientSide) {
            Minecraft.getInstance().setScreen(MagicBookScreen())
            return InteractionResultHolder.success(player.getItemInHand(hand))
        } else InteractionResultHolder.pass(player.getItemInHand(hand))
    }
}