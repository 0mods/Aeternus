/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.impl.event

import net.minecraft.world.item.ItemStack
import team._0mods.aeternus.api.event.base.client.TooltipEvent

class TooltipAdditionalContext: TooltipEvent.AdditionalContents {
    companion object {
        private val threadLocal = ThreadLocal.withInitial(::TooltipAdditionalContext)

        @JvmStatic
        fun get(): TooltipAdditionalContext = threadLocal.get()
    }

    private var stack: ItemStack? = null

    override var item: ItemStack?
        get() = stack
        set(value) {
            this.stack = value
        }
}
