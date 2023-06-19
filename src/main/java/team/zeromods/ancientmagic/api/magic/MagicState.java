package team.zeromods.ancientmagic.api.magic;

import net.minecraft.nbt.CompoundTag;

public interface MagicState {
    MagicType getMagicType();

    MagicType getMagicSubtype();

    void setMaxMana(int maxMana);

    void setMagicType(MagicType type);

    void setMagicSubtype(MagicType subtype);

    int getMaxMana();

    int getStorageMana();

    void addMana(int countOfAddition);

    void subMana(int countOfSub);

    void save(CompoundTag saveTag);

    void load(CompoundTag toLoad);
}
