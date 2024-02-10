package team._0mods.aeternus.api.event.core

import team._0mods.aeternus.service.ServiceProvider

abstract class EventHandler protected constructor() {
    companion object {
        private var isInitialized = false

        fun init() {
            if (isInitialized) return
            isInitialized = true

            val platform = ServiceProvider.platform
            val handlerImpl = ServiceProvider.event.eventHandler

            if (platform.isPhysicalClient()) handlerImpl.registerClient()
            handlerImpl.registerCommon()
            if (platform.isPhysicalServer()) handlerImpl.registerServer()
        }
    }

    abstract fun registerClient()

    abstract fun registerCommon()

    abstract fun registerServer()
}