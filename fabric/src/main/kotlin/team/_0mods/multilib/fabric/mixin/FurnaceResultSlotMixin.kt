package team._0mods.multilib.fabric.mixin

import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.FurnaceResultSlot
import net.minecraft.world.item.ItemStack
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.common.PlayerEvent

@Mixin(FurnaceResultSlot::class)
class FurnaceResultSlotMixin {
    @Shadow
    @Final
    private val player: Player? = null

    @Inject(method = ["checkTakeAchievements"], at = [At("RETURN")])
    private fun checkTakeAchievements(itemStack: ItemStack, ci: CallbackInfo) {
        PlayerEvent.SMELT_ITEM.event.smelt(player!!, itemStack)
    }
}