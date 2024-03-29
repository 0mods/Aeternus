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

package team._0mods.multilib.registries

import team._0mods.multilib.util.NullableSupplier
import java.util.function.Supplier
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class DelegatedRegistry<V>(val asRegistry: RegistrySupplier<V>): ReadOnlyProperty<Any?, V>, Supplier<V>, () -> V, NullableSupplier<V> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): V = asRegistry.get()

    override fun get(): V = asRegistry.get()

    override fun invoke(): V = asRegistry.get()

    override fun getNullable(): V? = asRegistry.getNullable()
}

fun <V, T: V> DeferredRegister<V>.reg(name: String, obj: () -> T): DelegatedRegistry<T> {
    val reg = this.register(name, obj)
    return DelegatedRegistry(reg)
}
