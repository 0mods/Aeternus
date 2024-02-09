package team._0mods.aeternus.api

import team._0mods.aeternus.api.registry.IResearchRegistry
import team._0mods.aeternus.api.registry.IResearchTriggerRegistry

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class AeternusPlugin // Annotation for Forge.

interface IAeternusPlugin {
    val modId: String

    fun registerResearch(reg: IResearchRegistry) {}

    fun registerResearchTriggers(reg: IResearchTriggerRegistry) {}
}