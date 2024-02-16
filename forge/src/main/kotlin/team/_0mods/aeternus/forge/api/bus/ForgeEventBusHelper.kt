package team._0mods.aeternus.forge.api.bus

import com.google.common.collect.LinkedListMultimap
import com.google.common.collect.Multimap
import com.google.common.collect.Multimaps
import net.minecraftforge.eventbus.api.IEventBus
import team._0mods.aeternus.api.forge.EventBusHelper
import java.util.*
import java.util.function.Consumer

object ForgeEventBusHelper: EventBusHelper<IEventBus> {
    private val eventBusMap: MutableMap<String, IEventBus> = Collections.synchronizedMap(hashMapOf())
    private val onRegistered: Multimap<String, Consumer<IEventBus>> = Multimaps.synchronizedMultimap(LinkedListMultimap.create())

    override fun registerModEvent(modId: String, bus: IEventBus) {
        if (eventBusMap.putIfAbsent(modId, bus) != null)
            throw IllegalStateException("Can't register event bus for mod '$modId' because it was previously registered!")

        onRegistered.get(modId).forEach { it.accept(bus) }
    }

    override fun whenAvailable(modId: String, bus: Consumer<IEventBus>) {
        if (eventBusMap.containsKey(modId))
            bus.accept(eventBusMap[modId]!!)
        else onRegistered.put(modId, bus)
    }

    override fun getModEventBus(modId: String): Optional<IEventBus> = Optional.ofNullable(eventBusMap[modId])
}