package team._0mods.aeternus.api.registry.impl

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.api.magic.research.IResearchTrigger
import team._0mods.aeternus.api.registry.IResearchTriggerRegistry
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.service.ServiceProvider

class ResearchTriggerRegistry(private val modId: String): IResearchTriggerRegistry {
    private val triggerMap: MutableMap<ResourceLocation, IResearchTrigger> = linkedMapOf()

    override val triggers: List<IResearchTrigger>
        get() = triggerMap.values.toList()

    override fun getResearchTriggerById(id: ResourceLocation): IResearchTrigger? =
        triggerMap[id]

    override fun register(id: String, research: IResearchTrigger) {
        val resLocId = "${this.modId}:$id".rl

        if (triggerMap.keys.stream().noneMatch { it == resLocId })
            triggerMap[resLocId] = research
        else
            LOGGER.atWarn().log(
                "Oh... Mod: {} trying to register a research with id {}, because research with this id is already registered! Skipping...",
                ServiceProvider.platform.getModNameByModId(resLocId.namespace),
                resLocId
            )

    }

    override fun getResearchTriggerListByIdList(id: List<ResourceLocation>): List<IResearchTrigger> = triggerMap.fromMapToListByList(id)
}