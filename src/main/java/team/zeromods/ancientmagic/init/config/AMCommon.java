package team.zeromods.ancientmagic.init.config;

import net.minecraftforge.common.ForgeConfigSpec;
import team.zeromods.ancientmagic.init.AMCommands;

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
        SPEC.pop(2);
        SPEC.push("ModData");
    }

    public static final ForgeConfigSpec.ConfigValue<String[]> VALID_COMMAND_NAMES = SPEC.comment("Valid identifiers for commands")
            .define("val_commands", AMCommands.NAMES_OF_COMMAND);
}
