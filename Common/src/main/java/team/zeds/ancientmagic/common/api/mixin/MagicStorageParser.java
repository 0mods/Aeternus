package team.zeds.ancientmagic.common.api.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import team.zeds.ancientmagic.common.api.magic.ItemStackMagicObject;

public class MagicStorageParser extends ItemStackMagicObject {
    private final long maxMana;

    public MagicStorageParser(@NotNull CompoundTag tag, long maxMana) {
        super(tag);
        this.maxMana = maxMana;
    }

    @Override
    public long getMaxManaStorage() {
        return this.maxMana;
    }

    @Override
    public long getManaStorage(@NotNull ItemStack obj) {
        return this.maxMana;
    }
}
