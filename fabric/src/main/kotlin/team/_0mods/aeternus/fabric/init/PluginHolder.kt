package team._0mods.aeternus.fabric.init

import net.fabricmc.loader.api.FabricLoader
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.api.IAeternusPlugin
import team._0mods.aeternus.common.impl.registry.ResearchRegistryImpl
import java.util.stream.Collectors

object PluginHolder {
    fun loadPlugins() {
        val listOfPlugins = FabricLoader.getInstance()
            .getEntrypointContainers("${ModId}_plugin", IAeternusPlugin::class.java)
            .stream()
            .map { it.entrypoint }
            .collect(Collectors.toList())

        listOfPlugins.forEach {
            it.registerResearch(ResearchRegistryImpl(it.modId))
            it.registerResearchTriggers(team._0mods.aeternus.common.impl.registry.ResearchTriggerRegistryImpl(it.modId))
        }
    }
}