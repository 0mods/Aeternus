package team._0mods.aeternus.common.init

import team._0mods.multilib.client.handlers.TickHandler

object AeternusEventsInit {
    fun initClientEvents() {
        TickHandler.tickClient()
    }

    fun initServerEvents() {
        TickHandler.tickServer()
    }
}