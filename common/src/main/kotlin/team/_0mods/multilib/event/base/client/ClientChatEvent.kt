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

import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import team._0mods.multilib.event.base.Event
import team._0mods.multilib.event.core.EventFactory.Companion.createEventResult
import team._0mods.multilib.event.result.EventResult
import team._0mods.multilib.event.result.EventResultHolder

interface ClientChatEvent {
    companion object {
        @JvmField val SEND: Event<Send> = createEventResult()

        @JvmField val RECEIVED: Event<Received> = createEventResult()
    }

    fun interface Send {
        fun send(message: String, component: Component?): EventResult
    }

    fun interface Received {
        fun process(type: ChatType.Bound, message: Component): EventResultHolder<Component>
    }
}
