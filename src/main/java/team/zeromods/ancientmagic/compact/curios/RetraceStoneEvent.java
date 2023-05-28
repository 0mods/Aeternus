package team.zeromods.ancientmagic.compact.curios;

import net.minecraft.world.item.ItemStack;
import team.zeromods.ancientmagic.init.AMRegister;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class RetraceStoneEvent implements ICurio {
    private static boolean itemActivated = false;

    @Override
    public ItemStack getStack() {
        return new ItemStack(AMRegister.RETRACE_CRYSTAL.get());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        itemActivated = true;
    }

    @Override
    public boolean canUnequip(SlotContext slotContext) {
        return itemActivated = false;
    }
}
