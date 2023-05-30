package team.zeromods.ancientmagic.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AMCommon {
    public static final ForgeConfigSpec.Builder SPEC = new ForgeConfigSpec.Builder();

    static {
        SPEC.push("Mod Compact");
        SPEC.push("Waystones");
    }

    public static final ForgeConfigSpec.BooleanValue COMPACT_WAYSTONES = SPEC.comment("Enable mod compact with mod Waystones")
            .define("enable_waystones_compact", true);
    public static final ForgeConfigSpec.IntValue CONSUME_DUST_COUNT = SPEC.comment("Count of consuming Magical Dust on teleport")
            .defineInRange("consume_dust_on_teleport", 2, 1, 64);

    static {
        SPEC.pop();
    }
}
