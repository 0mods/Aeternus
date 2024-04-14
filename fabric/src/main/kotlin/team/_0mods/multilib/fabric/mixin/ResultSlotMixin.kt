package team._0mods.multilib.fabric.mixin

import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.inventory.ResultSlot
import net.minecraft.world.item.ItemStack
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.common.PlayerEvent

@Mixin(ResultSlot::class)
class ResultSlotMixin {
    @Shadow
    @Final
    private lateinit var player: Player

    @Shadow
    @Final
    private lateinit var craftSlots: CraftingContainer

    @Inject(
        method = ["checkTakeAchievements"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;onCraftedBy(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;I)V",
            shift = At.Shift.AFTER
        )]
    )
    private fun craft(itemStack: ItemStack, ci: CallbackInfo) {
        PlayerEvent.CRAFT_ITEM.event.craft(player, itemStack, craftSlots)
    }
}
