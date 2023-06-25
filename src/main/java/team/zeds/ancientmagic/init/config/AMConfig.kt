package team.zeds.ancientmagic.init.config

import net.minecraftforge.common.ForgeConfigSpec

class AMCommon {
    @JvmField val SPEC: ForgeConfigSpec.Builder = ForgeConfigSpec.Builder()

    @JvmField var COMPACT_WAYSTONES: ForgeConfigSpec.BooleanValue? = null
    @JvmField var CONSUME_DUST_COUNT_ON_TELEPORT: ForgeConfigSpec.IntValue? = null
    @JvmField var VALID_COMMAND_NAMES: ForgeConfigSpec.ConfigValue<Array<out String>>? = null

    fun init() {
        SPEC.push("Mod Compact")
        SPEC.push("Waystones")
        COMPACT_WAYSTONES = SPEC.comment("Enable mod compact with mod Waystones")
            .define("enable_waystones_compact", true)
        CONSUME_DUST_COUNT_ON_TELEPORT = SPEC.comment("Count of consuming Magical Dust on teleport")
            .defineInRange("consume_dust_on_teleport", 2, 1, 64)
        SPEC.pop(2)
        SPEC.push("ModData")
        VALID_COMMAND_NAMES
        SPEC.pop()
    }

    init {
        init()
    }
}