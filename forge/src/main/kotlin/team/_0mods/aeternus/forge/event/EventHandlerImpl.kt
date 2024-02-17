/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forge.event

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.MinecraftForge
import team._0mods.aeternus.api.event.core.EventHandler
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.forge.api.bus.ForgeEventBusHelper
import team._0mods.aeternus.forge.api.event.ClientEventsHandler
import team._0mods.aeternus.forge.api.event.CommonEventsHandler

object EventHandlerImpl: EventHandler() {
    @OnlyIn(Dist.CLIENT)
    override fun registerClient() {
        MinecraftForge.EVENT_BUS.register(ClientEventsHandler::class.java)
        ForgeEventBusHelper.whenAvailable(ModId) {
            it.register(ClientEventsHandler::class.java)
        }
    }

    override fun registerCommon() {
        MinecraftForge.EVENT_BUS.register(CommonEventsHandler::class.java)
        ForgeEventBusHelper.whenAvailable(ModId) {
            it.register(CommonEventsHandler::class.java)
        }
    }

    override fun registerServer() {}
}
