package team._0mods.aeternus.service

import team._0mods.aeternus.LOGGER
import team._0mods.aeternus.service.core.IEtheriumHelper
import team._0mods.aeternus.service.core.IPlatformHelper
import java.util.ServiceLoader
import kotlin.reflect.KClass

object ServiceProvider {
    val etheriumHelper: IEtheriumHelper = initPlatformed(IEtheriumHelper::class)
    val platform: IPlatformHelper = initPlatformed(IPlatformHelper::class)

    fun <T> initPlatformed(clazz: KClass<T>): T where T: Any {
        val loaded: T = ServiceLoader.load(clazz.java).findFirst().orElseThrow { NullPointerException("Failed to load Service for ${clazz.simpleName}") }
        LOGGER.debug("Loading service {} for {}...", loaded, clazz)
        return loaded
    }
}