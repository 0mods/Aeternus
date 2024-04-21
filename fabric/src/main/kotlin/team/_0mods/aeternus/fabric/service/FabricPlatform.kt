/*
 * All Rights Received
 * Copyright (c) 2024.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.fabric.service

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import team._0mods.aeternus.service.core.PlatformHelper
import kotlin.jvm.optionals.getOrNull

class FabricPlatform: PlatformHelper {
    private val fabricInstance = FabricLoader.getInstance()

    override fun isProduction(): Boolean = !fabricInstance.isDevelopmentEnvironment

    override fun isPhysicalClient(): Boolean = fabricInstance.environmentType.equals(EnvType.CLIENT)

    override fun isModLoaded(modId: String): Boolean = fabricInstance.isModLoaded(modId)

    override fun getModNameByModId(modId: String): String {
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

        return fabricInstance.getModContainer(modId).getOrNull()?.metadata?.name ?: failedName
    }

    override fun isFabric(): Boolean = true
}