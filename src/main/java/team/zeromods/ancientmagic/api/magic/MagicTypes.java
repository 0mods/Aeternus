package team.zeromods.ancientmagic.api.magic;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

public enum MagicTypes implements MagicType {
    //Main magic types
    LOW_MAGIC("low_magic", 0, MagicClassifier.MAIN_TYPE),
    MEDIUM_MAGIC("medium_magic", 1, MagicClassifier.MAIN_TYPE, ChatFormatting.BLUE),
    PRE_HIGH_MAGIC("pre_high", 2, MagicClassifier.MAIN_TYPE, ChatFormatting.AQUA),
    HIGH_MAGIC("high_magic", 3, MagicClassifier.MAIN_TYPE, ChatFormatting.GOLD),
    SUPERIOR("superior", 4, MagicClassifier.MAIN_TYPE, ChatFormatting.RED),
    //Magic subtypes
    GENERATING("generating", MagicClassifier.SUBTYPE),
    CONSUMING("consuming", MagicClassifier.SUBTYPE),
    STORAGE("storage", MagicClassifier.SUBTYPE),
    ADMIN("admin", MagicClassifier.SUBTYPE);

    private final String name;
    private final int numerate;
    private final MagicClassifier classifier;
    private final ChatFormatting[] style;

    MagicTypes(String stringId, int numerate, MagicClassifier classifier, ChatFormatting... style) {
        this.name = stringId;
        this.classifier = classifier;
        this.style = style;
        this.numerate = numerate;
    }

    MagicTypes(String stringId, int numerate, MagicClassifier classifier) {
        this(stringId, numerate, classifier, ChatFormatting.WHITE);
    }

    MagicTypes(String stringId, MagicClassifier classifier) {
        this(stringId, -1, classifier, ChatFormatting.WHITE);
    }

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public int numerate() {
        return this.numerate;
    }

    @Override
    public MutableComponent getTranslation() {
        return MagicType.getMagicTypeMessage(String.format("type.%s", this.getId())).withStyle(this.getStyles());
    }

    @Override
    public MagicClassifier getClassifier() {
        return this.classifier;
    }

    @Override
    public ChatFormatting[] getStyles() {
        return this.style;
    }

    public static MagicType getById(String name) {
        return !name.isEmpty() ? MagicTypes.valueOf(name) : MagicTypes.LOW_MAGIC;
    }

    public static MagicType getByNumeration(int id) {
        return id > 0 && id <= MagicTypes.values().length ? MagicTypes.values()[id] : MagicTypes.LOW_MAGIC;
    }
}
