/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.platformredirect.common.init

import team._0mods.aeternus.api.plugin.AeternusPlugin
import team._0mods.aeternus.api.plugin.AeternusPluginInit
import team._0mods.aeternus.api.registry.*
import team._0mods.aeternus.platformredirect.common.LOGGER
import team._0mods.aeternus.platformredirect.common.ModId

@AeternusPluginInit(ModId)
class AeternusCorePlugin: AeternusPlugin {
    companion object {
        lateinit var researchRegistry: ResearchRegistry
        lateinit var triggerRegistry: ResearchTriggerRegistry
    }

    override fun registerResearch(reg: ResearchRegistry) {
        researchRegistry = reg
//        ReloadListenerRegistry.register(PackType.SERVER_DATA, ResearchReloadListener(reg))
        LOGGER.info("Research Registry is initialized")
    }

    override fun registerResearchTriggers(reg: ResearchTriggerRegistry) {
        triggerRegistry = reg
        LOGGER.info("Research Triggers Registry is initialized")
    }
}
