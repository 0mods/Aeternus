package team.zeds.ancientmagic.common.api.magic

import net.minecraft.core.Direction

interface IMagicProvider<T> {
    /**
     * [getMagicStorage] method-getter of [IMagicObject]
     * @param obj object of [IMagicObject]
     * @return [IMagicObject]
     */
    fun getMagicStorage(obj: T): IMagicObject<T>

    /**
     * Returning [IMagicObject] at side
     * @param obj object of [IMagicObject]
     * @param side side of [IMagicObject]
     * @return [IMagicObject]
     */
    fun getMagicStorage(obj: T, side: Direction): IMagicObject<T> = getMagicStorage(obj)
}