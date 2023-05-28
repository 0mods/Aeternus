package team.zeromods.ancientmagic.compact.curios;

import api.ancientmagic.mod.Constant;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class AMCurio {
    public static final ICapabilityProvider RetraceStoneEventProvider = veryLazyCapProvider(RetraceStoneEvent::new);

    public static void createCurioSlots(final InterModEnqueueEvent event) {
        InterModComms.sendTo(Constant.CurioKey, SlotTypeMessage.REGISTER_TYPE, ()-> SlotTypePreset.BELT.getMessageBuilder()
                .build());
    }

    private static ICapabilityProvider curioCapabilityProvider(LazyOptional<ICurio> curioLazy) {
        return new ICapabilityProvider() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curioLazy);
            }
        };
    }

    private static <T extends ICurio> LazyOptional<T> lazyGen(NonNullSupplier<T> sup) {
        return LazyOptional.of(sup);
    }

    private static ICapabilityProvider veryLazyCapProvider(NonNullSupplier<ICurio> sup) {
        return curioCapabilityProvider(lazyGen(sup));
    }
}
