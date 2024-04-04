/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.multilib.neo.event

import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import net.neoforged.neoforge.common.NeoForge
import team._0mods.multilib.ModId
import team._0mods.multilib.event.core.EventHandler
import team._0mods.multilib.neo.bus.NeoEventBusHelper

object EventHandlerImpl: EventHandler() {
    @OnlyIn(Dist.CLIENT)
    override fun registerClient() {
        NeoForge.EVENT_BUS.register(ClientEventsHandler::class.java)
        NeoEventBusHelper.whenAvailable(ModId) {
            it.register(ClientEventsHandler.ModEventHandler::class.java)
        }
    }

    override fun registerCommon() {
        NeoForge.EVENT_BUS.register(CommonEventsHandler::class.java)
        NeoEventBusHelper.whenAvailable(ModId) {
            it.register(CommonEventsHandler.ModEventHandler::class.java)
        }
    }
}
