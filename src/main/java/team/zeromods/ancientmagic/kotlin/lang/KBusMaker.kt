package team.zeromods.ancientmagic.kotlin.lang

import net.minecraftforge.eventbus.api.BusBuilder
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.IEventExceptionHandler
import net.minecraftforge.fml.event.IModBusEvent

internal sealed interface KBusMaker {
    fun make(exceptionHandler: IEventExceptionHandler): IEventBus
}

internal data object OldBus : KBusMaker {
    private val builder = BusBuilder::class.java.getDeclaredMethod("builder")
    private val setExceptionHandler = BusBuilder::class.java.getDeclaredMethod("setExceptionHandler", IEventExceptionHandler::class.java)
    private val setTrackPhases = BusBuilder::class.java.getDeclaredMethod("setTrackPhases", Boolean::class.java)
    private val markerType = BusBuilder::class.java.getDeclaredMethod("markerType", Class::class.java)
    private val build = BusBuilder::class.java.getDeclaredMethod("build")

    override fun make(exceptionHandler: IEventExceptionHandler): IEventBus {
        val builder = builder.invoke(null)
        setExceptionHandler.invoke(builder, exceptionHandler)
        setTrackPhases.invoke(builder, false)
        markerType.invoke(builder, IModBusEvent::class.java)

        return build.invoke(builder) as IEventBus
    }
}
internal data object NewBus : KBusMaker {
    override fun make(exceptionHandler: IEventExceptionHandler): IEventBus {
        return BusBuilder.builder().setExceptionHandler(exceptionHandler).setTrackPhases(false).markerType(IModBusEvent::class.java).useModLauncher().build()
    }
}