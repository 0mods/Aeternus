package team._0mods.aeternus.init

import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.ModId
import team._0mods.aeternus.api.AeternusPlugin
import team._0mods.aeternus.api.IAeternusPlugin
import team._0mods.aeternus.api.registry.IResearchRegistry
import team._0mods.aeternus.api.registry.IResearchTriggerRegistry

@AeternusPlugin
class AeternusCorePlugin: IAeternusPlugin {
    companion object {
        lateinit var researchRegistry: IResearchRegistry
        lateinit var triggerRegistry: IResearchTriggerRegistry
    }

    override val modId: String
        get() = ModId

    override fun registerResearch(reg: IResearchRegistry) {
        researchRegistry = reg
        LOGGER.info("Research Registry is initialized")
    }

    override fun registerResearchTriggers(reg: IResearchTriggerRegistry) {
        triggerRegistry = reg
    }
}