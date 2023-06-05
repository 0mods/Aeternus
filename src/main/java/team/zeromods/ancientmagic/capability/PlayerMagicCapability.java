package team.zeromods.ancientmagic.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.zeromods.ancientmagic.init.AMCapability;

public class PlayerMagicCapability {
    private int level;

    public int getMagicLevel() {
        return this.level;
    }

    public void addMagicLevel(int add) {
        this.level = Math.min(this.level + add, 4);
    }

    public void subLevel(int sub) {
        this.level = Math.max(this.level - sub, 0);
    }

    public void copyFrom(PlayerMagicCapability source) {
        this.level = source.level;
    }

    public void saveTag(CompoundTag tag) {
        tag.putInt("MagicPlayerLevel", this.level);
    }

    public void loadTag(CompoundTag tagToLoad) {
        this.level = tagToLoad.getInt("MagicPlayerLevel");
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private PlayerMagicCapability wrapper = null;
        private final LazyOptional<PlayerMagicCapability> lazy = LazyOptional.of(this::createWrapper);

        private PlayerMagicCapability createWrapper() {
            if (this.wrapper == null) this.wrapper = new PlayerMagicCapability();

            return this.wrapper;
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == AMCapability.PLAYER_MAGIC_HANDLER) return lazy.cast();

            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            this.createWrapper().saveTag(tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.createWrapper().loadTag(nbt);
        }
    }
}
