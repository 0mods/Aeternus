package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.server.IntegratedServer
import net.minecraft.server.MinecraftServer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.LifecycleEvent

@Mixin(IntegratedServer::class)
class IntegratedServerMixin {
    @Inject(method = ["initServer"], at = [At("RETURN")], cancellable = true)
    private fun initServer(cir: CallbackInfoReturnable<Boolean>) {
        if (cir.returnValueZ) {
            LifecycleEvent.SERVER_STARTING.event.onChanged(this as MinecraftServer)
        }
    }
}