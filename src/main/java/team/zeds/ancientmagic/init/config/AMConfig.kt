package team.zeds.ancientmagic.init.config

import net.minecraftforge.common.ForgeConfigSpec
import team.zeds.ancientmagic.init.registries.AMCommands

@Suppress("PropertyName")
class AMCommon {
    @JvmField val SPEC: ForgeConfigSpec.Builder = ForgeConfigSpec.Builder()

    init {
        SPEC.push("Mod Compact")
        SPEC.push("Waystones")
    }

    var compactWaystones: ForgeConfigSpec.BooleanValue = SPEC.comment("Enable mod compact with mod Waystones")
        .define("enable_waystones_compact", true)
    var consumeDustTeleportUse: ForgeConfigSpec.IntValue = SPEC.comment("Count of consuming Magical Dust on teleport")
        .defineInRange("consume_dust_on_teleport", 2, 1, 64)

    init {
        SPEC.pop(2)
        SPEC.push("ModData")
    }

    var commandsIdentifier: ForgeConfigSpec.ConfigValue<MutableList<String>> = SPEC.comment("Valid identifiers for commands")
        .define("command_value", AMCommands.NAMES_OF_COMMAND)

    init {
        SPEC.pop()
    }

    companion object {
        @get:JvmStatic
        var instance: AMCommon? = null
            get() {
                if (field == null) field = AMCommon()
                return field!!
            }
            private set
    }
}