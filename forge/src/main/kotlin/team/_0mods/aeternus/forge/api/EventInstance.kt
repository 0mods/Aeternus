/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forge.api

import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.IModBusEvent
import org.jetbrains.annotations.ApiStatus.Internal
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

inline fun <reified T: Event> forgeEventAndStop(noinline evt: (T) -> Boolean) {
    val event = ForgeEventInstance(T::class.java, evt)
    FORGE_BUS.register(event)
}

inline fun <reified T: Event> forgeEvent(noinline evt: (T) -> Unit) {
    val event = ForgeEventInstance(T::class.java) { evt(it); return@ForgeEventInstance false }
    FORGE_BUS.register(event)
}

inline fun <reified T> modEventAndStop(noinline evt: (T) -> Boolean) where T: Event, T: IModBusEvent {
    val event = ModEventInstance(T::class.java, evt)
    MOD_BUS.register(event)
}

inline fun <reified T> modEvent(noinline evt: (T) -> Unit) where T: Event, T: IModBusEvent {
    val event = ModEventInstance(T::class.java) { evt(it); return@ModEventInstance false }
    MOD_BUS.register(event)
}

class ModEventInstance<T>(private val type: Class<T>, private val unregisterAfter: (T) -> Boolean) where T : Event, T: IModBusEvent {
    @SubscribeEvent
    fun onEvent(evt: T) {
        val eType = evt::class.java

        if (!eType.isAssignableFrom(type)) return

        try {
            if (unregisterAfter.invoke(evt)) {
                MOD_BUS.unregister(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

class ForgeEventInstance<T: Event>(private val type: Class<T>, private val unregisterAfter: (T) -> Boolean) {
    @SubscribeEvent
    fun onEvent(evt: T) {
        val eType = evt::class.java

        if (!eType.isAssignableFrom(type)) return

        try {
            if (unregisterAfter.invoke(evt)) {
                FORGE_BUS.unregister(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
