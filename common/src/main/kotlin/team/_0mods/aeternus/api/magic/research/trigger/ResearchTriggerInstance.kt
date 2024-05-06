/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic.research.trigger

import team._0mods.aeternus.api.impl.research.trigger.ResearchTriggerInstanceImpl

interface ResearchTriggerInstance {
    companion object {
        @JvmStatic
        fun triggerMap(): MutableMap<Class<out ResearchTrigger>, ResearchTrigger> = linkedMapOf()

        @JvmStatic
        fun <V> triggerInstances(): MutableMap<in ResearchTrigger, V> = linkedMapOf()

        @JvmStatic
        fun Companion.createInstance(): ResearchTriggerInstance = ResearchTriggerInstanceImpl

        @JvmStatic
        @JvmName("createInstance")
        fun createTriggerInstance(): ResearchTriggerInstance = this.createInstance()
    }

    fun getTrigger(instance: Class<out ResearchTrigger>): ResearchTrigger?

    fun <V> createTrigger(trigger: Class<out ResearchTrigger>, instance: V)
}
