package team.zeds.ancientmagic.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.zeds.ancientmagic.api.cap.*;
import team.zeds.ancientmagic.init.registries.AMCapability;

public class BlockManaCapability implements BlockMana {
    protected BlockManaCapability() {}

    int storage = 0;
    int max = 0;

    @Override
    public int getManaStorage() {
        return this.storage;
    }

    @Override
    public void setManaStorage(int storage) {
        this.storage = storage;
    }

    @Override
    public int getMaxManaStorage() {
        return max;
    }

    @Override
    public void setMaxManaStorage(int storage) {
        this.max = storage;
    }

    @Override
    public void addMana(int count) {
        this.storage = Math.min(this.storage + count, this.getMaxManaStorage());
    }

    @Override
    public void subMana(int count) {
        this.storage = Math.max(this.storage - count, 0);
    }

    @Override
    public void save(@NotNull CompoundTag tag) {
        tag.putInt("ManaStorage", this.storage);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        this.storage = tag.getInt("ManaStorage");
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private BlockManaCapability wrapper;
        private final LazyOptional<BlockManaCapability> lazy = LazyOptional.of(this::createCap);

        public BlockManaCapability createCap() {
            if (this.wrapper == null) this.wrapper = new BlockManaCapability();
            return this.wrapper;
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return AMCapability.BLOCK_MAGIC_CAPABILITY.orEmpty(cap, this.lazy);
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
