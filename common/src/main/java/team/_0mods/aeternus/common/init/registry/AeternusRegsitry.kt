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

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.material.PushReaction
import ru.hollowhorizon.hc.common.registry.HollowRegistry
import team._0mods.aeternus.api.block.UnprivatedLiquidBlock
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.api.util.aRl
import team._0mods.aeternus.common.fluid.LiquidEtherium
import team._0mods.aeternus.common.helper.AeternusItem
import team._0mods.aeternus.common.item.*

@Suppress("UnstableApiUsage")
object AeternusRegsitry: HollowRegistry() {
    /* TABS */
//    val aeternusTab = tabs.register("aeternus_tab") {
//        CreativeTabRegistry.create {
//            it.title(tab("misc"))
//                .displayItems { _, o ->
//                    items.registrar.entrySet().forEach { e ->
//                        val i = e.value
//
//                        if (i is ITabbed) o.accept(ItemStack(i))
//                    }
//                }
//                .icon { ItemStack(knowledgeBook.get()) }
//        }
//    }
//
//    val spellTab = tabs.register("spell") {
//        CreativeTabRegistry.create {
//            it.title(tab("spells"))
//                .icon { ItemStack(emptyScroll.get()) }
//                .displayItems { _, output ->
//                    SpellRegistryImpl.scrolls.forEach { s ->
//                        val spell = s.spell
//
//                        if (!spell.isHidden) output.accept(ItemStack(s))
//                    }
//
//                    output.accept(ItemStack(emptyScroll.get()))
//                }
//
//                .icon { ItemStack(emptyScroll.get()) }
//        }
//    }

    /* ITEMS */
    // MISC
    val emptyScroll by register("empty_scroll".aRl, true) { EmptyScroll() }
    val knowledgeBook by register("knowledge_book".aRl, true) { KnowledgeBook() }
    val etheriumTar by register("etherium_tar".aRl, true) { AeternusItem() }
    val crystallizedEtherium by register("crystallized_etherium".aRl, true) { AeternusItem() }
    val originaleEtherium by register("orginale_etherium".aRl, true) { AeternusItem() }
    val drilldwill by register("drilldwill".aRl, true) { AeternusItem() }

    // BUCKETS
    val etheriumBucket by register("etherium_bucket".aRl, true) { BucketItem(Fluids.EMPTY, Item.Properties()) }

    // ARMORS
    val drilldwillHelmet by register("drilldwill_helmet".aRl) {
        DrilldwillArmor(ArmorItem.Type.HELMET, Item.Properties())
    }
    val drilldwillChest by register("drilldwill_chestplate".aRl) {
        DrilldwillArmor(ArmorItem.Type.CHESTPLATE, Item.Properties())
    }
    val drilldwillLegs by register("drilldwill_leggings".aRl) {
        DrilldwillArmor(ArmorItem.Type.LEGGINGS, Item.Properties())
    }
    val drilldwillBoots by register("drilldwill_boots".aRl) {
        DrilldwillArmor(ArmorItem.Type.BOOTS, Item.Properties())
    }

    /* DIMENSIONS */
    val iterLevelStem = ResourceKey.create(Registries.LEVEL_STEM, "iter".aRl)
    val iterLevelKey = ResourceKey.create(Registries.DIMENSION, "iter".aRl)
    val iterDimType = ResourceKey.create(Registries.DIMENSION_TYPE, "iter".aRl)

    /* FLUIDS */
    val liquidEtherium by register("etherium".aRl) { LiquidEtherium.Source() }
    val etheriumFlowing by register("etherium_flowing".aRl) { LiquidEtherium.Flowing() }

    /* BLOCKS */
    val liquidEtheriumBlock by register("etherium_fluid".aRl) {
        UnprivatedLiquidBlock(
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
    }

    private fun tab(id: String): Component = Component.translatable("itemGroup.$ModId.$id")
}
