/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.platformredirect.api.util

import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.util.mcemulate.MCPlayer

val Player.isMoving: Boolean
    get() = this.deltaMovement.x != 0.0 || this.deltaMovement.z != 0.0

val Player.isJumping: Boolean
    get() = this.deltaMovement.y > 0.0

val Player.isFalling: Boolean
    get() = this.deltaMovement.y < 0.0

val Player.asMcPlayer: MCPlayer
    get() = this as MCPlayer

val MCPlayer.asPlayer: Player
    get() = this as Player
