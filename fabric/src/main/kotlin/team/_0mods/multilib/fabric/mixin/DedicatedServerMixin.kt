package team._0mods.multilib.fabric.mixin

import net.minecraft.server.MinecraftServer
import net.minecraft.server.dedicated.DedicatedServer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.LifecycleEvent

@Mixin(DedicatedServer::class)
class DedicatedServerMixin {
    @Inject(method = ["initServer"], at = [At("RETURN")], cancellable = true)
    private fun initServer(cir: CallbackInfoReturnable<Boolean>) {
        if (cir.returnValueZ) {
            LifecycleEvent.SERVER_STARTING.event.onChanged(this as MinecraftServer)
        }
    }
}
