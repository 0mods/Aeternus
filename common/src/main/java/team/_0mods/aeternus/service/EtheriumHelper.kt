/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service

import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.world.entity.player.Player

object EtheriumHelper {
    @JvmStatic @ExpectPlatform
    fun add(addFor: Player, count: Int) {
        throw AssertionError()
    }

    @JvmStatic @ExpectPlatform
    fun consume(consumeFor: Player, count: Int) {
        throw AssertionError()
    }

    @JvmStatic @ExpectPlatform
    fun getCountForPlayer(player: Player): Int {
        throw AssertionError()
    }
}
