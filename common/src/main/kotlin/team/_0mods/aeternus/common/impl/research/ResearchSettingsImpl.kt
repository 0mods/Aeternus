package team._0mods.aeternus.common.impl.research

import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.ResearchSettings
import team._0mods.aeternus.api.magic.research.ResearchTrigger

internal class ResearchSettingsImpl(
    private val triggerList: MutableList<ResearchTrigger>,
    private val dependencies: MutableList<Research>
): ResearchSettings {
    override val depends: List<Research>
        get() = dependencies

    override val triggers: List<ResearchTrigger>
        get() = triggerList

    override fun addRequirementResearch(vararg research: Research) {
        triggerList
    }

    override fun addTriggers(vararg trigger: ResearchTrigger) {

    }
}