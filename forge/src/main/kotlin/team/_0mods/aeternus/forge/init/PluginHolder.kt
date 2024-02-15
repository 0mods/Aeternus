package team._0mods.aeternus.forge.init

import com.mojang.logging.LogUtils

import net.minecraftforge.fml.ModList
import team._0mods.aeternus.api.*
import team._0mods.aeternus.common.impl.registry.*

object PluginHolder {
    private val logger = LogUtils.getLogger()
    private val list: MutableList<IAeternusPlugin> = mutableListOf()

    fun loadPlugins() {
        ModList.get().allScanData.forEach { data ->
            data.annotations.forEach { annot ->
                if (annot.annotationType.className.equals(AeternusPlugin::class.java)) {
                    try {
                        val clazz = Class.forName(annot.memberName)
                        if (IAeternusPlugin::class.java.isAssignableFrom(clazz)) {
                            val plugin: IAeternusPlugin =
                                clazz.getDeclaredConstructor().newInstance() as IAeternusPlugin
                            list.add(plugin)
                            logger.info("Plugin {} has been registered!", annot.memberName)
                        }
                    } catch (e: Exception) {
                        logger.error(LogUtils.FATAL_MARKER, "Error during loading plugin: {}", annot.memberName, e)
                    }
                }
            }
        }

        list.forEach {
            it.registerResearch(ResearchRegistryImpl(it.modId))
            it.registerResearchTriggers(ResearchTriggerRegistryImpl(it.modId))
        }
    }
}