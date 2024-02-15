package team._0mods.aeternus.api.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.ResearchTrigger

interface ResearchTriggerRegistry {
    val triggers: List<ResearchTrigger>

    fun getResearchTriggerById(id: ResourceLocation): ResearchTrigger?

    fun register(id: String, research: ResearchTrigger)

    fun getResearchTriggerListByIdList(id: List<ResourceLocation>): List<ResearchTrigger>
}