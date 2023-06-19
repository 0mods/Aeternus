package team.zeromods.ancientmagic.api.item;

import team.zeromods.ancientmagic.api.atomic.AtomicUse;
import net.minecraft.world.item.ItemStack;

public interface IMagicItem {
    void use(AtomicUse<ItemStack> atomicUse);
    void useOn(AtomicUse<?> atomicUse);
}
