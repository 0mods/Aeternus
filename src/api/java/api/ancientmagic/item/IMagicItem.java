package api.ancientmagic.item;

import api.ancientmagic.atomic.AtomicUse;
import net.minecraft.world.item.ItemStack;

public interface IMagicItem {
    void use(AtomicUse<ItemStack> atomicUse);
    void useOn(AtomicUse<?> atomicUse);
}
