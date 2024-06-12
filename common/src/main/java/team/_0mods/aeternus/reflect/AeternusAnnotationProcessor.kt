package team._0mods.aeternus.reflect

import org.reflections.Reflections
import team._0mods.aeternus.api.impl.registry.*
import team._0mods.aeternus.api.plugin.AeternusPlugin
import team._0mods.aeternus.api.plugin.AeternusPluginInit
import team._0mods.aeternus.common.LOGGER

object AeternusAnnotationProcessor {
    private val reflect = Reflections("") // Unsecure and too slowly

    fun start() {
        registerProcessor<AeternusPlugin, AeternusPluginInit>(AeternusPluginInit::class.java) { pluginClass, annotation ->
            val plugin = pluginClass.getDeclaredConstructor().newInstance()
            val modId = annotation.modId
            plugin.registerResearch(ResearchRegistryImpl(modId))
            plugin.registerResearchTriggers(ResearchTriggerRegistryImpl(modId))
            plugin.registerSpells(SpellRegistryImpl(modId))
        }
    }

    private inline fun <reified T, A: Annotation> registerProcessor(annotation: Class<A>, invoke: (Class<T>, A) -> Unit) {
        val classesWithAnnotation = reflect.getTypesAnnotatedWith(annotation)
        classesWithAnnotation.forEach {
            try {
                val inst = it.getDeclaredConstructor().newInstance()
                if (inst is T) {
                    val a = it.getAnnotation(annotation)
                    invoke(it as Class<T>, a)
                }
            } catch (e: KotlinReflectionNotSupportedError) {
                LOGGER.error("Failed to load processor for ${it.name}", e)
            }
        }
    }
}
