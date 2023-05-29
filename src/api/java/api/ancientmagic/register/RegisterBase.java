package api.ancientmagic.register;

import api.ancientmagic.mod.Constant;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class RegisterBase {
    public static final DeferredRegister<Item> ITEMS = deferredCreator(ForgeRegistries.ITEMS);
    public static final DeferredRegister<Block> BLOCKS = deferredCreator(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<EntityType<?>> ENTITY = deferredCreator(ForgeRegistries.ENTITY_TYPES);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = deferredCreator(ForgeRegistries.BLOCK_ENTITY_TYPES);
    public static final DeferredRegister<Biome> BIOMES = deferredCreator(ForgeRegistries.BIOMES);
    public static final DeferredRegister<Fluid> FLUID = deferredCreator(ForgeRegistries.FLUIDS);
    public static final DeferredRegister<StructureType<?>> STRUCTURE = deferredCreator(Registries.STRUCTURE_TYPE);
    public static final DeferredRegister<MenuType<?>> CONTAINER = deferredCreator(ForgeRegistries.MENU_TYPES);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE = deferredCreator(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final DeferredRegister<Codec<? extends BiomeModifier>> MODIFIER_CODEC = deferredCreator(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS);

    static <Y, T extends IForgeRegistry<Y>> DeferredRegister<Y> deferredCreator(T forgeRegister) {
        return DeferredRegister.create(forgeRegister, Constant.Key);
    }

    static <Y, T extends ResourceKey<? extends Registry<Y>>> DeferredRegister<Y> deferredCreator(T resourceKey) {
        return DeferredRegister.create(resourceKey, Constant.Key);
    }

    public static void init() {
        IEventBus b = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(b);
        BLOCKS.register(b);
        ENTITY.register(b);
        BLOCK_ENTITY.register(b);
        BIOMES.register(b);
        FLUID.register(b);
        STRUCTURE.register(b);
        CONTAINER.register(b);
        RECIPE.register(b);
        MODIFIER_CODEC.register(b);
    }
}
