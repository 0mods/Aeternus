/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forge.init

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

object AFRegistryHandler {
    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
    private val blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

    init {
        AeternusRegsitry.getBlocksForRegistry().entries.forEach {
            val id = it.key
            val block = it.value
            val reg = blocks.register(id) { block.apply(BlockBehaviour.Properties.of()) }
            items.register(id) { BlockItem(reg.get(), Item.Properties()) }
        }

        AeternusRegsitry.getItemsForRegistry().entries.forEach {
            val id = it.key
            val item = it.value
            items.register(id) { item.apply(Item.Properties()) }
        }
    }

    fun init(bus: IEventBus) {
        items.register(bus)
        blocks.register(bus)
    }
}
