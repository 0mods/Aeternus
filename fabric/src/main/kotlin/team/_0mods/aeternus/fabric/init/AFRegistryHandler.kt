/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.fabric.init

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import team._0mods.aeternus.api.util.resloc
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

object AFRegistryHandler {
    fun init() {
        AeternusRegsitry.getBlocksForRegistry().entries.forEach {
            val rl = resloc(ModId, it.key)
            val blockNotInitialized = it.value
            val block = Registry.register(BuiltInRegistries.BLOCK, rl, blockNotInitialized.apply(BlockBehaviour.Properties.of()))
            Registry.register(BuiltInRegistries.ITEM, rl, BlockItem(block, Item.Properties()))
        }

        AeternusRegsitry.getItemsForRegistry().entries.forEach {
            val rl = resloc(ModId, it.key)
            val iNI = it.value
            Registry.register(BuiltInRegistries.ITEM, rl, iNI.apply(Item.Properties()))
        }
    }
}
