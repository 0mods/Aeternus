package team._0mods.aeternus.common.impl.research

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.ResearchTrigger

abstract class SimpleResearch(private val id: ResourceLocation): Research {
    private val required: MutableList<Research> = mutableListOf()
    private val triggerList: MutableList<ResearchTrigger> = mutableListOf()

    override val name: ResourceLocation
        get() = this.id

    override val depends: List<Research>
        get() = this.required

    override val triggers: List<ResearchTrigger>
        get() = this.triggerList

    override fun addRequirementResearch(vararg research: Research) {
        required.addAll(research)
    }

    override fun addTriggers(vararg trigger: ResearchTrigger) {
        triggerList.addAll(trigger)
    }
}