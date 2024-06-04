/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service

import dev.architectury.injectables.annotations.ExpectPlatform
import java.nio.file.Path

object PlatformHelper {
    @JvmStatic @ExpectPlatform
    fun isProd(): Boolean = throw AssertionError()

    @JvmStatic @ExpectPlatform
    fun isPhysicalClient(): Boolean = throw AssertionError()

    @JvmStatic
    fun isPhysicalServer(): Boolean = !isPhysicalClient()

    @JvmStatic @ExpectPlatform
    fun isLogicalClient(): Boolean = throw AssertionError()

    @JvmStatic
    fun isLogicalServer(): Boolean = !isLogicalClient()

    @JvmStatic @ExpectPlatform
    fun isModLoaded(modId: String): Boolean = throw AssertionError()

    @JvmStatic @ExpectPlatform
    fun getModNameByModId(modId: String): String = throw AssertionError()

    @JvmStatic @ExpectPlatform
    fun isForge(): Boolean = throw AssertionError()

    @JvmStatic @ExpectPlatform
    fun isFabric(): Boolean = throw AssertionError()

    @JvmStatic @ExpectPlatform
    fun gamePath(): Path = throw AssertionError()
}