package team._0mods.aeternus.api.registry

import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.IResearch

interface IResearchRegistry {
    val researches: List<IResearch>

    fun getResearchById(id: ResourceLocation): IResearch?

    fun getIdByResearch(research: IResearch): ResourceLocation?

    @ApiStatus.ScheduledForRemoval
    @Deprecated("Deprecated.",
        ReplaceWith("register(net.minecraft.resources.ResourceLocation, team._0mods.aeternus.api.magic.research.IResearch)"),
        level = DeprecationLevel.ERROR
    )
    fun register(research: IResearch) = register(research.name.toString(), research)

    fun register(id: String, research: IResearch)

    fun registerAll(vararg researches: Pair<String, IResearch>) {
        for (researchPair in researches) {
            val id = researchPair.first
            val research = researchPair.second

            register(id, research)
        }
    }

    fun getResearchListByIdList(id: List<ResourceLocation>): List<IResearch>
}