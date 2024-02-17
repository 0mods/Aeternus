package team._0mods.aeternus.common.impl.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.revert
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.ServiceProvider

class ResearchRegistryImpl(private val modId: String): ResearchRegistry {
    private val researchMap: MutableMap<ResourceLocation, Research> = linkedMapOf()

    override val researches: List<Research>
        get() = researchMap.values.toList()

    override fun getResearchById(id: ResourceLocation): Research? = researchMap[id]

    override fun getIdByResearch(research: Research): ResourceLocation? = researchMap.revert()[research]

    override fun register(id: String, research: Research) {
        val resLocId = "${this.modId}:$id".rl
        if (researchMap.keys.stream().noneMatch { it == resLocId })
            researchMap[resLocId] = research
        else
            LOGGER.atWarn().log(
                "Oh... Mod: {} trying to register a research with id {}, because research with this id is already registered! Skipping...",
                ServiceProvider.platform.getModNameByModId(resLocId.namespace),
                resLocId
            )
    }

    override fun getResearchListByIdList(id: List<ResourceLocation>): List<Research> = researchMap.fromMapToListByList(id)
}