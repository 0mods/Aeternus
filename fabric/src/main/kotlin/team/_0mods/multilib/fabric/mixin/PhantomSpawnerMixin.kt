package team._0mods.multilib.fabric.mixin

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.ServerStatsCounter
import net.minecraft.util.RandomSource
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.SpawnGroupData
import net.minecraft.world.entity.monster.Phantom
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.PhantomSpawner
import net.minecraft.world.level.material.FluidState
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.EntityEvent

@Mixin(PhantomSpawner::class)
class PhantomSpawnerMixin {
    @Inject(
        method = ["tick"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/monster/Phantom;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;",
            ordinal = 0,
            shift = At.Shift.BEFORE
        )],
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private fun checkPhantomSpawn(
        level: ServerLevel,
        bl: Boolean,
        bl2: Boolean,
        cir: CallbackInfoReturnable<Int>,
        random: RandomSource,
        i: Int,
        it: Iterator<ServerPlayer>,
        player: ServerPlayer,
        pos: BlockPos,
        diff: DifficultyInstance,
        serverStatsCounter: ServerStatsCounter,
        j: Int,
        k: Int,
        pos2: BlockPos,
        blockState: BlockState,
        fluidState: FluidState,
        sgd: SpawnGroupData,
        l: Int,
        m: Int,
        entity: Phantom
    ) {
        if (EntityEvent.CHECK_SPAWN.event
                .canSpawn(entity, level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), MobSpawnType.NATURAL, null)
                .value == false
        ) {
            cir.setReturnValue(0)
            cir.cancel()
        }
    }
}
