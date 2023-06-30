package team.zeds.ancientmagic.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import team.zeds.ancientmagic.api.item.MagicItemBuilder;
import team.zeds.ancientmagic.api.magic.MagicTypes;
import team.zeds.ancientmagic.api.mod.Constant;
import team.zeds.ancientmagic.api.item.MagicItem;
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
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import team.zeds.ancientmagic.compact.CompactInitializer;
import team.zeds.ancientmagic.item.RetraceStone;
import team.zeds.ancientmagic.item.*;
import team.zeds.ancientmagic.recipes.AltarRecipe;
import team.zeds.ancientmagic.recipes.base.AMRecipeSerializer;
import team.zeds.ancientmagic.recipes.base.AbstractAMRecipe;

import java.util.function.Supplier;

@SuppressWarnings({"SameParameterValue", "DataFlowIssue", "unused"})
public final class AMRegister {
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
    public static final DeferredRegister<CreativeModeTab> TABS = deferredCreator(Registries.CREATIVE_MODE_TAB);

    public static final RegistryObject<CreativeModeTab> TAB = regTab("tab", CreativeModeTab.builder()
            .icon(()-> new ItemStack(AMRegister.RETRACE_CRYSTAL.get()))
            .displayItems((f,o) -> ITEMS.getEntries().stream().map(RegistryObject::get)
                    .forEach(o::accept))
    );

    public static final RegistryObject<MagicItem> MAGIC_DUST = i("magic_dust",
            ()-> new MagicItem(MagicItemBuilder.get().setMagicType(MagicTypes.LOW_MAGIC)));
    public static final RegistryObject<MagicItem> RETRACE_CRYSTAL =
        boolReg("teleportation_crystal", RetraceStone::new, CompactInitializer.getWaystonesLoaded()
            && (FMLEnvironment.production && AMManage.COMMON_CONFIG.COMPACT_WAYSTONES.get()), "retrace_stone", RetraceStone::new);
    public static final RegistryObject<MagicItem> START_MANA_STORAGE = i("start_mana_storage",
            ()-> new ManaStorage(MagicItemBuilder.get(), 1000, false));
    public static final RegistryObject<MagicItem> CREATIVE_BUF_ITEM =
            boolReg("creative_buf", CreativeBufItem::new, !FMLEnvironment.production);
    public static final RegistryObject<Item> MAGIC_BOOK = i("magic_book", MagicBook::new);
    public static final RegistryObject<AMRecipeSerializer<AltarRecipe>> ALTAR_RECIPE_SERIAL = r("altar_recipe", AltarRecipe::new);

    static <T extends Item> RegistryObject<T> boolReg(String id, Supplier<T> sup, boolean boolIfReg) {
        return boolIfReg ? i(id, sup) : null;
    }

    static <T extends Item> RegistryObject<T> boolReg(String id, Supplier<T> sup, boolean boolIfReg, String idIfBool,
                                                      Supplier<T> supIfBool) {
        return boolIfReg ? i(id, sup) : i(idIfBool, supIfBool);
    }

    static <T extends Item> RegistryObject<T> i(String id, Supplier<T> sup) {
        return ITEMS.register(id, sup);
    }

    static RegistryObject<CreativeModeTab> regTab(String name, CreativeModeTab.Builder builder) {
        return TABS.register(name, builder.title(Component.translatable(String.format("itemTab.%s.%s",
                Constant.KEY, name)))::build);
    }

    static <T extends AbstractAMRecipe> RegistryObject<AMRecipeSerializer<T>> r(String id, AMRecipeSerializer.SerializerFactory<T> factory) {
        return RECIPE.register(id, ()-> new AMRecipeSerializer<>(factory));
    }

    static <Y, T extends IForgeRegistry<Y>> DeferredRegister<Y> deferredCreator(T forgeRegister) {
        return DeferredRegister.create(forgeRegister, Constant.KEY);
    }

    static <Y, T extends ResourceKey<? extends Registry<Y>>> DeferredRegister<Y> deferredCreator(T resourceKey) {
        return DeferredRegister.create(resourceKey, Constant.KEY);
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
