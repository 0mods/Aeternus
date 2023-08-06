package team.zeds.ancientmagic.common.api.magic

import net.minecraft.nbt.CompoundTag
import kotlin.math.*

interface IMagicObject<T>: IMagicProvider<T> {
    fun saveToCompound(tag: CompoundTag): CompoundTag {
        tag.putLong("AMMagicStorage", this.getManaStorage())
        tag.putLong("AMMagicStorageMax", this.getMaxManaStorage())
        return tag
    }

    fun saveToCompound(obj: T, tag: CompoundTag): CompoundTag {
        tag.putLong("AMMagicStorage", this.getManaStorage(obj))
        tag.putLong("AMMagicStorageMax", this.getMaxManaStorage(obj))
        return tag
    }

    fun setManaStorage(value: Long) {}

    fun setManaStorage(obj: T, value: Long) {
        setManaStorage(value)
    }

    fun setMaxManaStorage(value: Long) {}

    fun setMaxManaStorage(obj: T, value: Long) {
        setMaxManaStorage(value)
    }

    fun getManaStorage(): Long = 0

    fun getManaStorage(obj: T): Long = getManaStorage()

    fun getMaxManaStorage(): Long = 0

    fun getMaxManaStorage(obj: T): Long = getMaxManaStorage()

    fun isFull(): Boolean = this.getManaStorage() == this.getMaxManaStorage()

    fun isFull(obj: T): Boolean = this.getManaStorage(obj) == this.getMaxManaStorage(obj)

    fun insertEnergy(value: Long): Long {
        val energy = getManaStorage()
        var result = 0L
        if (energy < this.getMaxManaStorage())
            result = min(energy + value, getMaxManaStorage())
        setManaStorage(result)
        return result
    }

    fun insertEnergy(obj: T, value: Long): Long {
        val energy = getManaStorage(obj)
        val result = min(energy + value, getMaxManaStorage(obj))
        setManaStorage(obj, result)
        return result
    }

    fun extractEnergy(value: Long): Long {
        val energy = getManaStorage()
        val result = max(energy - value, 0)
        setManaStorage(result)
        return result
    }

    fun extractEnergy(obj: T, value: Long): Long {
        val energy = getManaStorage(obj)
        val result = max(energy - value, 0)
        setManaStorage(obj, result)
        return result
    }

    fun equalize(equalizeWith: IMagicObject<*>): Long {
        val max = this.getMaxManaStorage()
        val j = (this.getManaStorage() - equalizeWith.getManaStorage()) / 2.0

        var i: Long = if (j < 0) floor(j).toLong() else ceil(j).toLong()

        if (i < 0) if (-i > max) i = -(i - (i - max)) + max / 2
        else if (i > max) i -= (max - i)
        else if (i > equalizeWith.getMaxManaStorage()) i += equalizeWith.getMaxManaStorage() - i

        if (i <= 0 && this.getManaStorage() == max) return 0

        i += this.extractEnergy(i)
        equalizeWith.insertEnergy(i)

        return i
    }

    fun equalize(obj: T, equalizeWith: IMagicObject<*>): Long {
        val max = this.getMaxManaStorage(obj)
        var i = (this.getManaStorage(obj) - equalizeWith.getManaStorage()) / 2

        if (i < 0)
            if (-i > max)
                i = -(i - (i - max)) + max
            else if (i > max)
                i -= (max - i)
            else if (i > equalizeWith.getMaxManaStorage()) i += equalizeWith.getMaxManaStorage() - i

        if (i == 0L || i < 0L && this.getManaStorage(obj) == max) return 0

        i += this.extractEnergy(obj, i)
        equalizeWith.insertEnergy(i)

        return i
    }

    fun transferEnergy(storage: IMagicObject<*>): Long {
        var value = storage.getManaStorage()
        if (value > this.getMaxManaStorage()) value -= (value - this.getMaxManaStorage())
        value -= this.insertEnergy(value)
        value += storage.extractEnergy(value)
        this.extractEnergy(value)
        return value
    }

    fun transferEnergy(obj: T, storage: IMagicObject<*>): Long {
        var value = storage.getManaStorage()
        if (value > this.getMaxManaStorage(obj)) value -= (value - this.getMaxManaStorage(obj))
        value -= this.insertEnergy(obj, value)
        value += storage.extractEnergy(value)
        this.extractEnergy(obj, value)
        return value
    }

    override fun getMagicStorage(obj: T): IMagicObject<T> = this
}