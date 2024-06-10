/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.capability

import net.minecraft.nbt.CompoundTag
import team._0mods.aeternus.api.event.LoadCapabilitiesEvent

interface ICapabilityDispatcher {
    val capabilities: MutableList<CapabilityInstance>
}

fun ICapabilityDispatcher.serializeCaps(tag: CompoundTag) {
    val nbt = CompoundTag()
    capabilities.forEach {
        nbt.put(it.javaClass.name, it.serializeNBT())
    }
    tag.put("aeternus_caps", nbt)
}

fun ICapabilityDispatcher.deserializeCapabilities(tag: CompoundTag) {
    val capabilities = tag.getCompound("hc_capabilities")
    for (key in capabilities.allKeys) {
        this.capabilities.find { it.javaClass.name == key }?.deserializeNBT(capabilities.getCompound(key))
    }
}

fun ICapabilityDispatcher.initialize() {
    LoadCapabilitiesEvent.EVENT.invoker().register(LoadCapabilitiesEvent.CapabilityEventInstance(this, capabilities))
}
