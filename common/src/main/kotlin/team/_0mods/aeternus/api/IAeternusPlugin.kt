package team._0mods.aeternus.api

import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.registry.*

/**
 * Starts registration of the current plugin.
 * Works only on (Neo)Forge(d) Mod Loader and only with [IAeternusPlugin]
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class AeternusPlugin

/**
 * Plugin base interface. For registry your plugin:
 *
 * On Fabric use entrypoint with "aeternus_plugin". [About entrypoints read there](https://fabricmc.net/wiki/documentation:entrypoint)
 *
 * On (Neo)Forge(d) use [AeternusPlugin] above plugin class
 */
interface IAeternusPlugin {
    /**
     * Plugin's mod id for registry initialization
     *
     * @return [String] of mod id
     */
    val modId: String

    /**
     * This is a research registration.
     *
     * @param [reg] returns [ResearchRegistry] in order to register research
     */
    fun registerResearch(reg: ResearchRegistry) {}

    /**
     * This is a research trigger registration.
     *
     * @param [reg] returns [ResearchTriggerRegistry] in order to register research trigger
     */
    fun registerResearchTriggers(reg: ResearchTriggerRegistry) {}

    /**
     * This is a material registration
     *
     * @param [reg] returns [MaterialRegistry] in order to register material
     */
    @ApiStatus.NonExtendable
    @ApiStatus.Internal
    @ApiStatus.AvailableSince("1.0")
    fun registerMaterial(reg: MaterialRegistry) {
        throw IllegalAccessException("This function is not available in current version. Please, wait realisation!")
    }
}