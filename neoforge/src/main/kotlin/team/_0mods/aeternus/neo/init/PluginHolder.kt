package team._0mods.aeternus.neo.init

import com.mojang.logging.LogUtils
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.neoforged.fml.ModList
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import team._0mods.aeternus.api.AeternusPlugin
import team._0mods.aeternus.api.IAeternusPlugin
import team._0mods.aeternus.api.registry.impl.ResearchRegistry
import team._0mods.aeternus.api.registry.impl.ResearchTriggerRegistry

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
                            val plugin: IAeternusPlugin = clazz.getDeclaredConstructor().newInstance() as IAeternusPlugin
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
            it.registerResearch(ResearchRegistry(it.modId))
            it.registerResearchTriggers(ResearchTriggerRegistry(it.modId))
        }
    }
}
