/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.init.registry

import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.material.PushReaction
import team._0mods.aeternus.api.impl.registry.SpellRegistryImpl
import team._0mods.aeternus.api.item.ITabbed
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.api.util.aRl
import team._0mods.aeternus.common.fluid.LiquidEtherium
import team._0mods.aeternus.common.helper.AeternusItem
import team._0mods.aeternus.common.item.*

@Suppress("UnstableApiUsage")
object AeternusRegsitry {
    private val items = DeferredRegister.create(ModId, Registries.ITEM)
    private val tabs = DeferredRegister.create(ModId, Registries.CREATIVE_MODE_TAB)
    private val fluids = DeferredRegister.create(ModId, Registries.FLUID)
    private val blocks = DeferredRegister.create(ModId, Registries.BLOCK)

    /* TABS */
    val aeternusTab = tabs.register("aeternus_tab") {
        CreativeTabRegistry.create {
            it.title(tab("misc"))
                .displayItems { _, o ->
                    items.registrar.entrySet().forEach { e ->
                        val i = e.value

                        if (i is ITabbed) o.accept(ItemStack(i))
                    }
                }
                .icon { ItemStack(knowledgeBook.get()) }
        }
    }

    val spellTab = tabs.register("spell") {
        CreativeTabRegistry.create {
            it.title(tab("spells"))
                .icon { ItemStack(emptyScroll.get()) }
                .displayItems { _, output ->
                    SpellRegistryImpl.scrolls.forEach { s ->
                        val spell = s.spell

                        if (!spell.isHidden) output.accept(ItemStack(s))
                    }

                    output.accept(ItemStack(emptyScroll.get()))
                }

                .icon { ItemStack(emptyScroll.get()) }
        }
    }

    /* ITEMS */
    // MISC
    val emptyScroll = items.register("empty_scroll", ::EmptyScroll)
    val knowledgeBook = items.register("knowledge_book", ::AeternusItem)
    val etheriumTar = items.register("etherium_tar", ::AeternusItem)
    val crystallizedEtherium = items.register("crystallized_etherium", ::AeternusItem)
    val originaleEtherium = items.register("orginale_etherium", ::AeternusItem)
    val drilldwill = items.register("drilldwill", ::AeternusItem)

    // BUCKETS
    val etheriumBucket = items.register("etherium_bucket") { BucketItem(Fluids.EMPTY, Item.Properties()) }

    // ARMORS
    val drilldwillHelmet = items.register("drilldwill_helmet") {
        DrilldwillArmor(ArmorItem.Type.HELMET, Item.Properties())
    }
    val drilldwillChest = items.register("drilldwill_chestplate") {
        DrilldwillArmor(ArmorItem.Type.CHESTPLATE, Item.Properties())
    }
    val drilldwillLegs = items.register("drilldwill_leggings") {
        DrilldwillArmor(ArmorItem.Type.LEGGINGS, Item.Properties())
    }
    val drilldwillBoots = items.register("drilldwill_boots") {
        DrilldwillArmor(ArmorItem.Type.BOOTS, Item.Properties())
    }

    /* DIMENSIONS */
    val iterLevelStem = ResourceKey.create(Registries.LEVEL_STEM, "iter".aRl)
    val iterLevelKey = ResourceKey.create(Registries.DIMENSION, "iter".aRl)
    val iterDimType = ResourceKey.create(Registries.DIMENSION_TYPE, "iter".aRl)

    /* FLUIDS */
    val liquidEtherium = fluids.register("etherium", LiquidEtherium::Source)
    val etheriumFlowing = fluids.register("etherium_flowing", LiquidEtherium::Flowing)

    /* BLOCKS */
    val liquidEtheriumBlock = block("etherium_fluid") {
        LiquidBlock(
            etheriumFlowing.get(),
            BlockBehaviour.Properties.of()
                .liquid()
                .replaceable()
                .noCollission()
                .strength(100F)
                .pushReaction(PushReaction.DESTROY)
                .noLootTable()
                .sound(SoundType.EMPTY)
        )
    }

    @JvmStatic
    fun init() {
        SpellRegistryImpl.onReg(items)

        items.register()
        tabs.register()
        fluids.register()
        blocks.register()
    }

    fun <T: Block> block(id: String, block: () -> T): RegistrySupplier<T> {
        val b = blocks.register(id, block)
        items.register(id) { BlockItem(b.get(), Item.Properties()) }
        return b
    }

    private fun tab(id: String): Component = Component.translatable("itemGroup.$ModId.$id")
}
