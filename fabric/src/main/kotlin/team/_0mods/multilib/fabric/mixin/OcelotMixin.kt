package team._0mods.multilib.fabric.mixin

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.animal.Ocelot
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(Ocelot::class)
class OcelotMixin {
    @Inject(
        method = ["mobInteract"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Ocelot;setTrusting(Z)V")],
        cancellable = true
    )
    private fun mobInteract(player: Player, hand: InteractionHand, cir: CallbackInfoReturnable<InteractionResult>) {
        val obj = this as Any
        if (EntityEvent.ANIMAL_TAME.event.tame(obj as Animal, player).isFalse) {
            cir.setReturnValue(InteractionResult.PASS)
        }
    }
}
