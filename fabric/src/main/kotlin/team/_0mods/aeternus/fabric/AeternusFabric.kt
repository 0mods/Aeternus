@file:JvmName("AeternusFabric")

package team._0mods.aeternus.fabric

import team._0mods.aeternus.fabric.init.AFRegistryHandler
import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.fabric.init.PluginHolder

fun startCommon() {
    LOGGER.info("Hello Minecraft world from Common side!")
    AFRegistryHandler.init()
    PluginHolder.loadPlugins()
}

fun startClient() {
    LOGGER.info("Hello Minecraft world from Client side!")
}
