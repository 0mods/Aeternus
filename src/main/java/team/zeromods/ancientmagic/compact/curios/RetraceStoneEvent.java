package team.zeromods.ancientmagic.compact.curios;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.zeromods.ancientmagic.init.AMRegister;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

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

    public static boolean getActivated() {
        return itemActivated;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
        CuriosApi.getCuriosHelper().addSlotModifier(this.getStack(), "belt_modifier", "def_buf", uuid,
                1.5D, AttributeModifier.Operation.ADDITION, SlotTypePreset.BELT.getIdentifier());
        return ICurio.super.getAttributeModifiers(slotContext, uuid);
    }
}
