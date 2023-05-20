package team.zeromods.ancientmagic.compact.curios;

import api.ancientmagic.mod.Constant;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CurioCapability {

    public static void createCurioSlots(InterModEnqueueEvent event) {
        InterModComms.sendTo(Constant.CurioKey, SlotTypeMessage.REGISTER_TYPE, ()-> SlotTypePreset.BELT.getMessageBuilder()
                .icon(new ResourceLocation(Constant.Key, "textures/curios/crystal_renderer.png")).build());
    }
}
