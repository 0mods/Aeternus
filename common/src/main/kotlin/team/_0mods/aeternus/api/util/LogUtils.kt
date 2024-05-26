/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.util

import org.slf4j.Logger
import org.slf4j.Marker
import team._0mods.aeternus.common.commonConfig
import team._0mods.aeternus.service.PlatformHelper

private val dm = commonConfig.debug.value

fun Logger.debugIfEnabled(msg: String) {
    if (dm || !PlatformHelper.isProd()) this.info("[DEBUG MODE] $msg")
}

fun Logger.debugIfEnabled(msg: String, any: Any) {
    if (dm || !PlatformHelper.isProd()) this.info("[DEBUG MODE] $msg", any)
}

fun Logger.debugIfEnabled(msg: String, any: Any, obj: Any) {
    if (dm || !PlatformHelper.isProd()) this.info("[DEBUG MODE] $msg", any, obj)
}

fun Logger.debugIfEnabled(msg: String, vararg any: Any) {
    if (dm || !PlatformHelper.isProd()) this.info("[DEBUG MODE] $msg", any)
}

fun Logger.debugIfEnabled(msg: String, thr: Throwable) {
    if (dm || !PlatformHelper.isProd()) this.info("[DEBUG MODE] $msg", thr)
}

fun Logger.debugIfEnabled(marker: Marker, msg: String) {
    if (dm || !PlatformHelper.isProd()) this.info(marker, "[DEBUG MODE] $msg")
}

fun Logger.debugIfEnabled(marker: Marker, msg: String, any: Any) {
    if (dm || !PlatformHelper.isProd()) this.info(marker, "[DEBUG MODE] $msg", any)
}

fun Logger.debugIfEnabled(marker: Marker, msg: String, any: Any, obj: Any) {
    if (dm || !PlatformHelper.isProd()) this.info(marker, "[DEBUG MODE] $msg", any, obj)
}

fun Logger.debugIfEnabled(marker: Marker, msg: String, vararg any: Any) {
    if (dm || !PlatformHelper.isProd()) this.info(marker, "[DEBUG MODE] $msg", any)
}

fun Logger.debugIfEnabled(marker: Marker, msg: String, thr: Throwable) {
    if (dm || !PlatformHelper.isProd()) this.info(marker, "[DEBUG MODE] $msg", thr)
}
