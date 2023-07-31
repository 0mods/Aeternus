package team.zeds.ancientmagic.common.api.cap

import net.minecraft.nbt.CompoundTag
import org.jetbrains.annotations.NotNull
import team.zeds.ancientmagic.common.api.magic.MagicType

interface PlayerMagic {
    fun getMagicLevel(): Int
    fun getMagicType(): MagicType
    fun addLevel(count: Int)
    fun subLevel(count: Int)
    fun setLevel(to: Int)
    fun <T: PlayerMagic> copy(source: T)
    fun save(): CompoundTag?
    fun load(tag: CompoundTag)
}