package team.zeds.ancientmagic.capability;

import org.jetbrains.annotations.NotNull;
import team.zeds.ancientmagic.api.cap.PlayerMagic;
import team.zeds.ancientmagic.api.magic.MagicType;
import team.zeds.ancientmagic.api.magic.MagicTypes;
import team.zeds.ancientmagic.api.mod.Constant;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import team.zeds.ancientmagic.init.registries.AMCapability;

public class PlayerMagicCapability implements PlayerMagic {
    private int level;

    @Override
    public int getMagicLevel() {
        return this.level;
    }

    @NotNull
    @Override
    public MagicType getMagicLevelAsMagicType() {
        return MagicTypes.getByNumeration(this.getMagicLevel());
    }

    @Override
    public void addLevel(int add) {
        this.level = Math.min(this.level + add, 4);
    }

    @Override
    public void subLevel(int sub) {
        this.level = Math.max(this.level - sub, 0);
    }

    @Override
    public void setLevel(int set) {
        this.level = set;
    }

    @Override
    public void copyFrom(@NotNull PlayerMagic source) {
        var capSource = (PlayerMagicCapability) source;
        this.level = capSource.level;
    }

    @Override
    public void save(@NotNull CompoundTag tag) {
        tag.putInt("MagicPlayerLevel", this.level);
        Constant.LOGGER.debug("{} has been saved", tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        this.level = tag.getInt("MagicPlayerLevel");
        Constant.LOGGER.debug("{} has been loaded", tag);
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private PlayerMagicCapability wrapper = null;
        private final LazyOptional<PlayerMagicCapability> lazy = LazyOptional.of(this::createCap);

        private PlayerMagicCapability createCap() {
            if (this.wrapper == null) this.wrapper = new PlayerMagicCapability();
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
            this.createCap().save(tag);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.createCap().load(nbt);
        }
    }
}
