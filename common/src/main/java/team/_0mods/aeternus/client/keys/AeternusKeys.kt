/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.client.keys

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import team._0mods.aeternus.common.ModId

internal val registries: MutableList<KeyMapping> = mutableListOf()

object AeternusKeys {
    private const val AETERNUS_CATEGORY = "category.$ModId.$ModId"
    private val KeyMapping.reg: KeyMapping
        get() {
            if (registries.stream().noneMatch { it == this })
                registries.add(this)

            return this
        }

    val configGUIOpen: KeyMapping = KeyMapping(
        "key.$ModId.config_screen",
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_APOSTROPHE,
        AETERNUS_CATEGORY
    ).reg

}