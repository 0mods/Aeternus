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

package team._0mods.multilib.impl

import team._0mods.multilib.event.base.client.TooltipEvent

class TooltipColorContext: TooltipEvent.ColorContext {
    companion object {
        @JvmField val CONTEXT: ThreadLocal<TooltipColorContext> = ThreadLocal.withInitial(::TooltipColorContext)
    }

    fun reset(): TooltipColorContext {
        this.backgroundColor = (0xf0100010).toInt()
        this.outlineGradientTopColor = 0x505000ff
        this.outlineGradientBottomColor = 0x5028007f
        return this
    }

    override var backgroundColor: Int = 0
    override var outlineGradientTopColor: Int = 0
    override var outlineGradientBottomColor: Int = 0
}
