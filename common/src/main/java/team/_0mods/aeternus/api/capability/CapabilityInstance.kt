/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.capability

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import team._0mods.aeternus.api.packets.*
import team._0mods.aeternus.network.*

open class CapabilityInstance {
    val properties = ArrayList<CapabilityProperty<CapabilityInstance, *>>()
    var notUsedTags = CompoundTag()
    open val consumeOnServer: Boolean = false
    open val canOtherPlayersAccess: Boolean = true
    lateinit var provider: ICapabilityDispatcher // It will be initialized by injector

    fun <T> syncable(default: T) = CapabilityProperty<CapabilityInstance, T>(default).apply {
        properties += this
    }

    fun sync() {
        when (val target = provider) {
            is Entity -> {
                if (target.level().isClientSide) {
                    if (consumeOnServer) SSyncEntityCapabilityPacket(target.id, javaClass.name, serializeNBT()).send()
                } else CSyncEntityCapabilityPacket(target.id, javaClass.name, serializeNBT()).sendTrackingEntity(target)
            }

            is Level -> {
                if (target.isClientSide) {
                    if (consumeOnServer) SSyncLevelCapabilityPacket(
                        target.dimension().location().toString(),
                        javaClass.name, serializeNBT()
                    ).send()
                } else CSyncLevelCapabilityPacket(javaClass.name, serializeNBT()).sendAllInDimension(target)
            }
        }
    }

    fun serializeNBT() = notUsedTags.copy().apply {
        properties.forEach { it.serialize(this) }
    }

    fun deserializeNBT(nbt: Tag) {
        properties.forEach { if (it.deserialize(nbt as? CompoundTag ?: return)) nbt.remove(it.defaultName) }
        val tag = nbt as? CompoundTag ?: return
        notUsedTags.mergeData(tag)
    }

    fun CompoundTag.mergeData(other: CompoundTag) {
        other.allKeys.forEach { key ->
            when (val value = this[key]) {
                is ListTag -> value.addAll(other[key] as ListTag)
                is CompoundTag -> value.mergeData(other[key] as CompoundTag)
                else -> this.put(key, other[key] ?: return)
            }
        }
    }

    inline fun <reified T : Any> syncableList(list: MutableList<T> = ArrayList()) =
        syncable(SyncableListImpl(list, T::class.java, this::sync))

    inline fun <reified T : Any> syncableList(vararg elements: T) = syncableList(elements.toMutableList())

    inline fun <reified K : Any, reified V : Any> syncableMap() =
        syncable(SyncableMapImpl(HashMap(), K::class.java, V::class.java, this::sync))
}