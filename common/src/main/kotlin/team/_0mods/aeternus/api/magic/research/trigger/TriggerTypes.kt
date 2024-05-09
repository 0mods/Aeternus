/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
@file:JvmName("TriggerTypes")

package team._0mods.aeternus.api.magic.research.trigger

import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.TagParser
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.util.rl
import java.util.*

@ApiStatus.Internal
interface PolymorphicTypedResearchTrigger<T>: ResearchTrigger {
    var value: T
}

interface StringResearchTrigger: PolymorphicTypedResearchTrigger<String>

interface BooleanResearchTrigger: PolymorphicTypedResearchTrigger<Boolean>

interface IntResearchTrigger: PolymorphicTypedResearchTrigger<Int>

interface DoubleResearchTrigger: PolymorphicTypedResearchTrigger<Double>

interface ItemStackResearchTrigger: StringResearchTrigger {
    var count: Int

    var nbt: String

    fun toStack(): ItemStack = ItemStack(Holder.direct(BuiltInRegistries.ITEM.get(value.rl)), count, Optional.of(TagParser.parseTag(nbt)))
}
