package team.zeds.ancientmagic.compact.curios.events;

import net.minecraft.world.item.ItemStack;
import team.zeds.ancientmagic.init.registries.AMRegister;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class RetraceStoneEvent implements ICurio {
    public static boolean isEquip = false;
    @Override
    public ItemStack getStack() {
        return new ItemStack(AMRegister.RETRACE_CRYSTAL.get());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        isEquip = true;
        return !slotContext.entity().isShiftKeyDown();
    }

    @Override
    public boolean canEquip(SlotContext slotContext) {
        isEquip = true;
        return false;
    }

    @Override
    public boolean canUnequip(SlotContext slotContext) {
        isEquip = false;
        return false;
    }

    @Override
    public void curioTick(SlotContext slotContext) {}
}
