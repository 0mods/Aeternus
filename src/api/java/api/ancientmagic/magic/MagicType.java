package api.ancientmagic.magic;

import api.ancientmagic.mod.Constant;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface MagicType {
    String getId();

    MutableComponent getTranslation();

    MagicClassifier getClassifier();

    ChatFormatting getStyle();

    default MutableComponent getMagicTooltip(String message, Object... objects) {
        return Component.translatable(String.format("magic.%s.%s", Constant.Key, message), objects);
    }

    default MutableComponent getMagicTypeTooltip(String message, Object... objects) {
        return Component.translatable(String.format("magicType.%s", message), objects);
    }

    enum MagicClassifier {
        MAIN_TYPE, SUBTYPE
    }
}
