package team._0mods.aeternus.api.event.base.common

import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.chunk.ChunkAccess

interface ChunkEvent {

    fun interface SaveData {
        fun save(chunk: ChunkAccess, level: ServerLevel, tag: CompoundTag)
    }

    fun interface LoadData {
        fun load(chunk: ChunkAccess, level: ServerLevel?, tag: CompoundTag)
    }
}