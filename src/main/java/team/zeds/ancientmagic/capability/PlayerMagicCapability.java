package team.zeds.ancientmagic.capability;

import team.zeds.ancientmagic.api.mod.Constant;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import team.zeds.ancientmagic.init.AMCapability;

public class PlayerMagicCapability {
    private int level;

    public int getMagicLevel() {
        return this.level;
    }

    public void addLevel(int add) {
        this.level = Math.min(this.level + add, 4);
    }

    public void subLevel(int sub) {
        this.level = Math.max(this.level - sub, 0);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void copyFrom(PlayerMagicCapability source) {
        this.level = source.level;
    }

    public void saveTag(CompoundTag tag) {
        tag.putInt("MagicPlayerLevel", this.level);
        Constant.LOGGER.debug("{} has been saved", tag);
    }

    public void loadTag(CompoundTag tagToLoad) {
        this.level = tagToLoad.getInt("MagicPlayerLevel");
        Constant.LOGGER.debug("{} has been loaded", tagToLoad);
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private PlayerMagicCapability wrapper = null;
        private final LazyOptional<PlayerMagicCapability> lazy = LazyOptional.of(this::createCap);

        private PlayerMagicCapability createCap() {
            if (this.wrapper == null) this.wrapper = new PlayerMagicCapability();
            Constant.LOGGER.debug("Capability \"PlayerMagicCapability\" has been created");
            return this.wrapper;
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            if (cap == AMCapability.PLAYER_MAGIC_HANDLER) return lazy.cast();

            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            this.createCap().saveTag(tag);
            Constant.LOGGER.debug("{} has been saved (FORGE)", tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            Constant.LOGGER.debug("{} has been loaded (FORGE)", nbt);
            this.createCap().loadTag(nbt);
        }
    }
}
