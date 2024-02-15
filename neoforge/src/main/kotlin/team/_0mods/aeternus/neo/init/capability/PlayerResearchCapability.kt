package team._0mods.aeternus.neo.init.capability

import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.api.magic.research.player.PlayerResearch
import team._0mods.aeternus.api.magic.research.Research

class PlayerResearchCapability: PlayerResearch {
    private val researchList: MutableList<Research> = mutableListOf()

    override val researches: List<Research>
        get() = researchList.toList() // Copy from list

    override fun addResearch(vararg research: Research) {
        for (researchImpl in research) {
            if (this.researchList.stream().noneMatch { it.name == researchImpl.name }) researchList.add(researchImpl)
            else {
                LOGGER.atWarn().log(
                    "Player already equals {} research. Why you will try to add it again?",
                    researchImpl.name
                )
                continue
            }
        }
    }
}