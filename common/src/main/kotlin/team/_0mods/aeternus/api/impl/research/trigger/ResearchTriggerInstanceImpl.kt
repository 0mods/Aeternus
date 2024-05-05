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

class ResearchTriggerInstanceImpl<T: ResearchTrigger, V>: ResearchTriggerInstance<T, V> {
    private val triggerMap: MutableMap<Class<T>, T> = mutableMapOf()

    override fun getTrigger(instance: Class<T>): T? = triggerMap[instance]

    override fun createTrigger(trigger: Class<T>, instance: V) {
        triggerMap[trigger] = trigger.getDeclaredConstructor().newInstance(instance)
    }
}
