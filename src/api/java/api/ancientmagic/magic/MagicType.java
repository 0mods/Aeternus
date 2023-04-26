package api.ancientmagic.magic;

import net.minecraft.network.chat.Component;

public enum MagicType {
    ATTACK("attacking_magic",0),
    GENERATING("generation_magic", 1),
    HIGH_MAGIC("high_magic", 2),
    RITUAL("ritual_magic",3),
    ADMIN(4);

    private final String id;
    private final int typeId;

    MagicType(Component id, int typeId) {
        this.id = id.getString();
        this.typeId = typeId;
    }

    MagicType(String id, int typeId) {
        this(Component.translatable("magicType." + id), typeId);
    }

    MagicType(int typeId) {
        this(Component.empty(), typeId);
    }

    public String getId() {
        if (this.id.equals("empty") || id == null) return "id_is_not_loaded_" + this.typeId;

        return this.id;
    }
}
