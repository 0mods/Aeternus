package team._0mods.aeternus.api.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.IResearchTrigger

interface IResearchTriggerRegistry {
    val triggers: List<IResearchTrigger>

    fun getResearchTriggerById(id: ResourceLocation): IResearchTrigger?

    fun register(id: String, research: IResearchTrigger)
}