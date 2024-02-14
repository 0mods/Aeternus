package team._0mods.aeternus.impl.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.api.magic.research.IResearch
import team._0mods.aeternus.api.registry.IResearchRegistry
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.revert
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.service.ServiceProvider

class ResearchRegistry(private val modId: String): IResearchRegistry {
    private val researchMap: MutableMap<ResourceLocation, IResearch> = linkedMapOf()

    override val researches: List<IResearch>
        get() = researchMap.values.toList()

    override fun getResearchById(id: ResourceLocation): IResearch? = researchMap[id]

    override fun getIdByResearch(research: IResearch): ResourceLocation? = researchMap.revert()[research]

    override fun register(id: String, research: IResearch) {
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

    override fun getResearchListByIdList(id: List<ResourceLocation>): List<IResearch> = researchMap.fromMapToListByList(id)
}