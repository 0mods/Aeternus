package api.ancientmagic.magic;

import net.minecraft.network.chat.Component;

public enum MagicType implements IMagicType {
    LOW_MAGIC("low_magic",0),
    MEDIUM_MAGIC("medium_magic", 1),
    PRE_HIGH_MAGIC("pre_high", 2),
    HIGH_MAGIC("high_magic", 3),
    GENERATING("generation_magic", 4),
    ADMIN(4);

    private final String name;
    private final int id;

    MagicType(String stringId, int id) {
        this.name = stringId;
        this.id = id;
    }

    MagicType(int id) {
        this(null, id);
    }

    @Override
    public String getName() {
        return !this.name.isEmpty() ? this.name : String.format("unnamed.%s", this.getId());
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Component getTranslation() {
        return !this.getName().isEmpty() ? Component.translatable(String.format("magicType.%s", this.getName())) :
                Component.translatable(String.format("magicType.%s", this.getId()));
    }

    public static IMagicType getTypeByName(String name) {
        return !name.isEmpty() ? MagicType.valueOf(name) : MagicType.LOW_MAGIC;
    }

    public static IMagicType getById(int id) {
        return id > 0 && id <= MagicType.values().length ? MagicType.values()[id] : MagicType.LOW_MAGIC;
    }
}
