package team.zeds.ancientmagic.init.config

import org.jetbrains.annotations.NotNull
import team.zeds.ancientmagic.AMConstant

class AMConfig {
    interface Common {}
    interface Client {}
    interface Server {}

    companion object {
        @JvmStatic var common: Common? = null
            set(@NotNull value) {
                if (field != null) AMConstant.LOGGER.warn(
                    "Config was replaced! Old {} New {}",
                    field!!::class.java,
                    value!!::class.java
                )
                field = value
            }
        @JvmStatic var client: Client? = null
            set(@NotNull value) {
                if (field != null) AMConstant.LOGGER.warn(
                    "Config was replaced! Old {} New {}",
                    field!!::class.java,
                    value!!::class.java
                )
                field = value
            }
        @JvmStatic var server: Server? = null
            set(@NotNull value) {
                if (field != null) AMConstant.LOGGER.warn(
                    "Config was replaced! Old {} New {}",
                    field!!::class.java,
                    value!!::class.java
                )
                field = value
            }
    }
}