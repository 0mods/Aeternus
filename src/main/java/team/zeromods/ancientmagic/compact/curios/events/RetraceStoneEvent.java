package team.zeromods.ancientmagic.compact.curios.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.zeromods.ancientmagic.init.AMRegister;
import team.zeromods.ancientmagic.item.RetraceStone;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class RetraceStoneEvent implements ICurio {
    @Override
    public ItemStack getStack() {
        return new ItemStack(AMRegister.RETRACE_CRYSTAL.get());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        var player = (Player) slotContext.entity();
        return !player.isShiftKeyDown();
    }

    @Override
    public boolean canEquip(SlotContext slotContext) {
        RetraceStone.setActive(this.getStack(), true);
        return ICurio.super.canEquip(slotContext);
    }

    @Override
    public void curioTick(SlotContext slotContext) {

    }

    @Override
    public boolean canUnequip(SlotContext slotContext) {
        RetraceStone.setActive(this.getStack(), false);
        return ICurio.super.canUnequip(slotContext);
    }

    public static boolean getActivated() {
        return true;
    }
}
