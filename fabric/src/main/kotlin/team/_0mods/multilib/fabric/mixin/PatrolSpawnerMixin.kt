package team._0mods.multilib.fabric.mixin

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.monster.PatrollingMonster
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.PatrolSpawner
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(PatrolSpawner::class)
class PatrolSpawnerMixin {
    @Inject(
        method = ["spawnPatrolMember"],
        at = [At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;",
            ordinal = 0,
            shift = At.Shift.BY,
            by = 2
        )],
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private fun checkPatrolSpawn(
        level: ServerLevel,
        pos: BlockPos,
        r: RandomSource,
        b: Boolean,
        cir: CallbackInfoReturnable<Boolean>,
        blockState: BlockState,
        entity: PatrollingMonster
    ) {
        val result = EntityEvent.CHECK_SPAWN.event
            .canSpawn(entity, level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), MobSpawnType.PATROL, null)
        if (result.value != null) {
            cir.setReturnValue(result.value)
        }
    }
}
