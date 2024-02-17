package team._0mods.aeternus.common.init

import team._0mods.aeternus.api.AeternusPlugin
import team._0mods.aeternus.api.IAeternusPlugin
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.api.registry.ResearchTriggerRegistry
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.common.ModId

@AeternusPlugin
class AeternusCorePlugin: IAeternusPlugin {
    companion object {
        lateinit var researchRegistry: ResearchRegistry
        lateinit var triggerRegistry: ResearchTriggerRegistry
    }

    override val modId: String
        get() = ModId

    override fun registerResearch(reg: ResearchRegistry) {
        researchRegistry = reg
        LOGGER.info("Research Registry is initialized")
    }

    override fun registerResearchTriggers(reg: ResearchTriggerRegistry) {
        triggerRegistry = reg
    }
}