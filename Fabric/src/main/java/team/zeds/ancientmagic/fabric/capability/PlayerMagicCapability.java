package team.zeds.ancientmagic.fabric.capability;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import team.zeds.ancientmagic.common.api.cap.PlayerMagic;
import team.zeds.ancientmagic.common.api.magic.MagicType;
import team.zeds.ancientmagic.common.api.magic.MagicTypes;
import team.zeds.ancientmagic.fabric.util.EntityDataHolder;

public class PlayerMagicCapability implements PlayerMagic {
    protected int level;

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
    public <T extends PlayerMagic> void copy(@NotNull T source) {
        this.level = source.getMagicLevel();
    }

    @Override
    public CompoundTag save(@NotNull CompoundTag tag) {
        tag.putInt("AncientMagicTag.level", this.level);
        return tag;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        this.level = tag.getInt("AncientMagicTag.level");
    }

    public void save(EntityDataHolder holder) {
        this.save(holder.getPersistentData());
    }

    public void load(EntityDataHolder holder) {
        this.load(holder.getPersistentData());
    }
}
