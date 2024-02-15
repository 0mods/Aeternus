package team._0mods.aeternus.api.magic.research.player

import team._0mods.aeternus.api.magic.research.Research

interface PlayerResearch {
    val researches: List<Research>

    fun hasResearch(research: Research): Boolean = this.researches.contains(research)

    fun addResearch(vararg research: Research)
}
