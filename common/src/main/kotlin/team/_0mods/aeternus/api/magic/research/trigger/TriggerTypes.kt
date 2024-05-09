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
import team._0mods.aeternus.api.util.rl
import java.util.*

interface PolymorphicResearchTriggerType<T> {
    var value: T
}

interface StringResearchTriggerType: PolymorphicResearchTriggerType<String>

interface BooleanResearchTriggerType: PolymorphicResearchTriggerType<Boolean>

interface IntResearchTriggerType: PolymorphicResearchTriggerType<Int>

interface DoubleResearchTriggerType: PolymorphicResearchTriggerType<Double>

interface ItemStackResearchTriggerType: StringResearchTriggerType {
    var count: Int

    var nbt: String

    fun toStack(): ItemStack = ItemStack(Holder.direct(BuiltInRegistries.ITEM.get(value.rl)), count, Optional.of(TagParser.parseTag(nbt)))
}
