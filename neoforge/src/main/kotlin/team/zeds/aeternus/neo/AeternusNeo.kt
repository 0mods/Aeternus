package team.zeds.aeternus.neo

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import team.zeds.aeternus.init.ModId
import team.zeds.aeternus.init.initCommon
import team.zeds.aeternus.neo.api.tab.AddItemToTabHandler

@Mod(ModId)
class AeternusNeo(bus: IEventBus) {
    init {
//        AddItemToTabHandler.init()
        initCommon()
    }
}