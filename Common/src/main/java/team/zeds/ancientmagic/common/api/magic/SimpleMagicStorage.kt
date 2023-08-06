package team.zeds.ancientmagic.common.api.magic

import kotlin.math.*

open class SimpleMagicStorage<T: SimpleMagicStorage<T>>(private var capacity: Long): IMagicObject<T> {
    private var storage: Long = 0

    override fun setManaStorage(value: Long) {
        this.storage = value
    }

    override fun setManaStorage(obj: T, value: Long) {
        obj.storage = value
    }

    override fun setMaxManaStorage(value: Long) {
        this.capacity = value
    }

    override fun setMaxManaStorage(obj: T, value: Long) {
        obj.storage = value
    }

    override fun getManaStorage(): Long = this.storage

    override fun getManaStorage(obj: T): Long = obj.storage

    override fun getMaxManaStorage(): Long = this.capacity

    override fun getMaxManaStorage(obj: T): Long = obj.capacity

    override fun insertMana(value: Long): Long {
        val energy = getManaStorage()
        var result = 0L
        if (energy < this.getMaxManaStorage())
            result = min(energy + value, getMaxManaStorage())
        setManaStorage(result)
        return result
    }

    override fun insertMana(obj: T, value: Long): Long {
        val energy = getManaStorage(obj)
        val result = min(energy + value, getMaxManaStorage(obj))
        setManaStorage(obj, result)
        return result
    }

    override fun extractMana(value: Long): Long {
        val energy = getManaStorage()
        val result = max(energy - value, 0)
        setManaStorage(result)
        return result
    }

    override fun extractMana(obj: T, value: Long): Long {
        val energy = getManaStorage(obj)
        val result = max(energy - value, 0)
        setManaStorage(obj, result)
        return result
    }

    override fun getMagicStorage(obj: T): IMagicObject<T> = this
}