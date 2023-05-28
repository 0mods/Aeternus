package api.ancientmagic.magic;

import net.minecraft.network.chat.Component;

public interface IMagicType {
    String getName();
    int getId();
    Component getTranslation();
}
