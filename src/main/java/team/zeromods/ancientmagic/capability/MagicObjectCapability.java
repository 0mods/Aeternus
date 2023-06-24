package team.zeromods.ancientmagic.capability;

import team.zeromods.ancientmagic.api.magic.MagicState;
import team.zeromods.ancientmagic.api.magic.MagicType;
import team.zeromods.ancientmagic.api.magic.MagicTypes;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import team.zeromods.ancientmagic.init.AMCapability;

public class MagicObjectCapability implements MagicState {
    private MagicType type;
    private MagicType subtype;
    private int maxMana;
    private int manaCount = 0;

    @Override
    public MagicType getMagicType() {
        return this.type;
    }

    @Override
    public MagicType getMagicSubtype() {
        return this.subtype;
    }

    @Override
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public void setMagicType(MagicType type) {
        if (type.getClassifier() == MagicType.MagicClassifier.MAIN_TYPE)
            this.type = type;
        else this.type = MagicTypes.LOW_MAGIC;
    }

    @Override
    public void setMagicSubtype(MagicType subtype) {
        this.subtype = subtype;
    }

    @Override
    public int getMaxMana() {
        return this.maxMana;
    }

    @Override
    public int getStorageMana() {
        return this.manaCount;
    }

    @Override
    public void addMana(int countOfAddition) {
        this.manaCount = Math.min(this.manaCount + countOfAddition, this.getMaxMana());
    }

    @Override
    public void subMana(int countOfSub) {
        this.manaCount = Math.max(this.manaCount - countOfSub, 0);
    }

    @Override
    public void save(CompoundTag saveTag) {
        ListTag tags = new ListTag();
        CompoundTag tagToList = new CompoundTag();

        tagToList.putInt("MagicLevel", this.getMagicType().numerate());
        tagToList.putInt("MaxManaCount", this.getMaxMana());
        tagToList.putInt("CurrentStorageMana", this.getStorageMana());

        tags.add(tagToList);

        saveTag.put("AMMagicDataOfObject", tags);
    }

    @Override
    public void load(CompoundTag toLoad) {
        this.type = MagicTypes.getByNumeration(toLoad.getInt("MagicLevel"));
        this.maxMana = toLoad.getInt("MaxManaCount");
        this.manaCount = toLoad.getInt("CurrentStorageMana");
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private MagicState wrapper = null;
        private final LazyOptional<MagicState> lazy = LazyOptional.of(this::createCap);

        private MagicState createCap() {
            if (this.wrapper == null) this.wrapper = new MagicObjectCapability();
            return this.wrapper;
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap,
                                                          Direction side) {
            if (cap == AMCapability.MAGIC_OBJECT) return lazy.cast();

            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            this.createCap().save(tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.createCap().load(nbt);
        }
    }
}
