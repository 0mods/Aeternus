package team.zeds.ancientmagic.item

import net.minecraft.client.Minecraft
import net.minecraft.world.item.ItemStack
import team.zeds.ancientmagic.api.item.MagicItem
import team.zeds.ancientmagic.api.item.MagicItemBuilder
import team.zeds.ancientmagic.api.atomic.KAtomicUse
import team.zeds.ancientmagic.client.MagicBookScreen

class MagicBook : MagicItem(MagicItemBuilder.get().setStacks(1)) {
    override fun use(use: KAtomicUse<ItemStack>) {
        if (use.level.isClientSide) {
            Minecraft.getInstance().setScreen(MagicBookScreen())
            use.setSuccess(use.player.getItemInHand(use.hand))
        } else use.setConsume(use.player.getItemInHand(use.hand))
    }
}