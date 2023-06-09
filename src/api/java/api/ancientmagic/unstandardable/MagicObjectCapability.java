package api.ancientmagic.unstandardable;

import api.ancientmagic.magic.MagicState;
import api.ancientmagic.magic.MagicType;
import api.ancientmagic.magic.MagicTypes;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        public static final Capability<MagicObjectCapability> MAGIC_OBJECT =
                CapabilityManager.get(new CapabilityToken<>() {});
        private MagicState wrapper = null;
        private final LazyOptional<MagicState> lazy = LazyOptional.of(this::createCap);

        private MagicState createCap() {
            if (this.wrapper == null) this.wrapper = new MagicObjectCapability();
            return this.wrapper;
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap,
                                                          @Nullable Direction side) {
            if (cap == MAGIC_OBJECT) return lazy.cast();

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
