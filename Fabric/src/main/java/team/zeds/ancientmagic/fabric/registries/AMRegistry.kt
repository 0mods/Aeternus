package team.zeds.ancientmagic.fabric.registries

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.*
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import team.zeds.ancientmagic.common.AMConstant
import team.zeds.ancientmagic.common.AMConstant.reloc
import team.zeds.ancientmagic.common.api.block.mutli.MultiCoreBlock
import team.zeds.ancientmagic.common.api.block.mutli.MultiModuleBlock
import team.zeds.ancientmagic.common.api.block.mutli.entity.MultiCoreBlockEntity
import team.zeds.ancientmagic.common.api.block.mutli.entity.MultiModuleBlockEntity
import team.zeds.ancientmagic.common.api.recipe.AMChancedRecipeSerializer
import team.zeds.ancientmagic.common.api.recipe.AMRecipeSerializer
import team.zeds.ancientmagic.common.api.registry.IAMRegistryEntry
import team.zeds.ancientmagic.common.block.AltarBlock
import team.zeds.ancientmagic.common.block.AltarPedestalBlock
import team.zeds.ancientmagic.common.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.common.block.entity.AltarPedestalBlockEntity
import team.zeds.ancientmagic.common.recipes.AltarRecipe
import team.zeds.ancientmagic.common.recipes.ManaGenerationRecipe

object AMRegistry: IAMRegistryEntry {
    private val altarBlock = AltarBlock()
    private val altarPedestalBlock = AltarPedestalBlock()
    private val multiCoreBlock = MultiCoreBlock()
    private val multiModuleBlock = MultiModuleBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))

    private val altarBlockEntity = FabricBlockEntityTypeBuilder.create({ pos, state -> AltarBlockEntity(pos, state) },
        altarBlock
    ).build()
    private val altarPedestalBlockEntity = FabricBlockEntityTypeBuilder.create({ pos, state -> AltarPedestalBlockEntity(pos, state) },
        altarPedestalBlock
    ).build()
    private val multiCoreBlockEntity = FabricBlockEntityTypeBuilder.create({ pos, state -> MultiCoreBlockEntity(pos, state)},
        multiCoreBlock
    ).build()
    private val multiModuleBlockEntity = FabricBlockEntityTypeBuilder.create({ pos, state -> MultiModuleBlockEntity(pos, state) },
        multiModuleBlock
    ).build()

    private val altarRecipe = AMRecipeSerializer {id, ingr, stack, exp, time ->
        AltarRecipe(id, ingr, stack, exp, time)
    }
    private val manaGenerationRecipe = AMChancedRecipeSerializer { id, ingr, stack, chance, exp, time ->
        ManaGenerationRecipe(id, ingr, stack, chance, exp, time)
    }

    private val tab = FabricItemGroup.builder()
        .title(Component.translatable("itemTab.${AMConstant.KEY}.tab"))
        .icon { ItemStack(altarBlock) }
        .build()

    @JvmStatic
    fun initialize() {
        AMConstant.LOGGER.debug("AMRegistry initialized!")
        // BLOCKS
        block("altar", altarBlock)
        block("altar_pedestal", altarPedestalBlock)
        blockNI("multi_core_block", multiCoreBlock)
        blockNI("multi_module_block", multiModuleBlock)
        // BLOCK ENTITIES
        be("altar", altarBlockEntity)
        be("altar_pedestal", altarPedestalBlockEntity)
        be("multi_core_block", multiCoreBlockEntity)
        be("multi_module_block", multiModuleBlockEntity)
        // RECIPE SERIALIZERS
        recipe("altar", altarRecipe)
        recipe("mana", manaGenerationRecipe)
        // ITEM GROUPS
        tab("tab", tab)

        IAMRegistryEntry.registeredItems.forEach {
            ItemGroupEvents.modifyEntriesEvent(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).get())
                .register { e -> e.accept(it) }
        }
    }

    private fun recipe(id: String, obj: RecipeSerializer<*>) {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, reloc(id), obj)
    }

    private fun be(id: String, obj: BlockEntityType<*>) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, reloc(id), obj)
    }

    private fun itemNG(id: String, obj: Item) {
        Registry.register(BuiltInRegistries.ITEM, reloc(id), obj)
    }

    private fun item(id: String, obj: Item) {
        Registry.register(BuiltInRegistries.ITEM, reloc(id), obj)
        IAMRegistryEntry.registeredItems.add(obj)
    }

    private fun tab(id: String, obj: CreativeModeTab) {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, reloc(id), obj)
    }

    private fun block(id: String, obj: Block) {
        block(id, obj, null)
    }

    private fun blockNI(id: String, obj: Block) {
        Registry.register(BuiltInRegistries.BLOCK, reloc(id), obj)
    }

    private fun blockNG(id: String, obj: Block) {
        blockNG(id, obj, null)
    }

    private fun blockNG(id: String, obj: Block, blockItemProps: Item.Properties?) {
        val localprops = blockItemProps ?: Item.Properties()
        val block = Registry.register(BuiltInRegistries.BLOCK, reloc(id), obj)
        Registry.register(BuiltInRegistries.ITEM, reloc(id), BlockItem(block, localprops))
    }

    private fun block(id: String, obj: Block, blockItemProps: Item.Properties?) {
        val localprops = blockItemProps ?: Item.Properties()
        val block = Registry.register(BuiltInRegistries.BLOCK, reloc(id), obj)
        val item = Registry.register(BuiltInRegistries.ITEM, reloc(id), BlockItem(block, localprops))
        IAMRegistryEntry.registeredItems.add(item)
    }

    override fun getAltarBlock(): AltarBlock = altarBlock

    override fun getAltarBlockEntityType(): BlockEntityType<AltarBlockEntity> = altarBlockEntity

    override fun getAltarPedestalBlockEntityType(): BlockEntityType<AltarPedestalBlockEntity> = altarPedestalBlockEntity

    override fun getAltarPedestalBlock(): AltarPedestalBlock = altarPedestalBlock

    override fun getAltarRecipeSerializer(): AMRecipeSerializer<AltarRecipe> = altarRecipe

    override fun getManaRecipeSerializer(): AMChancedRecipeSerializer<ManaGenerationRecipe> = manaGenerationRecipe

    override fun getMultiModuleBlockEntity(): BlockEntityType<MultiModuleBlockEntity> = multiModuleBlockEntity

    override fun getMultiCoreBlockEntity(): BlockEntityType<MultiCoreBlockEntity> = multiCoreBlockEntity
}