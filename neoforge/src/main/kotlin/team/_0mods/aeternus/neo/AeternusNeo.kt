package team._0mods.aeternus.neo

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.neo.api.modEvent
import team._0mods.aeternus.neo.api.tab.AddItemToTabHandler
import team._0mods.aeternus.neo.init.ANRegistryHandler
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(ModId)
class AeternusNeo(bus: IEventBus) {
    init {
        AddItemToTabHandler.init()
        commonInit(bus)
    }

    private fun commonInit(bus: IEventBus) {
        bus.addListener<FMLCommonSetupEvent> {
            ANRegistryHandler.init(bus)
        }
    }
}
