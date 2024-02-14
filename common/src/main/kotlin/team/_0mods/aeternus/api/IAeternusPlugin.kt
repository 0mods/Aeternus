package team._0mods.aeternus.api

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
     * This is research registration.
     *
     * @param [reg] returns [IResearchRegistry] in order to register research
     */
    fun registerResearch(reg: IResearchRegistry) {}

    /**
     * This is research trigger registration.
     *
     * @param [reg] returns [IResearchTriggerRegistry] in order to register research trigger
     */
    fun registerResearchTriggers(reg: IResearchTriggerRegistry) {}
}