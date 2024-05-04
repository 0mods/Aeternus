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
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.material.PushReaction
import team._0mods.aeternus.api.registry.delegate.DelegatedRegistry
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.api.registry.delegate.reg
import team._0mods.aeternus.api.util.aRl
import team._0mods.aeternus.api.util.resloc
import team._0mods.aeternus.common.fluid.EtheriumFluid
import team._0mods.aeternus.common.helper.AeternusItem
import team._0mods.aeternus.common.item.DrilldwillArmor

object AeternusRegsitry {
    private val items = DeferredRegister.create(ModId, Registries.ITEM)
    private val dimensions = DeferredRegister.create(ModId, Registries.DIMENSION_TYPE)
    private val tabs = DeferredRegister.create(ModId, Registries.CREATIVE_MODE_TAB)
    private val fluids = DeferredRegister.create(ModId, Registries.FLUID)
    private val blocks = DeferredRegister.create(ModId, Registries.BLOCK)

    /* TABS */
    val aeternusTab by tabs.reg("aeternus_tab") {
        CreativeTabRegistry.create(Component.translatable("itemGroup.$ModId.${ModId}_tab")) { knowledgeBook.defaultInstance }
    }

    /* ITEMS */
    // MISC
    val knowledgeBook by items.reg("knowledge_book", ItemTypes.DEFAULT_ITEM)
    val etheriumTar by items.reg("etherium_tar", ItemTypes.DEFAULT_ITEM)
    val crystallizedEtherium by items.reg("crystallized_etherium", ItemTypes.DEFAULT_ITEM)
    val originaleEtherium by items.reg("orginale_etherium", ItemTypes.DEFAULT_ITEM)
    val drilldwill by items.reg("drilldwill", ItemTypes.DEFAULT_ITEM)

    // BUCKETS
    val etheriumBucket by items.reg("etherium_bucket", ItemTypes::ETHERIUM_BUCKET)

    // ARMORS
    val drilldwillHelmet by items.reg("drilldwill_helmet") {
        DrilldwillArmor(ArmorItem.Type.HELMET, ItemTypes.DEFAULT_PROPERTIES)
    }
    val drilldwillChest by items.reg("drilldwill_chestplate") {
        DrilldwillArmor(ArmorItem.Type.CHESTPLATE, ItemTypes.DEFAULT_PROPERTIES)
    }
    val drilldwillLegs by items.reg("drilldwill_leggings") {
        DrilldwillArmor(ArmorItem.Type.LEGGINGS, ItemTypes.DEFAULT_PROPERTIES)
    }
    val drilldwillBoots by items.reg("drilldwill_boots") {
        DrilldwillArmor(ArmorItem.Type.BOOTS, ItemTypes.DEFAULT_PROPERTIES)
    }

    /* BLOCKS */
    val etheriumFluidBlock by blocks.regBlock("etherium_fluid", BlockTypes.ETHERIUM_LIQUID_BLOCK)

    /* DIMENSIONS */
    val iterLevelStem = ResourceKey.create(Registries.LEVEL_STEM, "iter".aRl)
    val iterLevelKey = ResourceKey.create(Registries.DIMENSION, "iter".aRl)
    val iterDimType = ResourceKey.create(Registries.DIMENSION_TYPE, "iter".aRl)

    /* FLUIDS */
    val etheriumFluid by fluids.reg("etherium", EtheriumFluid::Source)
    val etheriumFlowing by fluids.reg("etherium_flowing", EtheriumFluid::Flowing)

    @JvmStatic
    fun init() {
        items.register()
    }

    private fun <V: Block, T: V> DeferredRegister<V>.regBlock(id: String, obj: () -> T): DelegatedRegistry<T> {
        val reg = this.register(id, obj)
        items.reg(id) { BlockItem(obj(), ItemTypes.DEFAULT_PROPERTIES) }
        return DelegatedRegistry(reg)
    }

    @Suppress("UnstableApiUsage")
    private object ItemTypes {
        @JvmField
        val DEFAULT_PROPERTIES = Item.Properties().`arch$tab`(aeternusTab)

        @JvmField
        val DEFAULT_ITEM = { AeternusItem() }

        val ETHERIUM_BUCKET by lazy { BucketItem(Fluids.EMPTY, Item.Properties().`arch$tab`(aeternusTab)) }
    }

    private object BlockTypes {
        @JvmField
        val ETHERIUM_LIQUID_BLOCK = {
            LiquidBlock(
                etheriumFlowing,
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
    }
}
