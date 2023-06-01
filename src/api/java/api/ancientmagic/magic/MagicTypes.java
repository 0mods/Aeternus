package api.ancientmagic.magic;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public enum MagicTypes implements MagicType {
    //Main magic types
    LOW_MAGIC("low_magic", MagicClassifier.MAIN_TYPE),
    MEDIUM_MAGIC("medium_magic", MagicClassifier.MAIN_TYPE, ChatFormatting.BLUE),
    PRE_HIGH_MAGIC("pre_high", MagicClassifier.MAIN_TYPE, ChatFormatting.AQUA),
    HIGH_MAGIC("high_magic", MagicClassifier.MAIN_TYPE, ChatFormatting.GOLD),
    SUPERIOR("superior", MagicClassifier.MAIN_TYPE, ChatFormatting.RED),
    //Magic subtypes
    GENERATING("generation_magic", MagicClassifier.SUBTYPE),
    CONSUMING("consuming_magic", MagicClassifier.SUBTYPE);

    private final String name;
    private final MagicClassifier classifier;
    private final ChatFormatting style;

    MagicTypes(String stringId, MagicClassifier classifier, ChatFormatting style) {
        this.name = stringId;
        this.classifier = classifier;
        this.style = style;
    }

    MagicTypes(String stringId, MagicClassifier classifier) {
        this(stringId, classifier, ChatFormatting.WHITE);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Component getTranslation() {
        return this.getMagicTypeTooltip(String.format("type.%s", this.getName()));
    }

    @Override
    public MagicClassifier getClassifier() {
        return this.classifier;
    }

    @Override
    public ChatFormatting getStyle() {
        return this.style;
    }

    public static MagicType getTypeByName(String name) {
        return !name.isEmpty() ? MagicTypes.valueOf(name) : MagicTypes.LOW_MAGIC;
    }

    public static MagicType getById(int id) {
        return id > 0 && id <= MagicTypes.values().length ? MagicTypes.values()[id] : MagicTypes.LOW_MAGIC;
    }
}
