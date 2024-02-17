package team._0mods.aeternus.service

import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.core.EtheriumHelper
import team._0mods.aeternus.service.core.EventHelper
import team._0mods.aeternus.service.core.PlatformHelper
import team._0mods.aeternus.service.core.RegistryHelper
import java.util.*
import kotlin.reflect.KClass

object ServiceProvider {
    val event: EventHelper = initPlatformed(EventHelper::class)
    val etheriumHelper: EtheriumHelper = initPlatformed(EtheriumHelper::class)
    val platform: PlatformHelper = initPlatformed(PlatformHelper::class)
    val registry = initPlatformed(RegistryHelper::class)

    fun <T> initPlatformed(clazz: KClass<T>): T where T: Any {
        val loaded: T = ServiceLoader.load(clazz.java).findFirst().orElseThrow { NullPointerException("Failed to load Service for ${clazz.simpleName}") }
        LOGGER.debug("Loading service {} for {}...", loaded, clazz)
        return loaded
    }
}