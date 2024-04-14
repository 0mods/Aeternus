package team._0mods.aeternus.api.magic.research

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.common.impl.research.ResearchBookMetadataImpl
import team._0mods.aeternus.common.impl.research.ResearchSettingsImpl
import team._0mods.multilib.util.rl

// Research Settings
fun ResearchSettings.Companion.of(vararg researchSettings: Pair<ResearchTrigger, Research>): ResearchSettings {
    val triggerList = mutableListOf<ResearchTrigger>()
    val researchList = mutableListOf<Research>()
    researchSettings.forEach {
        val research = it.second
        val trigger = it.first
        triggerList.add(trigger)
        researchList.add(research)
    }

    return ResearchSettingsImpl(triggerList, researchList)
}

fun ResearchSettings.Companion.of(triggers: List<ResearchTrigger>, parents: List<Research>): ResearchSettings =
    ResearchSettingsImpl(triggers.toMutableList(), parents.toMutableList())

fun ResearchSettings.Companion.of(triggers: Array<ResearchTrigger>, parents: Array<Research>) = this.of(triggers.toList(), parents.toList())

fun ResearchSettings.Companion.of(triggers: List<ResearchTrigger>) = this.of(triggers, listOf())

fun ResearchSettings.Companion.of(vararg triggers: ResearchTrigger) = this.of(triggers.toList())

fun ResearchSettings.Companion.of(triggers: Array<ResearchTrigger>) = this.of(triggers.toList())

// Research Metadata
fun ResearchBookMetadata.Companion.of(title: Component, desc: Component, icon: ResourceLocation, pos: Pair<Int, Int>): ResearchBookMetadata =
    ResearchBookMetadataImpl(title, desc, icon, pos)

fun ResearchBookMetadata.Companion.of(title: Component, desc: Component, icon: String, pos: Pair<Int, Int>): ResearchBookMetadata =
    this.of(title, desc, icon.rl, pos)

fun ResearchBookMetadata.Companion.of(title: Component, desc: Component, icon: ResourceLocation, x: Int, y: Int): ResearchBookMetadata =
    this.of(title, desc, icon, x to y)

fun ResearchBookMetadata.Companion.of(title: Component, desc: Component, icon: String, x: Int, y: Int): ResearchBookMetadata =
    this.of(title, desc, icon, x to y)
