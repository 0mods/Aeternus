package team.zeromods.ancientmagic.kotlin.lang

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type
import java.util.*

object KEventBusSubscriber {
    private val EVENT_BUS_SUBSCRIBER: Type = Type.getType(Mod.EventBusSubscriber::class.java)

    private val DIST_HOLDERS = listOf(
        ModAnnotation.EnumHolder(null, "CLIENT"),
        ModAnnotation.EnumHolder(null, "DEDICATED_SERVER")
    )

    @Suppress("UNCHECKED_CAST")
    fun inject(mod: KModContainer, scanData: ModFileScanData, classLoader: ClassLoader) {
        val data = scanData.annotations.filter { annotationData ->
            EVENT_BUS_SUBSCRIBER == annotationData.annotationType
        }

        for (annotationData in data) {
            val sideValue = annotationData.annotationData.getOrDefault("value", DIST_HOLDERS)
                    as List<ModAnnotation.EnumHolder>
            val sides = EnumSet.noneOf(Dist::class.java).plus(sideValue.map { eh -> Dist.valueOf(eh.value) })
            val modid = annotationData.annotationData.getOrDefault("modid", mod.modId)
            val busTargetHolder = annotationData.annotationData.getOrDefault("bus",
                ModAnnotation.EnumHolder(null, "FORGE")) as ModAnnotation.EnumHolder
            val busTarget = Mod.EventBusSubscriber.Bus.valueOf(busTargetHolder.value)

            if (mod.modId == modid && FMLEnvironment.dist in sides) {
                val kClass = Class.forName(annotationData.clazz.className, true, classLoader).kotlin

                var ktObject: Any?

                try {
                    ktObject = kClass.objectInstance
                } catch (unsupported: UnsupportedOperationException) {
                    if (unsupported.message?.contains("file facades") == false) {
                        throw unsupported
                    } else {
                        registerTo(kClass.java, busTarget, mod)
                        continue
                    }
                }

                if (ktObject != null) {
                    try {
                        registerTo(ktObject, busTarget, mod)
                    } catch (e: Throwable) {
                        throw RuntimeException(e)
                    }
                }
            }
        }
    }

    private fun registerTo(any: Any, target: Bus, mod: KModContainer) {
        if (target == Bus.FORGE)
            target.bus().get().register(any)
        else mod.eventBus.register(any)
    }
}