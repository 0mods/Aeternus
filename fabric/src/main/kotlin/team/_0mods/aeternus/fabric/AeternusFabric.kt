package team._0mods.aeternus.fabric

import team._0mods.aeternus.fabric.init.AFRegistryHandler
import team._0mods.aeternus.LOGGER

fun startCommon() {
    LOGGER.info("Hello Minecraft world from Common side!")
    AFRegistryHandler.init()
}

fun startClient() {
    LOGGER.info("Hello Minecraft world from Client side!")
}
