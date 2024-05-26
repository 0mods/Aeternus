/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.capability

import net.minecraftforge.common.extensions.IForgeFluid
import team._0mods.aeternus.api.magic.PlayerEtherium
import kotlin.math.*

class PlayerEtheriumCapability : PlayerEtherium {
    private var etherium = 0

    override val etheriumCount: Int = etherium

    override var canRegenerate: Boolean = false

    override fun plus(count: Int) {
        etherium += min(0, count)
    }

    override fun minus(count: Int) {
        etherium -= max(etherium - count, 0)
    }
}
