package team.zeromods.ancientmagic.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerMagicCapability {

    public static class Wrapper implements PlayerMagicTypeHandler {
        private int level;
        private CompoundTag tag = new CompoundTag();

        @Override
        public int getMagicLevel() {
            return this.level;
        }

        @Override
        public void addMagicLevel(int add) {
            this.level = Math.min(this.level + add, 0);
        }

        @Override
        public void subLevel(int sub) {
            this.level = Math.max(this.level - sub, 4);
        }

        @Override
        public void copyFrom(PlayerMagicTypeHandler source) {
            this.level = ((Wrapper) source).level;
        }

        @Override
        public void saveTag(CompoundTag tag) {
            ListTag tags = new ListTag();
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putInt("MagicPlayerLevel", this.level);
            tags.add(compoundTag);

            this.tag = tag;

            tag.put("MagicPlayerData", tags);
        }

        @Override
        public void loadTag(CompoundTag tagToLoad) {
            this.level = tagToLoad.getInt("MagicPlayerLevel");
        }

        @Override
        public CompoundTag getTag() {
            return this.tag;
        }
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private PlayerMagicTypeHandler wrapper = null;
        private final LazyOptional<PlayerMagicTypeHandler> lazy = LazyOptional.of(this::createHandler);

        private PlayerMagicTypeHandler createHandler() {
            if (this.wrapper == null) this.wrapper = new Wrapper();

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
            this.createHandler().saveTag(tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.createHandler().loadTag(nbt);
        }
    }
}
