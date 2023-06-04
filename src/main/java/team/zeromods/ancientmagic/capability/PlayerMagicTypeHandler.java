package team.zeromods.ancientmagic.capability;

import api.ancientmagic.magic.MagicType;
import net.minecraft.nbt.CompoundTag;

public interface PlayerMagicTypeHandler {
    int getMagicLevel();

    void addMagicLevel(int add);

    void subLevel(int sub);

    void copyFrom(PlayerMagicTypeHandler source);

    void saveTag(CompoundTag tag);

    void loadTag(CompoundTag tagToLoad);
}
