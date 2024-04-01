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

@file:Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
package team._0mods.multilib.event.result

import net.minecraft.world.InteractionResultHolder

class EventResultHolder<T> internal constructor(private val result: EventResult, private val t: T?) {
    companion object {
        private val PASS = EventResultHolder(EventResult.pass(), null)

        fun <T> pass(): EventResultHolder<T> = PASS as EventResultHolder<T>

        fun <T> result(result: Boolean?, obj: T): EventResultHolder<T> = EventResultHolder(EventResult.result(result), obj)

        fun <T> resultTrue(obj: T): EventResultHolder<T> = EventResultHolder(EventResult.resultTrue(), obj)

        fun <T> default(obj: T): EventResultHolder<T> = EventResultHolder(EventResult.default(), obj)

        fun <T> resultFalse(obj: T): EventResultHolder<T> = EventResultHolder(EventResult.resultFalse(), obj)
    }

    val endFurtherEvaluation: Boolean
        get() = result.endFurtherEvaluation

    val value: Boolean?
        get() = result.value

    val isEmpty: Boolean
        get() = result.isEmpty

    val isPresent: Boolean
        get() = result.isPresent

    val isTrue: Boolean
        get() = result.isTrue

    val isFalse: Boolean
        get() = result.isFalse

    fun result() = result

    val obj: T?
        get() = t

    val asInteractionResult: InteractionResultHolder<T>
        get() = InteractionResultHolder(result.asInteractionResult, t)
}
