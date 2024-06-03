/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.block.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

interface IBlockEntity<T: BlockEntity?> {
    val hasTicker: Boolean
        get() = false

    fun onPlace(level: Level, state: BlockState, oldState: BlockState, isMoving: Boolean)

    fun onRemove(level: Level, state: BlockState, oldState: BlockState, isMoving: Boolean)

    fun onPlacedBy(level: Level, state: BlockState, placer: LivingEntity?, stack: ItemStack)

    fun tickOnClient(level: Level, pos: BlockPos, state: BlockState, entity: T) {}

    fun tickOnServer(level: Level, pos: BlockPos, state: BlockState, entity: T) {}
}
