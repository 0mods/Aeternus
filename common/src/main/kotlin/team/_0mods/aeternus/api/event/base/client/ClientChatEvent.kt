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

import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.core.EventFactory.Companion.createEventResult
import team._0mods.aeternus.api.event.result.EventResult
import team._0mods.aeternus.api.event.result.EventResultHolder

interface ClientChatEvent {
    companion object {
        @JvmField
        val SEND: Event<ClientChatEvent> = createEventResult()

        @JvmField
        val RECEIVED: Event<Received> = createEventResult()
    }

    fun interface Send {
        fun send(message: String?, component: Component?): EventResult?
    }

    fun interface Received {
        fun process(type: ChatType.Bound?, message: Component?): EventResultHolder<Component?>?
    }
}
