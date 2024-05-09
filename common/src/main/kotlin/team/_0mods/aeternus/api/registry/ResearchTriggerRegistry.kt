/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.registry

import net.minecraft.resources.ResourceLocation
import team._0mods.aeternus.api.magic.research.trigger.*

interface ResearchTriggerRegistry {
    val triggers: List<ResearchTrigger>

    fun getResearchTriggerById(id: ResourceLocation): ResearchTrigger?

    fun register(id: String, research: ResearchTrigger)

    fun getByIdList(id: List<ResourceLocation>): List<ResearchTrigger>

    fun getStackTrigger(id: ResourceLocation): ItemStackResearchTrigger?

    fun getStringTrigger(id: ResourceLocation): StringResearchTrigger?

    fun getIntTrigger(id: ResourceLocation): IntResearchTrigger?

    fun getDoubleTrigger(id: ResourceLocation): DoubleResearchTrigger?
}
