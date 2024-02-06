package team._0mods.aeternus.api.magic.research.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.IResearch

interface IResearchRegistry {
    val researches: List<IResearch>

    fun getResearchById(id: ResourceLocation): IResearch?

    fun register(research: IResearch)
}