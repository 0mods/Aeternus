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

import net.minecraft.locale.Language
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.EndTag
import team._0mods.aeternus.api.util.c
import team._0mods.aeternus.api.util.nbt.NBTFormat
import team._0mods.aeternus.api.util.nbt.deserializeNoInline
import team._0mods.aeternus.api.util.nbt.serializeNoInline
import team._0mods.aeternus.common.LOGGER
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.javaType

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalStdlibApi::class)
open class CapabilityProperty<T : CapabilityInstance, V : Any?>(var value: V) : ReadWriteProperty<T, V> {
    var defaultName = ""
    private var defaultType: Class<out V>? = null
    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (defaultName.isEmpty()) {
            defaultName = property.name
            defaultType = if(value == null) property.returnType.javaType as Class<out V> else value!!.javaClass
            if(property.name !in thisRef.notUsedTags) return value

            val tag = thisRef.notUsedTags.get(property.name) ?: return value
            if (tag is EndTag) return value

            value = NBTFormat.deserializeNoInline(tag, property.returnType.javaType as Class<out V>) as V
        }

        return value
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
        if (defaultName.isEmpty()) defaultName = property.name
        this.value = value
        if (defaultType == null) defaultType = if(this.value == null) property.returnType.javaType as Class<out V> else this.value!!.javaClass
        thisRef.sync()
    }

    fun serialize(tag: CompoundTag) {
        if (defaultName.isNotEmpty() && defaultType != null) {
            when (value) {
                null -> tag.put(defaultName, EndTag.INSTANCE)
                else -> tag.put(
                    defaultName,
                    NBTFormat.serializeNoInline(c(value), this.defaultType!!)
                )
            }
            Language.getInstance()
        }
    }

    fun deserialize(tag: CompoundTag): Boolean {
        if (defaultName.isNotEmpty() && defaultType != null && defaultName in tag) {
            try {
                if (tag[defaultName] is EndTag) value = null as V
                else value = NBTFormat.deserializeNoInline(tag[defaultName]!!, this.defaultType!!)
                return true
            } catch (e: Exception) {
                LOGGER.error("Error while deserializing {}: {}", defaultType, tag[defaultName], e)
            }
        }
        return false
    }
}
