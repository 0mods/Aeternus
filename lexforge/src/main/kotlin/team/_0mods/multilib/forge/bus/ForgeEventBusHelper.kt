/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.multilib.forge.bus

import com.google.common.collect.LinkedListMultimap
import com.google.common.collect.Multimap
import com.google.common.collect.Multimaps
import net.minecraftforge.eventbus.api.IEventBus
import team._0mods.multilib.event.forge.EventBusHelper
import java.util.*
import java.util.function.Consumer

object ForgeEventBusHelper: EventBusHelper<IEventBus> {
    private val eventBusMap: MutableMap<String, IEventBus> = Collections.synchronizedMap(hashMapOf())
    private val onRegistered: Multimap<String, Consumer<IEventBus>> = Multimaps.synchronizedMultimap(LinkedListMultimap.create())

    @JvmStatic
    fun registerModEvent(modId: String, bus: IEventBus) {
        if (eventBusMap.putIfAbsent(modId, bus) != null)
            throw IllegalStateException("Can't register event bus for mod '$modId' because it was previously registered!")

        onRegistered.get(modId).forEach { it.accept(bus) }
    }

    override fun whenAvailable(modId: String, bus: Consumer<IEventBus>) {
        if (eventBusMap.containsKey(modId))
            bus.accept(eventBusMap[modId]!!)
        else onRegistered.put(modId, bus)
    }

    override fun getModEventBus(modId: String): Optional<IEventBus> = Optional.ofNullable(eventBusMap[modId])
}
