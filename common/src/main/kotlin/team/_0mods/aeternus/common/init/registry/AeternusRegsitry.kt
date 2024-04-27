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

import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.dimension.DimensionType
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.api.registry.delegate.reg
import team._0mods.aeternus.api.util.resloc

object AeternusRegsitry {
    private val items = DeferredRegister.create(ModId, Registries.ITEM)
    private val dimensions = DeferredRegister.create(ModId, Registries.DIMENSION_TYPE)

    /* RESOURCE REGISTRY KEYS */
    val researchRK: ResourceKey<Registry<Research>> = ResourceKey.createRegistryKey(resloc(ModId, "research"))

    /* ITEMS */
    val knowledgeBook by items.reg("knowledge_book") { Item(Item.Properties()) }

    /* BLOCKS */

    /* DIMENSION TYPES */
    val alTakeDim: ResourceKey<DimensionType> = ResourceKey.create(Registries.DIMENSION_TYPE, resloc(ModId, "altake"))

    @JvmStatic
    fun init() {
        items.register()
    }
}
