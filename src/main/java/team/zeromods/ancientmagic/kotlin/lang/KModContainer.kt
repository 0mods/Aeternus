package team.zeromods.ancientmagic.kotlin.lang

import net.minecraftforge.eventbus.EventBusErrorMessage
import net.minecraftforge.eventbus.api.BusBuilder
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.IEventListener
import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.ModLoadingException
import net.minecraftforge.fml.ModLoadingStage
import net.minecraftforge.fml.event.IModBusEvent
import net.minecraftforge.forgespi.language.IModInfo
import net.minecraftforge.forgespi.language.ModFileScanData
import team.zeromods.ancientmagic.kotlin.Logger
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier

class KModContainer(info: IModInfo, className: String, private val data:
    ModFileScanData, gamelayer: ModuleLayer) : ModContainer(info) {
    private var modInst: Any? = null
    internal val eventBus: IEventBus
    private val modClass: Class<*>

    init {
        activityMap[ModLoadingStage.CONSTRUCT] = Runnable(::construct)
        eventBus = try {
            BusBuilder::class.java.getDeclaredMethod("useModLauncher")
            NewBus.make(::onEventFailed)
        } catch (e: NoSuchMethodException) {
            OldBus.make(::onEventFailed)
        }

        configHandler = Optional.of(Consumer { event -> eventBus.post(event.self()) })

        val ctx = KModLoadingContext(this)
        contextExtension = Supplier { ctx }

        try {
            val layer = gamelayer.findModule(info.owningFile.moduleName()).orElseThrow()
            modClass = Class.forName(layer, className)
        } catch (t: Throwable) {
            throw ModLoadingException(info, ModLoadingStage.CONSTRUCT, "fml.modloading.failedtoloadmodclass", t)
        }
    }

    private fun onEventFailed(iEventBus: IEventBus, event: Event, listeners: Array<IEventListener>, busId: Int, throwable: Throwable) {
        Logger.error(EventBusErrorMessage(event, busId, listeners, throwable).toString())
    }

    private fun construct() {
        try {
            modInst = modClass.kotlin.objectInstance ?: modClass.getDeclaredConstructor().newInstance()
        } catch(throwable: Throwable) {
            throw ModLoadingException(modInfo, ModLoadingStage.CONSTRUCT, "fml.modloading.faliledtoloadmod", throwable, modClass)
        }
        try {
            KEventBusSubscriber.inject(this, data, modClass.classLoader)
        } catch (t: Throwable) {
            throw ModLoadingException(modInfo, ModLoadingStage.CONSTRUCT, "fml.modloading.failedtoloadmod", t, modClass)
        }
    }


    override fun matches(mod: Any?): Boolean {
        return mod == modInst
    }

    override fun getMod(): Any? = modInst

    override fun <T> acceptEvent(e: T) where T : Event?, T : IModBusEvent? {
        try {
            eventBus.post(e)
        } catch (throwable: Throwable) {
            throw ModLoadingException(modInfo, modLoadingStage, "fml.modloading.errorduringevent", throwable)
        }
    }
}