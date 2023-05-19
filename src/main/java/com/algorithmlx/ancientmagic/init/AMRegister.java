package com.algorithmlx.ancientmagic.init;

import api.ancientmagic.mod.Constant;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class AMRegister {
    public static final DeferredRegister<Item> ITEMS = deferredCreator(ForgeRegistries.ITEMS);
    public static final DeferredRegister<Block> BLOCKS = deferredCreator(ForgeRegistries.BLOCKS);

    public static final RegistryObject<Item> MAGIC_DUST = ITEMS.register("magic_dust", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LARGE_BRANCH_CRYSTAL = ModList.get().isLoaded("waystones")
            ? ITEMS.register("teleportation_crystal", ()-> new Item(new Item.Properties().fireResistant()))
            : ITEMS.register("undying_crystal", ()-> new Item(new Item.Properties().fireResistant()));
    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(bus);
        BLOCKS.register(bus);
    }

    static <Y, T extends IForgeRegistry<Y>> DeferredRegister<Y> deferredCreator(T forgeRegister) {
        return DeferredRegister.create(forgeRegister, Constant.Key);
    }
}
