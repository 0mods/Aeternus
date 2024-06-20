/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus

//import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.energy.EnergyStorage
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.items.ItemStackHandler
import team._0mods.aeternus.common.*

@Mod(ModId)
class AeternusForge {
    init {
        val bus = FMLJavaModLoadingContext.get().modEventBus

//        EventBuses.registerModEventBus(ModId, bus)
        commonInit()
        if (FMLEnvironment.dist.isClient)
            bus.addListener(this::initClient)

        ForgeCapabilities.ITEM_HANDLER
    }

    private fun initClient(e: FMLClientSetupEvent) {
        clientInit()
    }
}
