package team._0mods.multilib.fabric.mixin

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.NaturalSpawner
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Redirect
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(NaturalSpawner::class)
abstract class NaturalSpawnerMixin {
    @Shadow
    private fun isValidPositionForMob(serverLevel: ServerLevel, mob: Mob, d: Double): Boolean {
        return false
    }

    @Redirect(
        method = ["spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner\$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner\$AfterSpawnCallback;)V"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/NaturalSpawner;isValidPositionForMob(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Mob;D)Z",
            ordinal = 0
        )
    )
    private fun overrideNaturalSpawnCondition(level: ServerLevel, entity: Mob, f: Double): Boolean {
        val result = EntityEvent.CHECK_SPAWN.event
            .canSpawn(entity, level, entity.xOld, entity.yOld, entity.zOld, MobSpawnType.NATURAL, null)
        return if (result.value != null) {
            result.value!!
        } else {
            isValidPositionForMob(level, entity, f)
        }
    }

    @Redirect(
        method = ["spawnMobsForChunkGeneration"],
        at = At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z",
            ordinal = 0
        )
    )
    private fun overrideChunkGenSpawnCondition(mob: Mob, level: LevelAccessor, type: MobSpawnType): Boolean {
        val result = EntityEvent.CHECK_SPAWN.event
            .canSpawn(mob, level, mob.xOld, mob.yOld, mob.zOld, MobSpawnType.CHUNK_GENERATION, null)
        return if (result.value != null) {
            result.value!!
        } else {
            mob.checkSpawnRules(level, type)
        }
    }
}