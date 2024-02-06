package team._0mods.aeternus.api.magic.research.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.api.magic.research.IResearch
import team._0mods.aeternus.service.ServiceProvider

object ResearchRegistry: IResearchRegistry {
    private val researchMap: MutableMap<ResourceLocation, IResearch> = linkedMapOf()

    override val researches: List<IResearch>
        get() = researchMap.values.toList()

    override fun getResearchById(id: ResourceLocation): IResearch? = researchMap[id]

    override fun register(research: IResearch) {
        if (this.researchMap.values.stream().noneMatch { it.name == research.name })
            researchMap[research.name] = research
        else
            LOGGER.atWarn().log(
                "Oh... Mod: {} trying to register a research with id {}, because it is already registered! Skipping...",
                ServiceProvider.platform.getModNameByModId(research.name.namespace),
                research.name
            )
    }
}