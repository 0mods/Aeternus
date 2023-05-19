package api.ancientmagic.magic;

import net.minecraft.network.chat.Component;

public interface IMagicType {
    String getName();
    int getId();
    IMagicType getTypeByName();
    IMagicType getById();

    default Component getTranslation() {
        return Component.translatable(String.format("magicType.%s", this.getName()));
    }
}
