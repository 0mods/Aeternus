package team._0mods.aeternus.neo.mixin

import net.neoforged.bus.EventBus
import net.neoforged.bus.api.Event
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.aeternus.LOGGER

@Mixin(EventBus::class)
class EventBusMixin {
    @Inject(
        method = ["post(Lnet/neoforged/bus/api/Event;)Lnet/neoforged/bus/api/Event;"],
        at = [At("HEAD")],
        remap = false
    )
    fun <T : Event?> inj(event: T, cir: CallbackInfoReturnable<T>?) {
        LOGGER.info("Mixins are works!")
    }
}