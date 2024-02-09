@file:JvmName("CollectionUtils")

package team._0mods.aeternus.api.util

import net.minecraft.resources.ResourceLocation

fun <T, X> Map<T, X>.revert(): Map<X, T> {
    val mutableMap = mutableMapOf<X, T>()

    val keys = this.keys.toList()

    for (key in keys) {
        val value = this[key] ?: continue
        mutableMap[value] = key
    }

    return mutableMap.toMap()
}

fun List<String>.toRLList(): List<ResourceLocation> {
    val rlList = mutableListOf<ResourceLocation>()
    for (id in this) {
        if (rlList.stream().noneMatch { it == id.rl })
            rlList.add(id.rl)
        else continue
    }
    return rlList.toList()
}

fun <T, R> Map<T, R>.fromMapToListByList(from: List<T>): List<R> {
    val listOfR = mutableListOf<R>()
    for (value in from) {
        val r = this[value] ?: continue

        if (listOfR.stream().noneMatch { it == r })
            listOfR.add(r)
        else continue

    }

    return listOfR.toList()
}
