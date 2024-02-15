package team._0mods.aeternus.api.registry

import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.Research

interface ResearchRegistry {
    val researches: List<Research>

    fun getResearchById(id: ResourceLocation): Research?

    fun getIdByResearch(research: Research): ResourceLocation?

    @ApiStatus.ScheduledForRemoval
    @Deprecated("Deprecated.",
        ReplaceWith("register(ResourceLocation, Research)"),
        level = DeprecationLevel.ERROR
    )
    fun register(research: Research) = register(research.name.toString(), research)

    fun register(id: String, research: Research)

    fun registerAll(vararg researches: Pair<String, Research>) {
        for (researchPair in researches) {
            val id = researchPair.first
            val research = researchPair.second

            register(id, research)
        }
    }

    fun getResearchListByIdList(id: List<ResourceLocation>): List<Research>
}