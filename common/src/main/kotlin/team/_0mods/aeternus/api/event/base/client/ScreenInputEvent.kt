/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.Minecraft
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult

interface ScreenInputEvent {
    companion object {
        @JvmField val KEY_PRESSED_PRE = EventFactory.createEventResult<KeyPressed>()
        @JvmField val KEY_PRESSED_POS = EventFactory.createEventResult<KeyPressed>()

        @JvmField val MOUSE_CLICKED_PRE = EventFactory.createEventResult<MouseClick>()
        @JvmField val MOUSE_CLICKED_POST = EventFactory.createEventResult<MouseClick>()

        @JvmField val MOUSE_RELEASED_PRE = EventFactory.createEventResult<MouseRelease>()
        @JvmField val MOUSE_RELEASED_POST = EventFactory.createEventResult<MouseRelease>()

        @JvmField val MOUSE_DRAGGED_PRE = EventFactory.createEventResult<MouseDrag>()
        @JvmField val MOUSE_DRAGGED_POST = EventFactory.createEventResult<MouseDrag>()

        @JvmField val KEY_RELEASED_PRE = EventFactory.createEventResult<KeyReleased>()
        @JvmField val KEY_RELEASED_POST = EventFactory.createEventResult<KeyReleased>()

        @JvmField val CHAR_TYPED_PRE = EventFactory.createEventResult<KeyTyped>()
        @JvmField val CHAR_TYPED_POST = EventFactory.createEventResult<KeyTyped>()
    }

    fun interface KeyPressed {
        fun onPress(minecraft: Minecraft, key: Int, scanCode: Int, action: Int, modifiers: Int): EventResult
    }

    fun interface KeyReleased {
        fun onRelease(minecraft: Minecraft, key: Int, scanCode: Int, action: Int, modifiers: Int): EventResult
    }

    fun interface KeyTyped {
        fun onPress(minecraft: Minecraft, key: Int, scanCode: Int, action: Int): EventResult
    }

    fun interface MouseScroll {
        fun onScroll(minecraft: Minecraft, amount: Double): EventResult
    }

    fun interface MouseRelease {
        fun onRelease(minecraft: Minecraft, x: Double, y: Double, z: Double, button: Int): EventResult
    }

    fun interface MouseDrag {
        /* (x, y, z)From - Start mouse cursor position
        * x, y, z - end mouse cursor position
        */
        fun onDrag(minecraft: Minecraft, xFrom: Double, yFrom: Double, zFrom: Double, x: Double, y: Double, z: Double): EventResult
    }

    fun interface MouseClick {
        fun onClicked(minecraft: Minecraft, button: Int, action: Int, modifiers: Int): EventResult
    }
}
