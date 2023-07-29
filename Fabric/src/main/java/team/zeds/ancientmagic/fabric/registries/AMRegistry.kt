package team.zeds.ancientmagic.fabric.registries

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.*
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.material.Fluid
import team.zeds.ancientmagic.common.AMConstant
import team.zeds.ancientmagic.common.AMConstant.reloc
import team.zeds.ancientmagic.common.api.except.UnsupportedRegistryException
import team.zeds.ancientmagic.common.api.recipe.AMChancedRecipeSerializer
import team.zeds.ancientmagic.common.api.recipe.AMRecipeSerializer
import team.zeds.ancientmagic.common.api.registry.IAMRegistryEntry
import team.zeds.ancientmagic.common.block.AltarBlock
import team.zeds.ancientmagic.common.block.AltarPedestalBlock
import team.zeds.ancientmagic.common.block.entity.AltarBlockEntity
import team.zeds.ancientmagic.common.recipes.AltarRecipe
import team.zeds.ancientmagic.common.recipes.ManaGenerationRecipe

object AMRegistry: IAMRegistryEntry {
    private val altarBlock = register("altar", AltarBlock())
    private val altarPedestalBlock = register("altar_pedestal", AltarPedestalBlock())
    private val altarRecipe = register("altar", AMRecipeSerializer {id, ingr, stack, exp, time ->
        AltarRecipe(id, ingr, stack, exp, time)
    })
    private val manaGenerationRecipe = register("mana", AMChancedRecipeSerializer { id, ingr, stack, chance, exp, time ->
        ManaGenerationRecipe(id, ingr, stack, chance, exp, time)
    })
    private val altarBlockEntity = register("altar", FabricBlockEntityTypeBuilder.create({ pos, state ->
        AltarBlockEntity(pos, state)
    }, altarBlock).build())
    private val altarPedestalBlockEntity = register("altar_pedestal", FabricBlockEntityTypeBuilder.create({ pos, state ->
        AltarBlockEntity(pos, state)
    }, altarPedestalBlock).build())
    private val tab = register(
        "tab",
        FabricItemGroup.builder()
            .title(Component.translatable("itemTab.${AMConstant.KEY}.tab"))
            .icon { ItemStack(Items.AIR) }
            .displayItems { _, b ->
                IAMRegistryEntry.registeredItems.forEach { item ->
                    IAMRegistryEntry.noTabItems.forEach {
                        if (item != it) b.accept(item)
                    }
                }
            }
            .build()
    )

    @JvmStatic
    fun initialize() {
        AMConstant.LOGGER.debug("AMRegistry initialized!")
    }

    private fun <T> register(id: String, obj: T): T = register(id, obj, null)

    private fun <T> register(id: String, obj: T, blockItemProps: Item.Properties?): T {
        when (obj) {
            is Block -> {
                var block = obj
                block = Registry.register(BuiltInRegistries.BLOCK, reloc(id), block)
                val props = blockItemProps ?: Item.Properties()
                val item = Registry.register(BuiltInRegistries.ITEM, reloc(id), BlockItem(block, props))
                IAMRegistryEntry.registeredBlocks.add(block)
                IAMRegistryEntry.registeredItems.add(item)
                return block
            }

            is Item -> {
                var item = obj
                item = Registry.register(BuiltInRegistries.ITEM, reloc(id), item)
                IAMRegistryEntry.registeredItems.add(item)
                return item
            }

            is SoundEvent -> {
                var sound = obj
                sound = Registry.register(BuiltInRegistries.SOUND_EVENT, reloc(id), sound)
                return sound
            }

            is Fluid -> {
                var fluid = obj
                fluid = Registry.register(BuiltInRegistries.FLUID, reloc(id), fluid)
                return fluid
            }

            is MobEffect -> {
                var effect = obj
                effect = Registry.register(BuiltInRegistries.MOB_EFFECT, reloc(id), effect)
                return effect
            }

            is Enchantment -> {
                var enchant = obj
                enchant = Registry.register(BuiltInRegistries.ENCHANTMENT, reloc(id), enchant)
                return enchant
            }

            is CreativeModeTab -> {
                var tab = obj
                tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, reloc(id), tab)
                return tab
            }

            is EntityType<*> -> {
                var type = obj
                type = Registry.register(BuiltInRegistries.ENTITY_TYPE, id, type)
                return type
            }

            is Potion -> {
                var potion = obj
                potion = Registry.register(BuiltInRegistries.POTION, id, potion)
                return potion
            }

            is ParticleType<*> -> {
                var particle = obj
                particle = Registry.register(BuiltInRegistries.PARTICLE_TYPE, id, particle)
                return particle
            }

            is BlockEntityType<*> -> {
                var blockEntity = obj
                blockEntity = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, blockEntity)
                return blockEntity
            }

            is RecipeSerializer<*> -> {
                var serializer = obj
                serializer = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer)
                return serializer
            }

            else -> throw UnsupportedRegistryException("${obj!!::class.java} is not supported for registry!")
        }
    }

    override fun getAltarBlockEntityType(): BlockEntityType<AltarBlockEntity> = altarBlockEntity

    override fun getAltarPedestalBlockEntityType(): BlockEntityType<AltarBlockEntity> = altarPedestalBlockEntity

    override fun getAltarPedestalBlock(): AltarPedestalBlock = altarPedestalBlock

    override fun getAltarRecipeSerializer(): AMRecipeSerializer<AltarRecipe> = altarRecipe

    override fun getManaRecipeSerializer(): AMChancedRecipeSerializer<ManaGenerationRecipe> = manaGenerationRecipe
}