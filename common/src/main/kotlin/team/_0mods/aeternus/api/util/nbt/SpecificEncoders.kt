/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.util.nbt

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.internal.NamedValueDecoder
import kotlinx.serialization.internal.NamedValueEncoder
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag

@OptIn(InternalSerializationApi::class)
internal abstract class NamedValueTagEncoder : NamedValueEncoder(), ICanEncodeTag {
    final override fun encodeTag(tag: Tag) = encodeTaggedTag(popTag(), tag)
    abstract fun encodeTaggedTag(key: String, tag: Tag)
}

@OptIn(InternalSerializationApi::class)
internal abstract class NamedValueNbtDecoder : NamedValueDecoder(), ICanDecodeTag {
    final override fun decodeTag(): Tag = decodeTaggedTag(popTag())
    abstract fun decodeTaggedTag(key: String): Tag
}

internal interface ICanEncodeTag : ICanEncodeCompoundNBT {
    fun encodeTag(tag: Tag)
    override fun encodeCompoundNBT(tag: Tag) = encodeTag(tag)
}

internal interface ICanDecodeTag : ICanDecodeCompoundNBT {
    fun decodeTag(): Tag
    override fun decodeCompoundNBT(): CompoundTag = decodeTag() as CompoundTag
}

internal fun interface ICanEncodeCompoundNBT {
    fun encodeCompoundNBT(tag: Tag)
}

internal fun interface ICanDecodeCompoundNBT {
    fun decodeCompoundNBT(): CompoundTag
}
