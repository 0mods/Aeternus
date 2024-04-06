package team._0mods.multilib.fabric.mixin

import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal
import net.minecraft.world.entity.animal.horse.AbstractHorse
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(RunAroundLikeCrazyGoal::class)
class HorseTame {
    @Shadow
    @Final
    private lateinit var horse: AbstractHorse

    @Inject(
        method = ["tick"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;tameWithName(Lnet/minecraft/world/entity/player/Player;)Z"
        )],
        cancellable = true
    )
    private fun tick(ci: CallbackInfo) {
        if (EntityEvent.ANIMAL_TAME.event.tame(this.horse, ((this.horse.passengers[0]) as Player)).isFalse) {
            ci.cancel()
        }
    }
}