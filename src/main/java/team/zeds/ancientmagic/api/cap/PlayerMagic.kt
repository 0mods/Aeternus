package team.zeds.ancientmagic.api.cap

import net.minecraft.nbt.CompoundTag
import team.zeds.ancientmagic.api.magic.MagicType

interface PlayerMagic {
    /**
     * Getter of int value
     * @return [Int]
     */
    fun getMagicLevel(): Int

    /**
     * Getter of [MagicType] from [getMagicLevel]
     * @return [MagicType]
     */
    fun getMagicLevelAsMagicType(): MagicType

    /**
     * Calculator of [Math].
     * @param add is number of added levels
     */
    fun addLevel(add: Int)

    /**
     * Calculator of [Math].
     * @param sub is number of sublevels
     */
    fun subLevel(sub: Int)

    /**
     * [Int] value setter.
     */
    fun setLevel(set: Int)

    /**
     * [copyFrom] - Method of copying data from old player to new. Setter
     */
    fun copyFrom(source: PlayerMagic)

    /**
     * [CompoundTag] serializer.
     * @param tag is sets to save tag.
     */
    fun save(tag: CompoundTag)

    /**
     * [CompoundTag] deserializer
     * @param tag is sets to load tag.
     */
    fun load(tag: CompoundTag)
}