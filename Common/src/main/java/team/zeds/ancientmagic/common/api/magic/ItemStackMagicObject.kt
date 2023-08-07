package team.zeds.ancientmagic.common.api.magic

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack

open class ItemStackMagicObject(tag: CompoundTag) : TagMagicObject<ItemStack>(tag) {
    override fun setManaStorage(obj: ItemStack, value: Long) {
        obj.getOrCreateTagElement("AMData").putLong("AMMagicStorage", value)
    }

    override fun setMaxManaStorage(obj: ItemStack, value: Long) {
        obj.getOrCreateTagElement("AMData").putLong("AMMagicStorageMax", value)
    }

    override fun getManaStorage(obj: ItemStack): Long {
        if (obj.hasTag() && obj.tag!!.contains("AMData"))
            return obj.tag!!.getCompound("AMData").getLong("AMMagicStorage")
        return 0
    }

    override fun getMaxManaStorage(obj: ItemStack): Long {
        if (obj.hasTag() && obj.tag!!.contains("AMData"))
            return obj.tag!!.getCompound("AMData").getLong("AMMagicStorageMax")
        return 0
    }

    override fun insertMana(obj: ItemStack, value: Long): Long {
        if (obj.hasTag() && obj.tag!!.contains("AMData")) {
            val data: CompoundTag = obj.tag!!.getCompound("AMData")
            val energy: Long = data.getLong("AMMagicStorage")
            val i: Long = data.getLong("max_energy") - energy
            if (value > i) {
                data.putLong("AMMagicStorage", energy + i)
                return value - i
            }
            data.putLong("AMMagicStorage", energy + value)
        } else setManaStorage(obj, value)
        return 0
    }

    fun extractEnergy(obj: ItemStack, value: Long): Long {
        if (value < 0) return insertMana(-value)
        if (obj.hasTag() && obj.tag!!.contains("AMData")) {
            val data: CompoundTag = obj.tag!!.getCompound("AMData")
            val energy: Long = data.getLong("AMMagicStorage")
            if (value > energy) {
                val x = -(energy - value)
                data.putLong("AMMagicStorage", energy - (value - x))
                return x
            }
            data.putLong("AMMagicStorage", energy - value)
            return 0
        }
        return value
    }
}
