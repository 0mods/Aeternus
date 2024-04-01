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

package team._0mods.multilib.config

interface Delegate

interface BaseValue<T>: Delegate {
    fun get(): T
}

interface Range<T: Number>: Delegate {
    val max: T

    val min: T

    val default: T
}

class IntRange private constructor(override val max: Int, override val min: Int, override val default: Int) : Range<Int> {
    companion object {
        @JvmStatic fun get(min: Int, max: Int, default: Int) = IntRange(max, min, default)
    }
}

class LongRange private constructor(override val max: Long, override val min: Long, override val default: Long): Range<Long> {
    companion object {
        @JvmStatic fun get(min: Long, max: Long, default: Long) = LongRange(max, min, default)
    }
}

class FloatRange private constructor(override val max: Float, override val min: Float, override val default: Float): Range<Float> {
    companion object {
        @JvmStatic fun get(min: Float, max: Float, default: Float) = FloatRange(max, min, default)
    }
}

class DoubleRange private constructor(override val max: Double, override val min: Double, override val default: Double): Range<Double> {
    companion object {
        @JvmStatic fun get(min: Double, max: Double, default: Double) = DoubleRange(max, min, default)
    }
}

class StringValue private constructor(private val id: String, private val value: (String) -> String): BaseValue<String> {
    companion object {
        @JvmStatic fun get(id: String, value: String) = StringValue(id) { value }
    }

    override fun get(): String = value(id)
}

class BooleanValue private constructor(private val id: String, private val value: (String) -> Boolean): BaseValue<Boolean> {
    companion object {
        @JvmStatic fun get(id: String, value: Boolean) = BooleanValue(id) { value }
    }

    override fun get(): Boolean = value(id)
}
