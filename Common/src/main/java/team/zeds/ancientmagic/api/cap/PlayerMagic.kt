package team.zeds.ancientmagic.api.cap

import net.minecraft.nbt.CompoundTag
import org.jetbrains.annotations.NotNull
import team.zeds.ancientmagic.api.magic.MagicType

interface PlayerMagic {
    fun getMagicLevel(): Int
    fun getMagicType(): MagicType
    fun addLevel(count: Int)
    fun subLevel(count: Int)
    fun setLevel(to: Int)
    fun copy(source: PlayerMagic)
    fun save(@NotNull tag: CompoundTag)
    fun load(@NotNull tag: CompoundTag)
}