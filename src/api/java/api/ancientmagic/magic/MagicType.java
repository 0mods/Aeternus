package api.ancientmagic.magic;

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

    public String getName() {
        return !this.name.isEmpty() ? this.name() : String.format("unnamed.%s", this.id);
    }

    @Override
    public int getId() {
        return this.id;
    }

    public static IMagicType getTypeByName(String name) {
        return !name.isEmpty() ? MagicType.valueOf(name) : MagicType.LOW_MAGIC;
    }

    public static IMagicType getById(int id) {
        return id > 0 && id < MagicType.values().length ? MagicType.values()[id] : MagicType.LOW_MAGIC;
    }
}
