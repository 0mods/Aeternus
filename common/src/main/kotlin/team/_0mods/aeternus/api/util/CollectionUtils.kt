/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:JvmName("CollectionUtils")

package team._0mods.aeternus.api.util

import com.google.common.collect.Multimap
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Experimental
fun <T, X> Map<T, X>.revert(): Map<X, T> {
    val mutableMap = mutableMapOf<X, T>()

    val keys = this.keys.toList()

    for (key in keys) {
        val value = this[key] ?: continue
        mutableMap[value] = key
    }

    return mutableMap.toMap()
}

@ApiStatus.Experimental
fun List<String>.toRLList(): List<ResourceLocation> {
    val rlList = mutableListOf<ResourceLocation>()
    for (id in this) {
        if (rlList.stream().noneMatch { it == id.rl })
            rlList.add(id.rl)
        else continue
    }
    return rlList.toList()
}

@ApiStatus.Experimental
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

operator fun <K, V> Multimap<K, V>.set(key: K, value: V) {
    this.put(key, value)
}
