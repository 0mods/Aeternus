/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.mixin.item

import net.minecraft.world.item.Item
import org.spongepowered.asm.mixin.Mixin
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.magic.research.ResearchRequired

@Mixin(Item::class)
class ItemMixin: ResearchRequired {
    override val requirements: Set<Research>
        get() = emptySet()

    override var lockItem: Boolean = false
}
