package team._0mods.aeternus.api.magic.research

import net.minecraft.resources.ResourceLocation

abstract class SimpleResearch(private val id: ResourceLocation): IResearch {
    private val required: MutableMap<ResourceLocation, IResearch> = mutableMapOf()
    private val triggers: MutableMap<ResourceLocation, IResearchTrigger> = mutableMapOf()

    init {
        this.register()
    }

    override fun getName(): ResourceLocation = this.id

    override fun getRequirementResearches(): MutableMap<ResourceLocation, IResearch> = required

    override fun addRequirementResearch(vararg research: IResearch) {
        research.forEach {
            required[it.getName()] = it
        }
    }

    override fun getTriggers(): MutableMap<ResourceLocation, IResearchTrigger> = triggers

    override fun addTriggers(vararg trigger: IResearchTrigger) {
        trigger.forEach {
            triggers[it.getName()] = it
        }
    }
}