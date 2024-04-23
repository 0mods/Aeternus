/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event

import dev.architectury.event.Event
import dev.architectury.event.EventFactory
import dev.architectury.event.EventResult
import net.minecraft.network.chat.Component
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.repository.*
import net.minecraft.server.packs.repository.Pack.*
import java.util.function.Consumer

fun interface AddPackEvent {
    companion object {
        @JvmField val ASSETS: Event<ClientPack> = EventFactory.createLoop()

        @JvmField val DATA: Event<ServerPack> = EventFactory.createLoop()
    }

    fun onAdd(source: Consumer<Pack>, creator: PackCreator)

    fun interface ClientPack: AddPackEvent

    fun interface ServerPack: AddPackEvent

    class PackCreator {
        fun readMetaAndCreate(
            id: String,
            title: Component,
            required: Boolean,
            resources: ResourcesSupplier,
            packType: PackType,
            position: Position,
            packSource: PackSource
        ) = Pack.readMetaAndCreate(id, title, required, resources, packType, position, packSource)

        fun create(
            id: String,
            title: Component,
            required: Boolean,
            resources: ResourcesSupplier,
            info: Info,
            position: Position,
            fixedPos: Boolean,
            packSource: PackSource
        ): Pack = Pack.create(id, title, required, resources, info, position, fixedPos, packSource)
    }
}
