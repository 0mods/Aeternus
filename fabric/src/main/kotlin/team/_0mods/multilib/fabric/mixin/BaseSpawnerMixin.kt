package team._0mods.multilib.fabric.mixin

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.level.BaseSpawner
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Redirect
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(BaseSpawner::class)
class BaseSpawnerMixin {
    @Redirect(
        method = ["serverTick"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z",
            ordinal = 0
        )
    )
    private fun checkSpawnerSpawn(mob: Mob, level: LevelAccessor, type: MobSpawnType): Boolean {
        val obj = this as Any
        val result = EntityEvent.CHECK_SPAWN.event
            .canSpawn(mob, level, mob.x, mob.y, mob.z, type, obj as BaseSpawner)
        if (result.value != null) {
            return result.value!!
        }
        return mob.checkSpawnRules(level, type) && mob.checkSpawnObstruction(level)
    }

    @Redirect(
        method = ["serverTick"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Mob;checkSpawnObstruction(Lnet/minecraft/world/level/LevelReader;)Z",
            ordinal = 0
        )
    )
    private fun skipDoubleObstruction(mob: Mob, levelReader: LevelReader): Boolean {
        return true
    }
}
