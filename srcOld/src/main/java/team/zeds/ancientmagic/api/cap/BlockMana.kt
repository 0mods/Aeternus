package team.zeds.ancientmagic.api.cap

import net.minecraft.nbt.CompoundTag

interface BlockMana {
    fun getManaStorage(): Int

    fun setManaStorage(storage: Int)

    fun getMaxManaStorage(): Int

    fun setMaxManaStorage(storage: Int)

    fun addMana(count: Int)

    fun subMana(count: Int)

    fun save(tag: CompoundTag) {}

    fun load(tag: CompoundTag) {}
}
