package api.ancientmagic.magic;

import api.ancientmagic.mod.Constant;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public interface MagicType {
    String getName();

    Component getTranslation();

    MagicClassifier getClassifier();

    ChatFormatting getStyle();

    default Component getMagicTooltip(String message, Object... objects) {
        return Component.translatable(String.format("magic.%s.%s", Constant.Key, message), objects);
    }

    default Component getMagicTypeTooltip(String message, Object... objects) {
        return Component.translatable(String.format("magicType.%s", message), objects);
    }

    enum MagicClassifier {
        MAIN_TYPE, SUBTYPE
    }
}
