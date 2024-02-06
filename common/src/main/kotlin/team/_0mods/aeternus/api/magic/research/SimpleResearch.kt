package team._0mods.aeternus.api.magic.research

import net.minecraft.resources.ResourceLocation

abstract class SimpleResearch(private val id: ResourceLocation): IResearch {
    private val required: MutableList<IResearch> = mutableListOf()
    private val triggerList: MutableList<IResearchTrigger> = mutableListOf()

    override val name: ResourceLocation
        get() = this.id

    override val depends: List<IResearch>
        get() = this.required

    override val triggers: List<IResearchTrigger>
        get() = this.triggerList

    override fun addRequirementResearch(vararg research: IResearch) {
        required.addAll(research)
    }

    override fun addTriggers(vararg trigger: IResearchTrigger) {
        triggerList.addAll(trigger)
    }
}