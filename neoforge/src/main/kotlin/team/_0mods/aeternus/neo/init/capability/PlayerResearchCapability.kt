package team._0mods.aeternus.neo.init.capability

import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.api.magic.research.IPlayerResearch
import team._0mods.aeternus.api.magic.research.IResearch

class PlayerResearchCapability: IPlayerResearch {
    private val researchList: MutableList<IResearch> = mutableListOf()

    override val researches: List<IResearch>
        get() = researchList.toList() // Copy from list

    override fun addResearch(vararg research: IResearch) {
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