package team.zeds.ancientmagic.api.helper

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.world.item.ItemStack

object TagHelper {
    fun setTag(stack: ItemStack, key: String, value: Tag) {
        getTagCompound(stack).put(key, value)
    }

    fun setByte(stack: ItemStack, key: String, value: Byte) {
        getTagCompound(stack).putByte(key, value)
    }

    fun setShort(stack: ItemStack, key: String, value: Short) {
        getTagCompound(stack).putShort(key, value)
    }

    fun setInt(stack: ItemStack, key: String, value: Int) {
        getTagCompound(stack).putInt(key, value)
    }

    fun setLong(stack: ItemStack, key: String, value: Long) {
        getTagCompound(stack).putLong(key, value)
    }

    fun setFloat(stack: ItemStack, key: String, value: Float) {
        getTagCompound(stack).putFloat(key, value)
    }

    fun setDouble(stack: ItemStack, key: String, value: Double) {
        getTagCompound(stack).putDouble(key, value)
    }

    fun setString(stack: ItemStack, key: String, value: String) {
        getTagCompound(stack).putString(key, value)
    }

    fun setByteArray(stack: ItemStack, key: String, value: ByteArray) {
        getTagCompound(stack).putByteArray(key, value)
    }

    fun setIntArray(stack: ItemStack, key: String, value: IntArray) {
        getTagCompound(stack).putIntArray(key, value)
    }

    fun setBoolean(stack: ItemStack, key: String, value: Boolean) {
        getTagCompound(stack).putBoolean(key, value)
    }

    fun getTag(stack: ItemStack, key: String): Tag? {
        return if (stack.hasTag()) getTagCompound(stack)[key] else null
    }

    fun getByte(stack: ItemStack, key: String): Byte {
        return if (stack.hasTag()) getTagCompound(stack).getByte(key) else 0
    }

    fun getShort(stack: ItemStack, key: String): Short {
        return if (stack.hasTag()) getTagCompound(stack).getShort(key) else 0
    }

    fun getInt(stack: ItemStack, key: String): Int {
        return if (stack.hasTag()) getTagCompound(stack).getInt(key) else 0
    }

    fun getLong(stack: ItemStack, key: String): Long {
        return if (stack.hasTag()) getTagCompound(stack).getLong(key) else 0L
    }

    fun getFloat(stack: ItemStack, key: String): Float {
        return if (stack.hasTag()) getTagCompound(stack).getFloat(key) else 0.0f
    }

    fun getDouble(stack: ItemStack, key: String): Double {
        return if (stack.hasTag()) getTagCompound(stack).getDouble(key) else 0.0
    }

    fun getString(stack: ItemStack, key: String): String {
        return if (stack.hasTag()) getTagCompound(stack).getString(key) else ""
    }

    fun getByteArray(stack: ItemStack, key: String): ByteArray {
        return if (stack.hasTag()) getTagCompound(stack).getByteArray(key) else ByteArray(0)
    }

    fun getIntArray(stack: ItemStack, key: String): IntArray {
        return if (stack.hasTag()) getTagCompound(stack).getIntArray(key) else IntArray(0)
    }

    fun getBoolean(stack: ItemStack, key: String): Boolean {
        return stack.hasTag() && getTagCompound(stack).getBoolean(key)
    }

    fun hasKey(stack: ItemStack, key: String): Boolean {
        return stack.hasTag() && getTagCompound(stack).contains(key)
    }

    fun flipBoolean(stack: ItemStack, key: String) {
        setBoolean(stack, key, !getBoolean(stack, key))
    }

    fun removeTag(stack: ItemStack, key: String) {
        if (hasKey(stack, key)) {
            getTagCompound(stack).remove(key)
        }
    }

    fun validateCompound(stack: ItemStack) {
        if (!stack.hasTag()) {
            val tag = CompoundTag()
            stack.tag = tag
        }
    }

    fun getTagCompound(stack: ItemStack): CompoundTag {
        validateCompound(stack)
        return stack.tag!!
    }
}