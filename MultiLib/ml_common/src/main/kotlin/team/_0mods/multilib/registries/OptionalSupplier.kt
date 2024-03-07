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
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import java.util.stream.Stream

interface OptionalSupplier<T>: NullableSupplier<T> {
    val isPresent: Boolean

    // throws NullPointerException
    fun get(): T = getNullable()!!

    val orNull: T?
        get() {
            if (isPresent)
                return this.get()
            return null
        }

    fun toOptional() = Optional.ofNullable(orNull)

    fun ifPresent(action: Consumer<in T>) {
        if (isPresent)
            action.accept(getNullable()!!)
    }

    fun ifPresentOrElse(action: Consumer<in T>, emptyAction: Runnable) {
        if (isPresent)
            action.accept(getNullable()!!)
        else
            emptyAction.run()
    }

    fun stream(): Stream<T> {
        return if (!isPresent)
            Stream.empty()
        else Stream.of(getNullable())
    }

    fun orElse(other: T) = if (isPresent) getNullable() else other

    fun orElseGet(supplier: Supplier<out T>) = if (isPresent) getNullable() else supplier.get()
}
