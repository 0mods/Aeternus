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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import team._0mods.aeternus.api.config.CommentedValue
import team._0mods.aeternus.api.config.CommentedValue.Companion.create

@Serializable
data class AeternusCommonConfig(
    val debug: CommentedValue<Boolean> = CommentedValue.create(false, "Enables debug mode"),
    val experimental: CategoryExperimental = CategoryExperimental()
) {
    companion object {
        val defaultConfig = AeternusCommonConfig()
    }

    @Serializable
    data class CategoryExperimental(
        @SerialName("experimental_features")
        val enableExperimentals: CommentedValue<Boolean> =
            CommentedValue.create(false, "Enables all unstable features", "USE AT YOUR RISK!"),
        @SerialName("butter_mechanic")
        val butterMechanic: Boolean = false
    )
}

@Serializable
data class AeternusClientConfig(
    @SerialName("low_mode")
    val lowMode: Boolean = false
) {
    companion object {
        val defaultConfig = AeternusClientConfig()
    }
}
