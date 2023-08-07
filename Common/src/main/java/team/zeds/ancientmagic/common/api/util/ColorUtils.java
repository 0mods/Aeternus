package team.zeds.ancientmagic.common.api.util;

import net.minecraft.util.Mth;

public final class ColorUtils {
    public static int calcColorWithMana(long mana, long maxMana) {
        return calcColorWithMana(mana, maxMana, 1f, 1f);
    }

    public static int calcColorWithMana(long mana, long maxMana, float saturation, float value) {
        return Mth.hsvToRgb(Math.max(0.0F, (float) mana / maxMana) / 3.0F, saturation, value);
    }

    public static int calcStepWithMana(long mana, long maxMana) {
        return 13 - Math.round(13F - mana * 13F / maxMana);
    }
}
