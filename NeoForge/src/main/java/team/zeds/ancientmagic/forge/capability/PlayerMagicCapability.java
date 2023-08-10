package team.zeds.ancientmagic.forge.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.zeds.ancientmagic.common.api.cap.PlayerMagic;
import team.zeds.ancientmagic.common.api.magic.type.MagicType;
import team.zeds.ancientmagic.common.api.magic.type.MagicTypes;
import team.zeds.ancientmagic.forge.init.AMCapability;

public class PlayerMagicCapability implements PlayerMagic<PlayerMagicCapability> {
    private int level = 0;

    protected PlayerMagicCapability() {}

    @Override
    public int getMagicLevel() {
        return this.level;
    }

    @NotNull
    @Override
    public MagicType getMagicType() {
        return MagicTypes.getByNumeration(this.level);
    }

    @Override
    public void addLevel(int count) {
        this.level = Math.min(this.level + count, 4);
    }

    @Override
    public void subLevel(int count) {
        this.level = Math.max(this.level - count, 0);
    }

    @Override
    public void setLevel(int to) {
        this.level = to;
    }

    @Override
    public void copy(@NotNull PlayerMagicCapability source) {
        this.level = source.getMagicLevel();
    }

    @Nullable
    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("MagicPlayerLevel", this.level);
        return tag;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        this.level = tag.getInt("MagicPlayerLevel");
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private PlayerMagicCapability wrapper = null;
        private final LazyOptional<PlayerMagicCapability> lazy = LazyOptional.of(this::createCap);

        public PlayerMagicCapability createCap() {
            if (this.wrapper == null) this.wrapper = new PlayerMagicCapability();
            return this.wrapper;
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == AMCapability.PLAYER_MAGIC_CAPABILITY) return lazy.cast();

            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.createCap().save();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.createCap().load(nbt);
        }
    }
}
