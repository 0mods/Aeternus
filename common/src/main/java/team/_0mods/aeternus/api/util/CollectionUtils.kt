/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:JvmName("CollectionUtils")

package team._0mods.aeternus.api.util

import com.google.common.collect.Multimap
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Experimental
fun <K, V> Map<K, V>.revert(): Map<V, K> {
    val mutableMap = mutableMapOf<V, K>()

    val keys = this.keys.toList()

    for (key in keys) {
        val value = this[key] ?: continue
        mutableMap[value] = key
    }

    return mutableMap.toMap()
}

@get:ApiStatus.Experimental
val List<String>.toAPIRLList: List<APIResourceLocation>
    get() {
        val rlList = mutableListOf<APIResourceLocation>()
        for (id in this) {
            if (rlList.stream().noneMatch { it == APIResourceLocation.createRL(id) })
                rlList.add(APIResourceLocation.createRL(id))
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

fun <K, V> Map<K, V>.noneMatchKey(key: K): Boolean = this.keys.stream().noneMatch { it == key }

fun <K, V> Map<K, V>.noneMatchValue(value: V): Boolean = this.values.stream().noneMatch { it == value }

operator fun <K, V> Multimap<K, V>.set(key: K, value: V) {
    this.put(key, value)
}
