package team._0mods.aeternus.init.config

import team._0mods.aeternus.LOGGER

class AeternusConfig {
    interface Client
    interface Common

    var clientConfig: Client? = null
        set(value) {
            if (field != null) {
                LOGGER.warn("Client config was replaced! Old: {}, New: {}", field!!.javaClass.name, value?.javaClass?.name)
            }

            field = value
        }

    var commonConfig: Common? = null
        set(value) {
            if (field != null) {
                LOGGER.warn("Client config was replaced! Old: {}, New: {}", field!!.javaClass.name, value?.javaClass?.name)
            }

            field = value
        }
}