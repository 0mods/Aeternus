/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.common

import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.chunk.ChunkAccess
import team._0mods.aeternus.api.event.core.EventFactory

interface ChunkEvent {
    companion object {
        @JvmField val SAVE_DATA = EventFactory.createNoResult<SaveData>()

        @JvmField val LOAD_DATA = EventFactory.createNoResult<LoadData>()
    }

    fun interface SaveData {
        fun save(chunk: ChunkAccess, level: ServerLevel, tag: CompoundTag)
    }

    fun interface LoadData {
        fun load(chunk: ChunkAccess, level: ServerLevel?, tag: CompoundTag)
    }
}
