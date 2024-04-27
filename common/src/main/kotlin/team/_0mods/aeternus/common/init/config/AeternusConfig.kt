/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.init.config

import team._0mods.aeternus.common.LOGGER

class AeternusConfig {
    interface Client
    interface Common {
        // CATEGORY: EXPERIMENTAL FEATURES
        val enableExperimentalFeatures: Boolean

        val butterMechanic: Boolean
    }

    var clientConfig: Client? = null
        set(value) {
            if (field != null) {
                warn(field!!.javaClass.name, value?.javaClass?.name)
            }

            field = value
        }

    var commonConfig: Common? = null
        set(value) {
            if (field != null) {
                warn(field!!.javaClass.name, value?.javaClass?.name)
            }

            field = value
        }


    private fun warn(fieldName: String, valueName: String?) {
        LOGGER.warn("Client config was replaced! Old: {}, New: {}", fieldName, valueName)
    }
}
