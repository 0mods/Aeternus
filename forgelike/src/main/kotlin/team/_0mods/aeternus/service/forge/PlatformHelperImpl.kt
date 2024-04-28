/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service.forge

import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLEnvironment
import kotlin.jvm.optionals.getOrNull

object PlatformHelperImpl {
    @JvmStatic
    fun isProd(): Boolean = FMLEnvironment.production

    @JvmStatic
    fun isPhysicalClient(): Boolean = FMLEnvironment.dist.isClient

    @JvmStatic
    fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId)

    @JvmStatic
    fun getModNameByModId(modId: String): String {
        if (!isModLoaded(modId)) return "Mod with id '$modId' is not loaded."

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

        return ModList.get().getModContainerById(modId).getOrNull()?.modId ?: failedName
    }

    @JvmStatic
    fun isForge(): Boolean = true

    @JvmStatic
    fun isFabric(): Boolean = !isForge()
}
