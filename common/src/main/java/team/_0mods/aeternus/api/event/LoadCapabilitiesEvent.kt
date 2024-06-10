/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event

import dev.architectury.event.Event
import dev.architectury.event.EventFactory
import team._0mods.aeternus.api.capability.CapabilityInstance
import team._0mods.aeternus.api.capability.ICapabilityDispatcher

fun interface LoadCapabilitiesEvent {
    companion object {
        @JvmField
        val EVENT: Event<LoadCapabilitiesEvent> = EventFactory.createLoop()
    }

    fun register(capability: CapabilityEventInstance)

    class CapabilityEventInstance(val provider: ICapabilityDispatcher, private val caps: MutableList<CapabilityInstance>) {
        fun addCapability(cap: CapabilityInstance) {
            caps.add(cap)
            cap.provider = provider
        }
    }
}
