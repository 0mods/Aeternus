package team._0mods.aeternus.neo

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import team._0mods.aeternus.ModId
import team._0mods.aeternus.neo.init.ANRegistryHandler

@Mod(ModId)
class AeternusNeo(bus: IEventBus) {
    init {
        ANRegistryHandler.init(bus)
    }
}
