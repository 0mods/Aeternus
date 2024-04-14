package team._0mods.multilib.fabric.mixin

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.ExplosionEvent

@Mixin(Explosion::class)
class ExplosionMixin {
    @Shadow
    @Final
    private val level: Level? = null

    @Inject(
        method = ["explode"],
        at = [At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V", ordinal = 1)],
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private fun explodePost(
        ci: CallbackInfo,
        set: Set<BlockPos>,
        i: Int,
        q: Float,
        r: Int,
        s: Int,
        t: Int,
        u: Int,
        v: Int,
        w: Int,
        list: List<Entity>
    ) {
        ExplosionEvent.DETONATE.event.explode(
            level!!,
            this as Explosion, list
        )
    }
}