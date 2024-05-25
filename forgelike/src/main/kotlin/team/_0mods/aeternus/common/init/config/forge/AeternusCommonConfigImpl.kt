/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.init.config.forge

import team._0mods.aeternus.common.init.config.SpecUtils

object AeternusCommonConfigImpl {
    private val spec = SpecUtils.commonBuilder

    val debug = spec.comment("Enables debug mode").define("debug", false)

    init {
        spec.push("Experimental")
    }

    val exFeatures = spec.comment("Enables all unstable features", "USE AT YOUR RISK!").define("experimental_features", false)
    val bm = spec.define("butter_mechanic", false)

    init {
        spec.pop()
    }

    //IMPLSя
    val enableExperimentalFeatures: Boolean = exFeatures.get()

    val butterMechanic: Boolean = bm.get()

    val debugMode: Boolean = debug.get()
}
