/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.init

import team._0mods.aeternus.api.AeternusPluginInit
import team._0mods.aeternus.api.AeternusPlugin
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.api.registry.ResearchTriggerRegistry
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.common.ModId

@AeternusPluginInit
class AeternusCorePlugin: AeternusPlugin {
    companion object {
        lateinit var researchRegistry: ResearchRegistry
        lateinit var triggerRegistry: ResearchTriggerRegistry
    }

    override val modId: String
        get() = ModId

    override fun registerResearch(reg: ResearchRegistry) {
        researchRegistry = reg
        LOGGER.info("Research Registry is initialized")
    }

    override fun registerResearchTriggers(reg: ResearchTriggerRegistry) {
        triggerRegistry = reg
        LOGGER.info("Research Triggers Registry is initialized")
    }
}
