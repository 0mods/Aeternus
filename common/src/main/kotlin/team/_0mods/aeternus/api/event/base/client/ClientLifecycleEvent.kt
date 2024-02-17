/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.base.client

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import team._0mods.aeternus.api.event.base.common.LifecycleEvent
import team._0mods.aeternus.api.event.core.EventFactory

interface ClientLifecycleEvent {
    companion object {
        @JvmField val CLIENT_SETUP = EventFactory.createNoResult<ClientState>()
        @JvmField val CLIENT_STARTED = EventFactory.createNoResult<ClientState>()
        @JvmField val CLIENT_STOPPING = EventFactory.createNoResult<ClientState>()
        @JvmField val CLIENT_LEVEL_LOAD = EventFactory.createNoResult<ClientLevelState>()
    }

    fun interface ClientState: LifecycleEvent.InstanceState<Minecraft>

    fun interface ClientLevelState: LifecycleEvent.LevelState<ClientLevel>
}
