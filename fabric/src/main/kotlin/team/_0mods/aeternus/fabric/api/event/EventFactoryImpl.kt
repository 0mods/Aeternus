/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.fabric.api.event

import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.core.EventActor
import team._0mods.aeternus.api.event.core.EventFactory
import java.util.function.Consumer

internal object EventFactoryImpl: EventFactory() {
    @ApiStatus.Internal
    override fun <T> attachToForge(evt: Event<Consumer<T>>): Event<Consumer<T>> = evt

    @ApiStatus.Internal
    override fun <T> attachToForgeEventActor(evt: Event<EventActor<T>>): Event<EventActor<T>> = evt

    @ApiStatus.Internal
    override fun <T> attachToForgeEventActorCancellable(evt: Event<EventActor<T>>): Event<EventActor<T>> = evt
}
