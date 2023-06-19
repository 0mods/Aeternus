package team.zeromods.ancientmagic.kotlin.lang

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.ModLoadingContext

class KModLoadingContext(private val container: KModContainer) {
    fun getEventBus(): IEventBus = container.eventBus

    companion object {
        fun get(): KModLoadingContext = ModLoadingContext.get().extension()
    }
}