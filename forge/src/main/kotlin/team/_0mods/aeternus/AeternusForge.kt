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

import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.loading.FMLEnvironment
import team._0mods.aeternus.common.*
import team._0mods.aeternus.common.init.config.forge.AeternusClientConfigImpl
import team._0mods.aeternus.common.init.config.forge.AeternusCommonConfigImpl
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.registerConfig

@Mod(ModId)
class AeternusForge {
    init {
        EventBuses.registerModEventBus(ModId, MOD_BUS)
        registerConfig(ModConfig.Type.COMMON, AeternusCommonConfigImpl.builded, "$ModId/common.toml")
        registerConfig(ModConfig.Type.CLIENT, AeternusClientConfigImpl.builded, "$ModId/client.toml")
        commonInit()
        if (FMLEnvironment.dist.isClient)
            MOD_BUS.addListener(this::initClient)
    }

    private fun initClient(e: FMLClientSetupEvent) {
        clientInit()
    }
}
