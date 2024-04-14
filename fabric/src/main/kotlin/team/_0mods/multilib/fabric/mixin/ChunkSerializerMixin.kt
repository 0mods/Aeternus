package team._0mods.multilib.fabric.mixin

import com.mojang.serialization.Codec
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.ai.village.poi.PoiManager
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.chunk.*
import net.minecraft.world.level.chunk.storage.ChunkSerializer
import net.minecraft.world.level.levelgen.blending.BlendingData
import net.minecraft.world.level.lighting.LevelLightEngine
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import org.spongepowered.asm.mixin.injection.callback.LocalCapture
import team._0mods.multilib.event.base.common.ChunkEvent

@Mixin(ChunkSerializer::class)
class ChunkSerializerMixin {
    @Inject(method = ["read"], at = [At("RETURN")], locals = LocalCapture.CAPTURE_FAILHARD)
    private fun load(
        serverLevel: ServerLevel, poiManager: PoiManager, chunkPos: ChunkPos, compoundTag: CompoundTag,
        cir: CallbackInfoReturnable<ProtoChunk>, chunkPos2: ChunkPos, upgradeData: UpgradeData,
        bl: Boolean, listTag: ListTag, i: Int, levelChunkSections: Array<LevelChunkSection>, bl2: Boolean,
        chunkSource: ChunkSource, levelLightEngine: LevelLightEngine, registry: Registry<Biome>,
        codec: Codec<PalettedContainer<Holder<Biome>>>, bl3: Boolean, m: Long,
        chunkType: ChunkStatus.ChunkType, blendingData: BlendingData, chunkAccess: ChunkAccess
    ) {
        ChunkEvent.LOAD_DATA.event.load(chunkAccess, serverLevel, compoundTag)
    }
}
