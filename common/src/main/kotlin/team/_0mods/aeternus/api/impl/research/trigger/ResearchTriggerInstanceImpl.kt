/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.impl.research.trigger

import team._0mods.aeternus.api.magic.research.trigger.ResearchTrigger
import team._0mods.aeternus.api.magic.research.trigger.ResearchTriggerInstance

internal object ResearchTriggerInstanceImpl: ResearchTriggerInstance {
    override fun getTrigger(instance: Class<out ResearchTrigger>): ResearchTrigger? {
        val tm = ResearchTriggerInstance.triggerMap()
        return tm[instance]
    }

    override fun <V> createTrigger(trigger: Class<out ResearchTrigger>, instance: V) {
        val tm = ResearchTriggerInstance.triggerMap()
        val insts = ResearchTriggerInstance.triggerInstances<V>()
        val triggerInstance = trigger.getDeclaredConstructor().newInstance(instance)
        insts[triggerInstance] = instance
        tm[trigger] = triggerInstance
    }
}
