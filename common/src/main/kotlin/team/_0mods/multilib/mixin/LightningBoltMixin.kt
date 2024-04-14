package team._0mods.multilib.mixin

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LightningBolt
import net.minecraft.world.level.Level
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.LightningEvent

@Mixin(LightningBolt::class)
abstract class LightningBoltMixin(entityType: EntityType<*>, level: Level) : Entity(entityType, level) {
    init {
        throw IllegalStateException()
    }

    @Inject(
        method = ["tick"],
        at = [At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
            ordinal = 1,
            shift = At.Shift.BY,
            by = 1
        )],
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    fun handleLightning(ci: CallbackInfo, list: List<Entity>) {
        if (this.isRemoved || level().isClientSide) return

        val obj = this as Any
        LightningEvent.STRIKE.event.onStrike(obj as LightningBolt, this.level(), this.position(), list)
    }
}
