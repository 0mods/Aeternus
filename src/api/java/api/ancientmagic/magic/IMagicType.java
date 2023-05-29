package api.ancientmagic.magic;

import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;

import java.awt.*;

public interface IMagicType {
    String getName();
    int getId();
    Component getTranslation();

    static Component magicTooltip(String message) {
        return Component.translatable(String.format("magic.%s.%s", Constant.Key, message));
    }

    static Component magicTooltip(String message, Object... objects) {
        return Component.translatable(String.format("magic.%s.%s", Constant.Key, message), objects);
    }
}
