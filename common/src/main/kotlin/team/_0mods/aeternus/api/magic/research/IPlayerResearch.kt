package team._0mods.aeternus.api.magic.research

interface IPlayerResearch {
    fun getOpenedResearches(): Array<out IResearch>

    fun hasResearch(research: IResearch): Boolean = this.getOpenedResearches().contains(research)

    operator fun plus(research: IResearch)

    operator fun minus(research: IResearch)
}