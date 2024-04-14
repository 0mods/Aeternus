package team._0mods.multilib.fabric.mixin

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.InteractionEvent
import team._0mods.multilib.event.base.common.PlayerEvent
import team._0mods.multilib.event.base.common.TickEvent

@Mixin(Player::class)
class PlayerMixin {
    @Inject(method = ["tick"], at = [At("HEAD")])
    private fun preTick(ci: CallbackInfo) {
        TickEvent.PLAYER_PRE.event.tick(this as Player)
    }

    @Inject(method = ["tick"], at = [At("RETURN")])
    private fun postTick(ci: CallbackInfo) {
        TickEvent.PLAYER_POST.event.tick(this as Player)
    }

    @Inject(
        method = ["drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;"],
        at = [At("RETURN")],
        cancellable = true
    )
    private fun drop(itemStack: ItemStack, bl: Boolean, bl2: Boolean, cir: CallbackInfoReturnable<ItemEntity?>) {
        if (cir.returnValue != null && PlayerEvent.DROP_ITEM.event.drop(this as Player, cir.returnValue!!).isFalse) {
            cir.setReturnValue(null)
        }
    }

    @Inject(
        method = ["interactOn"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;",
            ordinal = 0
        )],
        cancellable = true
    )
    private fun entityInteract(
        entity: Entity,
        interactionHand: InteractionHand,
        cir: CallbackInfoReturnable<InteractionResult>
    ) {
        val result = InteractionEvent.INTERACT_ENTITY.event.interact(this as Player, entity, interactionHand)
        if (result.isPresent) {
            cir.setReturnValue(result.asInteractionResult)
        }
    }
}
