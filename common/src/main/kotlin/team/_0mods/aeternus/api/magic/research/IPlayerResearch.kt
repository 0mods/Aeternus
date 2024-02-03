package team._0mods.aeternus.api.magic.research

interface IPlayerResearch {
    fun getResearches(): MutableMap<IResearch, Boolean>

    fun hasResearch(research: IResearch): Boolean = this.getResearches().containsKey(research)
            && (this.getResearches()[research] != null && this.getResearches()[research]!!)

    operator fun set(research: IResearch, have: Boolean)

    operator fun plus(research: IResearch)
}
