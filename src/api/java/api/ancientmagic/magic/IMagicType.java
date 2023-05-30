package api.ancientmagic.magic;

import api.ancientmagic.mod.Constant;
import net.minecraft.network.chat.Component;

public interface IMagicType {
    String getName();
    int getId();
    Component getTranslation();

    default Component getMagicTooltip(String message, Object... objects) {
        return Component.translatable(String.format("magic.%s.%s", Constant.Key, message), objects);
    }

    default Component getMagicTypeTooltip(String message, Object... objects) {
        return Component.translatable(String.format("magicType.%s", message), objects);
    }
}
