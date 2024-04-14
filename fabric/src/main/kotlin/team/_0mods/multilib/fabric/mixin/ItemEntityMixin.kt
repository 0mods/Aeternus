package team._0mods.multilib.fabric.mixin

import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.common.PlayerEvent

@Mixin(ItemEntity::class)
abstract class ItemEntityMixin {
    @Shadow
    abstract fun getItem(): ItemStack

    @Unique
    private var cache: ItemStack? = null

    @Inject(
        method = ["playerTouch"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCount()I")],
        cancellable = true
    )
    private fun prePickup(player: Player, ci: CallbackInfo) {
        val obj = this as Any
        cache = getItem().copy()
        val canPickUp = PlayerEvent.PICKUP_ITEM_PRE.event.canPickup(
            player,
            obj as ItemEntity, getItem()
        )
        if (canPickUp.isFalse) {
            ci.cancel()
        }
    }

    @Inject(
        method = ["playerTouch"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;take(Lnet/minecraft/world/entity/Entity;I)V"
        )]
    )
    private fun pickup(player: Player, ci: CallbackInfo) {
        val obj = this as Any
        if (cache != null) {
            PlayerEvent.PICKUP_ITEM_POST.event.pickup(
                player,
                obj as ItemEntity, cache!!
            )
        }

        this.cache = null
    }
}