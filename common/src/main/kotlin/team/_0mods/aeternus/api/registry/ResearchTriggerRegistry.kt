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
import team._0mods.aeternus.api.magic.research.trigger.ResearchTrigger
import team._0mods.aeternus.api.magic.research.trigger.ResearchTriggerInstance

interface ResearchTriggerRegistry {
    val triggers: List<ResearchTrigger>

    @Deprecated("Use generic version of Trigger Registry")
    fun getResearchTriggerById(id: ResourceLocation): ResearchTrigger?

    @Deprecated("Use generic version of Trigger Registry")
    fun register(id: String, research: ResearchTrigger)

    @Deprecated("Use generic version of Trigger Registry")
    fun getByIdList(id: List<ResourceLocation>): List<ResearchTrigger>

    fun getResearchTriggerInstanceById(id: ResourceLocation): ResearchTriggerInstance?

    fun <T: ResearchTrigger, V> register(id: String, research: ResearchTriggerInstance)

    fun getInstancesByIdList(id: List<ResourceLocation>): List<ResearchTriggerInstance>

    fun <T: ResearchTrigger> getByIdOrClass(id: String): T?
}
