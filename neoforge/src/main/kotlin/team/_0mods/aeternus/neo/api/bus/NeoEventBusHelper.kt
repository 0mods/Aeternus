/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.neo.api.bus

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import team._0mods.aeternus.api.forge.EventBusHelper
import java.util.*
import java.util.function.Consumer

object NeoEventBusHelper: EventBusHelper<IEventBus> {
    override fun whenAvailable(modId: String, bus: Consumer<IEventBus>) {
        val eventBus = this.getModEventBus(modId).orElseThrow { IllegalStateException("Mod '$modId' is not available") }
        bus.accept(eventBus)
    }

    override fun getModEventBus(modId: String): Optional<IEventBus> =
        ModList.get().getModContainerById(modId).map(ModContainer::getEventBus)
}