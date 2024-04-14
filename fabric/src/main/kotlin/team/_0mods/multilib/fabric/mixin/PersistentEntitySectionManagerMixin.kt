package team._0mods.multilib.fabric.mixin

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.entity.EntityAccess
import net.minecraft.world.level.entity.PersistentEntitySectionManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import team._0mods.multilib.event.base.common.EntityEvent
import team._0mods.multilib.fabric.hooks.PersistentEntitySectionManagerHooks
import java.lang.ref.WeakReference

@Mixin(PersistentEntitySectionManager::class)
class PersistentEntitySectionManagerMixin<T: EntityAccess> : PersistentEntitySectionManagerHooks {
    @Unique
    private var levelRef: WeakReference<ServerLevel>? = null

    override fun mlAttachLevel(level: ServerLevel) {
        levelRef = WeakReference(level)
    }

    @Inject(
        method = ["addEntity"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/core/SectionPos;asLong(Lnet/minecraft/core/BlockPos;)J")],
        cancellable = true
    )
    private fun addEntity(entityAccess: T, bl: Boolean, cir: CallbackInfoReturnable<Boolean>) {
        if (entityAccess is Entity && levelRef != null) {
            val level = levelRef!!.get()
            levelRef = null

            if (level != null) {
                if (EntityEvent.JOIN_WORLD.event.join(entityAccess, level).isFalse) {
                    cir.setReturnValue(false)
                }
            }
        }
    }
}