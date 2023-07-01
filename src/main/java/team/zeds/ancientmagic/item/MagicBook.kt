package team.zeds.ancientmagic.item

import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.client.MagicBookScreen

class MagicBook : MagicItem(this.callBuilder().setStacks(1)) {
    override fun useMT(level: Level, player: Player, hand: InteractionHand) {
        if (level.isClientSide) {
            Minecraft.getInstance().setScreen(MagicBookScreen())
            return
        } else return
    }
}