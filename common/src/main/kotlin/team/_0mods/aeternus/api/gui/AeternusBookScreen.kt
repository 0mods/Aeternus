/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.gui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import team._0mods.multilib.util.toRL
import java.io.IOException
import java.nio.file.AccessDeniedException
import java.util.*

open class AeternusBookScreen(text: Component) : Screen(text) {
    protected fun readBookEntry(rl: ResourceLocation): BookEntry? {
        var resource: Optional<Resource>? = null
        var page: BookEntry? = null

        try {
            resource = Minecraft.getInstance().resourceManager.getResource(rl)
            if (resource.isPresent) {
                val str = resource.get().openAsReader()
                page = BookEntry.deserialize(str)
            }
        } catch (e: IOException) {
            if (e !is AccessDeniedException) {
                e.printStackTrace()
            }
        }

        return page
    }

    fun isVisibleEntry(lTo: String): Boolean {
        val rl = "${bookDirectory}$lTo".toRL()
        val entry = readBookEntry(rl)
        return entry != null && entry.isUnlocked(this) || Minecraft.getInstance().player != null && Minecraft.getInstance().player!!.isCreative
    }
}
