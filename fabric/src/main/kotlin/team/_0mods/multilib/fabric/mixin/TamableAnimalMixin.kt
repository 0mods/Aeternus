package team._0mods.multilib.fabric.mixin

import net.minecraft.world.entity.TamableAnimal
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(TamableAnimal::class)
class TamableAnimalMixin {
    @Inject(method = ["tame"], at = [At(value = "HEAD")], cancellable = true)
    private fun tame(player: Player, ci: CallbackInfo) {
        if (EntityEvent.ANIMAL_TAME.event.tame(this as Animal, player).isFalse) {
            ci.cancel()
        }
    }
}
