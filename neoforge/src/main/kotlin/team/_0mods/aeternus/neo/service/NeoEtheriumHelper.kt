/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.neo.service

import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.magic.PlayerEtherium
import team._0mods.aeternus.neo.init.capability.ANCapabilities
import team._0mods.aeternus.service.core.EtheriumHelper

class NeoEtheriumHelper: EtheriumHelper {
    private val Player.etherium: PlayerEtherium
        get() = this.getCapability(ANCapabilities.playerEtherium, this) ?: throw NullPointerException()

    override fun add(addFor: Player, count: Int) {
        addFor.etherium + count
    }

    override fun consume(consumeFrom: Player, count: Int) {
        consumeFrom.etherium - count
    }

    override fun getCountForPlayer(countFor: Player): Int = countFor.etherium.etheriumCount
}