package team.zeds.ancientmagic.fabric.registries

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.schedule.Activity
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.material.Fluid
import team.zeds.ancientmagic.AMConstant
import team.zeds.ancientmagic.AMConstant.reloc
import team.zeds.ancientmagic.api.except.UnsupportedRegistryException
import team.zeds.ancientmagic.api.registry.IAMRegistryEntry

object AMRegistry: IAMRegistryEntry {
    private val testBlock: Block = register("test_fabric_block", Block(FabricBlockSettings.create()))
    private val testItem: Item = register("test_fabric_item", Item(FabricItemSettings()))

    @JvmStatic
    fun initialize() {
        register(
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
            .build().displayItems
        )
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

            else -> throw UnsupportedRegistryException("${obj!!::class.java} is not supported for registry!")
        }
    }
}