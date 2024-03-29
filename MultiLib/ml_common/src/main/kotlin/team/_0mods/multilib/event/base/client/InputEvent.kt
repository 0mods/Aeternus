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

package team._0mods.multilib.event.base.client

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import team._0mods.multilib.event.core.EventFactory
import team._0mods.multilib.event.result.EventResult

interface InputEvent {
    companion object {
        @JvmField val MOUSE_SCROLL = EventFactory.createEventResult<MouseScroll>()

        @JvmField val MOUSE_CLICK_PRE = EventFactory.createEventResult<MouseClick>()
        @JvmField val MOUSE_CLICK_POST = EventFactory.createEventResult<MouseClick>()

        @JvmField val KEY_PRESS = EventFactory.createEventResult<Key>()
    }

    fun interface Key {
        fun onPress(minecraft: Minecraft, key: Int, scanCode: Int, action: Int, modifiers: Int): EventResult
    }

    fun interface MouseScroll {
        fun onScroll(minecraft: Minecraft, deltaX: Double, deltaY: Double): EventResult
    }

    fun interface MouseClick {
        fun onClicked(minecraft: Minecraft, button: Int, action: Int, modifiers: Int): EventResult
    }
}
