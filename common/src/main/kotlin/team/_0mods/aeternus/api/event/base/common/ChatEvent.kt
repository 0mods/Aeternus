/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.common

import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import team._0mods.aeternus.api.event.core.EventFactory
import team._0mods.aeternus.api.event.result.EventResult

interface ChatEvent {
    companion object {
        @JvmField val DECORATE = EventFactory.createNoResult<Decorate>()
        @JvmField val RECEIVED = EventFactory.createEventResult<Received>()
    }

    fun interface Decorate {
        fun decode(player: ServerPlayer, component: ChatComponent)
    }

    fun interface Received {
        fun received(player: ServerPlayer?, component: Component): EventResult
    }

    interface ChatComponent {
        var component: Component
    }
}
