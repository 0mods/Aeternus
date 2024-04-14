package team._0mods.multilib.fabric.mixin

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.FarmBlock
import net.minecraft.world.level.block.state.BlockState
import org.apache.commons.lang3.tuple.Triple
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import team._0mods.multilib.event.base.common.InteractionEvent

@Mixin(FarmBlock::class)
class FarmBlockMixin {
    @Unique
    private var turnToDirtLocal: ThreadLocal<Triple<Long, Float, Entity>>? = ThreadLocal<Triple<Long, Float, Entity>>()

    @Inject(
        method = ["fallOn"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/FarmBlock;turnToDirt(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"
        )]
    )
    private fun fallOn(
        level: Level,
        blockState: BlockState,
        blockPos: BlockPos,
        entity: Entity,
        f: Float,
        ci: CallbackInfo
    ) {
        turnToDirtLocal?.set(Triple.of(blockPos.asLong(), f, entity))
    }

    @Inject(method = ["turnToDirt"], at = [At("HEAD")], cancellable = true)
    private fun turnToDirt(entity: Entity?, state: BlockState, level: Level, pos: BlockPos, ci: CallbackInfo) {
        val triple: Triple<Long, Float, Entity>? = turnToDirtLocal?.get()
        turnToDirtLocal?.remove()
        if (triple != null && triple.left == pos.asLong() && triple.right == entity) {
            if (InteractionEvent.FARMLAND_TRAMPLE.event.trample(level, pos, state, triple.middle, entity)
                    .value != null
            ) {
                ci.cancel()
            }
        }
    }
}