package team._0mods.multilib.fabric.mixin

import net.minecraft.server.level.ServerLevel
import net.minecraft.util.ProgressListener
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.entity.PersistentEntitySectionManager
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.LifecycleEvent
import team._0mods.multilib.fabric.hooks.PersistentEntitySectionManagerHooks

@Mixin(ServerLevel::class)
class ServerLevelMixin {
    @Shadow
    @Final
    private lateinit var entityManager: PersistentEntitySectionManager<Entity>

    @Inject(
        method = ["save"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerChunkCache;save(Z)V")]
    )
    private fun save(progressListener: ProgressListener, bl: Boolean, bl2: Boolean, ci: CallbackInfo) {
        val obj = this as Any
        LifecycleEvent.SERVER_LEVEL_SAVE.event.onChanged(obj as ServerLevel)
    }

    @Inject(
        method = ["addEntity"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/entity/PersistentEntitySectionManager;addNewEntity(Lnet/minecraft/world/level/entity/EntityAccess;)Z"
        )],
        cancellable = true
    )
    private fun addEntity(entity: Entity, cir: CallbackInfoReturnable<Boolean>) {
        val obj = this as Any
        (entityManager as PersistentEntitySectionManagerHooks).mlAttachLevel(obj as ServerLevel)
    }
}
