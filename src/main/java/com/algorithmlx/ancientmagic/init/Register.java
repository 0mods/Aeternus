package com.algorithmlx.ancientmagic.init;

import com.algorithmlx.ancientmagic.AncientMagic;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = AncientMagic.ModId)
public class Register {
    public static final DeferredRegister<Item> ITEMS = deferredCreator(ForgeRegistries.ITEMS);
    public static final DeferredRegister<Block> BLOCKS = deferredCreator(ForgeRegistries.BLOCKS);

    public static final RegistryObject<Item> ITEST = ITEMS.register("itest", ()-> new Item(new Item.Properties()));

    @SubscribeEvent
    public void forgeEventRegistry(FMLCommonSetupEvent e) {
        AncientMagic.LOGGER.debug("initializing registry..");
        init();
    }

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(bus);
        BLOCKS.register(bus);
    }

    static <Y, T extends IForgeRegistry<Y>> DeferredRegister<Y> deferredCreator(T forgeRegister) {
        return DeferredRegister.create(forgeRegister, AncientMagic.ModId);
    }
}
