/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package team._0mods.multilib.forge.service

import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLEnvironment
import team._0mods.multilib.service.core.PlatformHelper
import kotlin.jvm.optionals.getOrNull

class ForgePlatformHelper: PlatformHelper {
    override fun isProduction(): Boolean = FMLEnvironment.production

    override fun isPhysicalClient(): Boolean = FMLEnvironment.dist.isClient

    override fun isModLoaded(modId: String): Boolean = ModList.get().isLoaded(modId)

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

        return ModList.get().getModContainerById(modId).getOrNull()?.modId ?: failedName
    }

    override fun isForge(): Boolean = true
}
