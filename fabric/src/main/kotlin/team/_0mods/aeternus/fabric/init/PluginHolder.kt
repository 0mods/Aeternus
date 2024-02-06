package team._0mods.aeternus.fabric.init

import net.fabricmc.loader.api.FabricLoader
import team._0mods.aeternus.ModId
import team._0mods.aeternus.api.IAeternusPlugin
import team._0mods.aeternus.api.magic.research.registry.ResearchRegistry
import java.util.stream.Collectors

object PluginHolder {
    fun loadPlugins() {
        val listOfPlugins = this.getInstances("${ModId}_plugin", IAeternusPlugin::class.java)

        listOfPlugins.forEach {
            it.registerResearch(ResearchRegistry)
        }
    }

    @Suppress("SameParameterValue")
    private fun <T> getInstances(entrypoint: String, inst: Class<T>): List<T> =
        FabricLoader.getInstance()
            .getEntrypointContainers(entrypoint, inst)
            .stream()
            .map { it.entrypoint }
            .collect(Collectors.toList())
}