/*
 * All Rights Received
 * Copyright (c) 2024.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.service.core

import team._0mods.aeternus.api.plugin.PluginHolder

interface PlatformHelper {
    fun isProduction(): Boolean

    fun isPhysicalClient(): Boolean

    fun isPhysicalServer(): Boolean = !isPhysicalClient()

    fun isModLoaded(modId: String): Boolean

    fun getModNameByModId(modId: String): String

    fun isForge(): Boolean = !isFabric()

    fun isFabric(): Boolean = !isForge()

    fun getPluginHolder(): PluginHolder
}