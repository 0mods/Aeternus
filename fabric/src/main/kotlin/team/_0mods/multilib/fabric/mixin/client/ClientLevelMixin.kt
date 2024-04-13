package team._0mods.multilib.fabric.mixin.client

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.client.renderer.LevelRenderer
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceKey
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.dimension.DimensionType
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.client.ClientLifecycleEvent
import team._0mods.multilib.event.base.common.EntityEvent
import java.util.function.Supplier

@Mixin(ClientLevel::class)
class ClientLevelMixin {
    @Inject(method = ["<init>"], at = [At("RETURN")])
    private fun construct(
        cpl: ClientPacketListener, level: ClientLevel.ClientLevelData, resKey: ResourceKey<Level>,
        holder: Holder<DimensionType>, i: Int, j: Int, sup: Supplier<ProfilerFiller>, lvlRender: LevelRenderer,
        bl: Boolean, l: Long, ci: CallbackInfo
    ) {
        val o = this as Any
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.event.onChanged(o as ClientLevel)
    }

    @Inject(method = ["addEntity"], at = [At("HEAD")], cancellable = true)
    private fun addEntity(e: Entity, ci: CallbackInfo) {
        val o = this as Any
        if (EntityEvent.JOIN_WORLD.event.join(e, o as ClientLevel).isFalse)
            ci.cancel()
    }
}
