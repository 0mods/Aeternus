package team.zeds.ancientmagic.fabric.util

import net.minecraft.nbt.CompoundTag

interface EntityDataHolder {
    fun getPersistentData(): CompoundTag?
}