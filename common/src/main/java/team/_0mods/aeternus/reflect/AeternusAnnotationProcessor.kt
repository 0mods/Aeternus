package team._0mods.aeternus.reflect

import io.github.classgraph.ClassGraph
import team._0mods.aeternus.api.impl.registry.*
import team._0mods.aeternus.api.plugin.AeternusPlugin
import team._0mods.aeternus.api.plugin.AeternusPluginInit
import team._0mods.aeternus.common.LOGGER

object AeternusAnnotationProcessor {
    private val graph = ClassGraph().enableAllInfo().scan()

    fun start() {
        registerProcessor<AeternusPlugin, AeternusPluginInit>(AeternusPluginInit::class.java) { pluginClass, annotation ->
            val plugin = pluginClass.getDeclaredConstructor().newInstance()
            val modId = annotation.modId
            plugin.registerResearch(ResearchRegistryImpl(modId))
            plugin.registerResearchTriggers(ResearchTriggerRegistryImpl(modId))
            plugin.registerSpells(SpellRegistryImpl(modId))
        }
    }

    private inline fun <reified T, A: Annotation> registerProcessor(annotation: Class<A>, crossinline invoke: (Class<T>, A) -> Unit) {
        preRegisterProcessor(annotation) {
            try {
                if (annotation.isAssignableFrom(it)) {
                    val a = it.getAnnotation(annotation)
                    invoke(it as Class<T>, a)
                }
            } catch (e: KotlinReflectionNotSupportedError) {
                LOGGER.error("Failed to load processor for ${it.name}", e)
            }
        }
    }

    private fun <A: Annotation> preRegisterProcessor(annotation: Class<A>, invoke: (Class<*>) -> Unit) {
        graph.getClassesWithAnnotation(annotation).map {
            Class.forName(it.name)
        }.toSet().forEach(invoke)
    }
}
