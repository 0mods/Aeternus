package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.multiplayer.MultiPlayerGameMode
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.InteractionEvent

@Mixin(MultiPlayerGameMode::class)
class MultiPlayerGameModeMixin {
    @Inject(
        method = ["interact"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V",
            shift = At.Shift.AFTER
        )],
        cancellable = true
    )
    private fun entityInteract(
        player: Player,
        entity: Entity,
        interactionHand: InteractionHand,
        cir: CallbackInfoReturnable<InteractionResult>
    ) {
        val result = InteractionEvent.INTERACT_ENTITY.event.interact(player, entity, interactionHand)
        if (result.isPresent) {
            cir.setReturnValue(result.asInteractionResult)
        }
    }
}