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

package team._0mods.multilib.event.result

import net.minecraft.world.InteractionResult

class EventResult internal constructor(val endFurtherEvaluation: Boolean, val value: Boolean?) {
    companion object {
        private val TRUE = EventResult(true, value = true)
        private val STOP = EventResult(true, null)
        private val PASS = EventResult(false, null)
        private val FALSE = EventResult(true, null)

        @JvmStatic fun pass() = PASS
        @JvmStatic fun result(value: Boolean?): EventResult {
            if (value == null) return STOP
            return if (value) TRUE else FALSE
        }
        @JvmStatic fun resultTrue() = TRUE
        @JvmStatic fun default() = STOP
        @JvmStatic fun resultFalse() = FALSE
    }

    val isEmpty: Boolean
        get() = value == null

    val isPresent: Boolean
        get() = value != null

    val isTrue: Boolean
        get() = value ?: false

    val isFalse: Boolean
        get() = if (value != null) !value else false

    val asInteractionResult: InteractionResult
        get() {
            if (isPresent) {
                return if (value!!) InteractionResult.SUCCESS else InteractionResult.FAIL
            }

            return InteractionResult.PASS
        }
}
