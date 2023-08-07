package team.zeds.ancientmagic.common.api.magic

import net.minecraft.nbt.CompoundTag
import kotlin.math.max
import kotlin.math.min

open class TagMagicObject<T>(val tag: CompoundTag): IMagicObject<T> {
    override fun setManaStorage(value: Long) {
        this.tag.putLong("AMMagicStorage", value)
    }

    override fun getManaStorage(): Long = this.tag.getLong("AMMagicStorage")

    override fun setMaxManaStorage(value: Long) {
        this.tag.putLong("AMMagicStorageMax", value)
    }

    override fun getMaxManaStorage(): Long = this.tag.getLong("AMMagicStorageMax")

    override fun insertMana(value: Long): Long {
        val energy = getManaStorage()
        var result = 0L
        if (energy < this.getMaxManaStorage())
            result = min(energy + value, getMaxManaStorage())
        setManaStorage(result)
        return result
    }

    override fun extractMana(value: Long): Long {
        if (value < 0)
            return extractMana(-value)

        val energy = getManaStorage()
        val result = max(energy - value, 0)
        setManaStorage(result)
        return result
    }
}