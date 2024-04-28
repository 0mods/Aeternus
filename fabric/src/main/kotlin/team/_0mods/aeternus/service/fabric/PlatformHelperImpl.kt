/*
 * All Rights Received
 * Copyright (c) 2024 0mods. 
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service.fabric

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import kotlin.jvm.optionals.getOrNull

object PlatformHelperImpl {
    private val fabric = FabricLoader.getInstance()

    @JvmStatic
    fun isProd(): Boolean = !fabric.isDevelopmentEnvironment

    @JvmStatic
    fun isPhysicalClient(): Boolean = fabric.environmentType.equals(EnvType.CLIENT)

    @JvmStatic
    fun isModLoaded(modId: String): Boolean = fabric.isModLoaded(modId)

    @JvmStatic
    fun getModNameByModId(modId: String): String {
        if (!isModLoaded(modId)) return "Mod with id '$modId' is not loaded."

        // yeah, it's very impractical, but sorry, I really want to do it >_<
        val cat = StringBuilder()
        cat.append("\n")
        cat.append("　　　　　／＞　　フ").append("\n")
        cat.append("　　　　　| 　_　 _ l").append("\n")
        cat.append("　 　　　／` ミ＿xノ").append("\n")
        cat.append("　　 　 /　　　 　 |").append("\n")
        cat.append("　　　 /　 ヽ　　 ﾉ").append("\n")
        cat.append("　 　 │　　| | |")
        val builtCat = cat.toString()
        val failedName = "Mod Name for Mod ID: $modId is not loaded! It is so sad :($builtCat"

        return fabric.getModContainer(modId).getOrNull()?.metadata?.name ?: failedName
    }

    @JvmStatic
    fun isForge(): Boolean = throw AssertionError()

    @JvmStatic
    fun isFabric(): Boolean = throw AssertionError()
}
