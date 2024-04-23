/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.fabric.event

import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import team._0mods.aeternus.api.event.AddPackEvent
import team._0mods.aeternus.api.util.rl

object AddPackHandler {
    @JvmStatic
    fun client() {
        AddPackEvent.ASSETS.invoker().onAdd({
            ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(object : SimpleSynchronousResourceReloadListener {
                override fun getFabricId(): ResourceLocation = it.id.rl

                override fun onResourceManagerReload(resourceManager: ResourceManager) {}
            })
        }, AddPackEvent.PackCreator())
    }

    @JvmStatic
    fun server() {
        AddPackEvent.DATA.invoker().onAdd({
            ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(object : SimpleSynchronousResourceReloadListener {
                override fun getFabricId(): ResourceLocation = it.id.rl

                override fun onResourceManagerReload(resourceManager: ResourceManager) {}
            })
        }, AddPackEvent.PackCreator())
    }
}
