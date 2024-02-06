package team._0mods.aeternus.api.magic.research

interface IPlayerResearch {
    val researches: List<IResearch>

    fun hasResearch(research: IResearch): Boolean = this.researches.contains(research)

    fun addResearch(vararg research: IResearch)
}
