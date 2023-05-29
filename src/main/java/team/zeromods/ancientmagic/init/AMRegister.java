package team.zeromods.ancientmagic.init;

import api.ancientmagic.register.RegisterBase;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;
import team.zeromods.ancientmagic.item.RetraceStone;

public class AMRegister extends RegisterBase {
    public static final RegistryObject<Item> MAGIC_DUST = ITEMS.register("magic_dust", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RETRACE_CRYSTAL = ModList.get().isLoaded("waystones")
            ? ITEMS.register("teleportation_crystal", RetraceStone::new)
            : ITEMS.register("retrace_stone", RetraceStone::new);
}
