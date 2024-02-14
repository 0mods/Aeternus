package team._0mods.aeternus.fabric.init

import net.fabricmc.loader.api.FabricLoader
import team._0mods.aeternus.ModId
import team._0mods.aeternus.api.IAeternusPlugin
import team._0mods.aeternus.impl.registry.ResearchRegistry
import team._0mods.aeternus.impl.registry.ResearchTriggerRegistry
import java.util.stream.Collectors

object PluginHolder {
    fun loadPlugins() {
        val listOfPlugins = FabricLoader.getInstance()
            .getEntrypointContainers("${ModId}_plugin", IAeternusPlugin::class.java)
            .stream()
            .map { it.entrypoint }
            .collect(Collectors.toList())

        listOfPlugins.forEach {
            it.registerResearch(ResearchRegistry(it.modId))
            it.registerResearchTriggers(ResearchTriggerRegistry(it.modId))
        }
    }
}