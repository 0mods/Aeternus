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

interface ScreenInputEvent {
    companion object {
        @JvmField val MOUSE_SCROLLED_PRE = EventFactory.createEventResult<MouseScroll>()
        @JvmField val MOUSE_SCROLLED_POST = EventFactory.createEventResult<MouseScroll>()

        @JvmField val MOUSE_CLICKED_PRE = EventFactory.createEventResult<MouseClick>()
        @JvmField val MOUSE_CLICKED_POST = EventFactory.createEventResult<MouseClick>()

        @JvmField val MOUSE_RELEASED_PRE = EventFactory.createEventResult<MouseRelease>()
        @JvmField val MOUSE_RELEASED_POST = EventFactory.createEventResult<MouseRelease>()

        @JvmField val MOUSE_DRAGGED_PRE = EventFactory.createEventResult<MouseDrag>()
        @JvmField val MOUSE_DRAGGED_POST = EventFactory.createEventResult<MouseDrag>()

        @JvmField val CHAR_TYPED_PRE = EventFactory.createEventResult<KeyTyped>()
        @JvmField val CHAR_TYPED_POST = EventFactory.createEventResult<KeyTyped>()

        @JvmField val KEY_RELEASED_PRE = EventFactory.createEventResult<KeyReleased>()
        @JvmField val KEY_RELEASED_POST = EventFactory.createEventResult<KeyReleased>()

        @JvmField val KEY_PRESSED_PRE = EventFactory.createEventResult<KeyPressed>()
        @JvmField val KEY_PRESSED_POST = EventFactory.createEventResult<KeyPressed>()
    }

    fun interface KeyPressed {
        fun onPress(minecraft: Minecraft, screen: Screen, key: Int, scanCode: Int, modifiers: Int): EventResult
    }

    fun interface KeyReleased {
        fun onRelease(minecraft: Minecraft, screen: Screen, keyCode: Int, scanCode: Int, modifiers: Int): EventResult
    }

    fun interface KeyTyped {
        fun onPress(minecraft: Minecraft, screen: Screen, character: Char, keyCode: Int): EventResult
    }

    fun interface MouseScroll {
        fun onScroll(minecraft: Minecraft, screen: Screen, mouseX: Double, mouseY: Double, amountX: Double, amountY: Double): EventResult
    }

    fun interface MouseRelease {
        fun onRelease(minecraft: Minecraft, screen: Screen, x: Double, y: Double, button: Int): EventResult
    }

    fun interface MouseDrag {
        fun onDrag(minecraft: Minecraft, screen: Screen, mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double): EventResult
    }

    fun interface MouseClick {
        fun onClicked(minecraft: Minecraft, screen: Screen, mouseX: Double, mouseY: Double, button: Int): EventResult
    }
}
