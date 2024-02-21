/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.init.registry

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.dimension.DimensionType
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.registry.registries.DeferredRegister
import team._0mods.aeternus.api.util.resloc
import team._0mods.aeternus.common.ModId
import java.util.function.Function
import java.util.function.Supplier

object AeternusRegsitry {
    private val items = DeferredRegister.create(Registries.ITEM, ModId)

    /* RESOURCE KEYS */
    val tab: ResourceKey<CreativeModeTab> = ResourceKey.create(Registries.CREATIVE_MODE_TAB, resloc(ModId, "aeternus"))
    /* RESOURCE REGISTRY KEYS */
    val researchRK: ResourceKey<Registry<Research>> = ResourceKey.createRegistryKey(resloc(ModId, "research"))

    /* ITEMS */
    val knowledgeBook = items.register("knowledge_book") { Item(Item.Properties()) }

    /* BLOCKS */

    /* DIMENSION TYPES */
    val alTakeDim: ResourceKey<DimensionType> = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))

    @JvmStatic
    fun init() {
        items.register()
    }
}
