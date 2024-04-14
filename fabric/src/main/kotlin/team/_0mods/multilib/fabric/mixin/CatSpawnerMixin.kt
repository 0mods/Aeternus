package team._0mods.multilib.fabric.mixin

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.npc.CatSpawner
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.EntityEvent
import kotlin.Int

@Mixin(CatSpawner::class)
class CatSpawnerMixin {
    @Inject(
        method = ["spawnCat"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/animal/Cat;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;",
            ordinal = 0
        )],
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private fun checkCatSpawn(pos: BlockPos, level: ServerLevel, cir: CallbackInfoReturnable<Int>, entity: Cat) {
        if (EntityEvent.CHECK_SPAWN.event
                .canSpawn(entity, level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), MobSpawnType.NATURAL, null).value == false
        ) {
            cir.setReturnValue(0)
            cir.cancel()
        }
    }
}