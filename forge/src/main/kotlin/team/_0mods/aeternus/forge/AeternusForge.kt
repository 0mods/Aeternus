/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forge

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.common.commonInit
import team._0mods.aeternus.forge.init.AFRegistryHandler
import team._0mods.aeternus.forge.init.PluginHolder
import team._0mods.multilib.event.core.EventHandler
import team._0mods.multilib.forge.bus.ForgeEventBusHelper
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(ModId)
class AeternusForge {
    init {
        // MULTILIB START
        ForgeEventBusHelper.registerModEvent(team._0mods.multilib.ModId, FMLJavaModLoadingContext.get().modEventBus)
        EventHandler.init()
        // MULTILIB END
        AFRegistryHandler.init(MOD_BUS)
        commonInit()
        PluginHolder.loadPlugins()
    }
}
