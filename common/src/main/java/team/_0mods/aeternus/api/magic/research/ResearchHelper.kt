package team._0mods.aeternus.api.magic.research

import team._0mods.aeternus.api.util.mcemulate.MCPlayer
import team._0mods.aeternus.platformredirect.api.util.asPlayer
import team._0mods.aeternus.service.ResearchHelper as RedirectHelper

object ResearchHelper {
    @JvmStatic
    val MCPlayer.researches: List<Research>
        get() = RedirectHelper.getResearches(this.asPlayer)

    @JvmStatic
    fun MCPlayer.hasResearch(research: Research) = RedirectHelper.hasResearch(this.asPlayer, research)

    @JvmStatic
    fun MCPlayer.hasResearches(vararg researches: Research) = RedirectHelper.hasResearches(this.asPlayer, *researches)

    @JvmStatic
    fun MCPlayer.addResearch(vararg researches: Research) = RedirectHelper.addResearch(this.asPlayer, *researches)

    fun MCPlayer.canOpen(research: Research) = RedirectHelper.canOpen(this.asPlayer, research)
}
