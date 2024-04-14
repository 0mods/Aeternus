package team._0mods.multilib.fabric.mixin

import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ChunkMap
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.chunk.ChunkStatus
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.ChunkEvent

@Mixin(ChunkMap::class)
class ChunkMapMixin {
    @Shadow
    @Final
    var level: ServerLevel? = null

    @Inject(
        method = ["save"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ChunkMap;write(Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/nbt/CompoundTag;)V",
            ordinal = 0
        )],
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private fun save(
        chunkAccess: ChunkAccess,
        cir: CallbackInfoReturnable<Boolean>,
        pos: ChunkPos,
        chunkStatus: ChunkStatus,
        nbt: CompoundTag
    ) {
        ChunkEvent.SAVE_DATA.event.save(chunkAccess, this.level!!, nbt)
    }
}
