package team._0mods.aeternus.api

import team._0mods.aeternus.api.magic.research.registry.IResearchRegistry

annotation class AeternusPlugin

interface IAeternusPlugin {
    fun registerResearch(reg: IResearchRegistry)
}