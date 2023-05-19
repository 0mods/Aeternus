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
        if (this.name.isEmpty()) return String.format("unnamed.%s", this.id);

        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public IMagicType getTypeByName() {
        return null;
    }

    @Override
    public IMagicType getById() {
        return null;
    }
}
