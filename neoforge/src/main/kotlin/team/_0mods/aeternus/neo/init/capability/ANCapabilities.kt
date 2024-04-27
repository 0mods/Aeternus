/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.neo.init.capability

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.capabilities.EntityCapability
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import team._0mods.aeternus.api.magic.PlayerEtherium
import team._0mods.aeternus.api.magic.research.player.PlayerResearch
import team._0mods.aeternus.common.ModId
import team._0mods.aeternus.api.util.rl

@Mod.EventBusSubscriber(modid = ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
object ANCapabilities {
    val playerResearch: EntityCapability<PlayerResearch, Player> = EntityCapability.create("$ModId:player_research".rl, PlayerResearch::class.java, Player::class.java)
    val playerEtherium: EntityCapability<PlayerEtherium, Player> = EntityCapability.create("$ModId:player_etherium".rl, PlayerEtherium::class.java, Player::class.java)

    @JvmStatic
    @SubscribeEvent
    fun registerCaps(evt: RegisterCapabilitiesEvent) {
        evt.registerEntity(playerResearch, EntityType.PLAYER) { _, _ -> PlayerResearchCapability() }
    }
}
