package team.zeds.ancientmagic.common.api.cap

import net.minecraft.nbt.CompoundTag
import team.zeds.ancientmagic.common.api.magic.type.MagicType

interface PlayerMagic<T: PlayerMagic<T>> {
    fun getMagicLevel(): Int
    fun getMagicType(): MagicType
    fun addLevel(count: Int)
    fun subLevel(count: Int)
    fun setLevel(to: Int)
    fun copy(source: T)
}