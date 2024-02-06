package team._0mods.aeternus.init

import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.api.AeternusPlugin
import team._0mods.aeternus.api.IAeternusPlugin
import team._0mods.aeternus.api.magic.research.registry.IResearchRegistry

@AeternusPlugin
class AeternusCorePlugin: IAeternusPlugin {
    override fun registerResearch(reg: IResearchRegistry) {
        LOGGER.info("Research Registry is initialized")
    }
}