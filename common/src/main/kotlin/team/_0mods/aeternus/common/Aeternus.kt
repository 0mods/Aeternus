/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:JvmName("Aeternus")

package team._0mods.aeternus.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import team._0mods.aeternus.api.plugin.PluginHolder
import team._0mods.aeternus.common.init.AeternusEventsInit
import team._0mods.aeternus.common.init.registry.AeternusRegsitry

const val ModId = "aeternus"
const val ModName = "Aeternus"

@JvmField val LOGGER: Logger = LoggerFactory.getLogger("Aeternus") //const

fun commonInit() {
    AeternusRegsitry.init()
    AeternusEventsInit.initServerEvents()
    PluginHolder.instance.loadPlugins()
}

fun clientInit() {
    AeternusEventsInit.initClientEvents()
}
