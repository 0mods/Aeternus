package team._0mods.multilib.client.handlers

import net.minecraft.client.Minecraft
import team._0mods.multilib.event.base.client.ClientTickEvent
import team._0mods.multilib.event.base.common.TickEvent
import team._0mods.multilib.service.ServiceProvider

object TickHandler {
    @JvmField var ticksNotPaused = 0
    @JvmField var clientTicks = 0
    @JvmField var serverTicks = 0

    val lazyCurrentTicks get() = lazy { currentTicks() }
    val currentTicks get() = currentTicks()
    val partialTicks get() = Minecraft.getInstance().frameTime

    fun computeTime(startTime: Int, duration: Int): Float = (currentTicks - startTime + partialTicks) / duration

    fun tickClient() {
        ClientTickEvent.CLIENT_POST.register {
            if (!Minecraft.getInstance().isPaused) ticksNotPaused++
            clientTicks++
        }
    }

    fun tickServer() {
        TickEvent.SERVER_POST.register {
            serverTicks++
        }
    }

    fun currentTicks() = if (ServiceProvider.platform.isPhysicalClient()) clientTicks else serverTicks
}