package team._0mods.aeternus.fabric

import team._0mods.aeternus.fabric.init.ANRegistryHandler
import team._0mods.aeternus.init.LOGGER

fun startCommon() {
    LOGGER.info("Hello Minecraft world from Common side!")
    ANRegistryHandler.init()

}

fun startClient() {
    LOGGER.info("Hello Minecraft world from Client side!")
}
