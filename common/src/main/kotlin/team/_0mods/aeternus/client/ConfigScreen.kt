/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.client

import imgui.ImGui
import imgui.ImVec2
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import net.minecraft.world.item.DiggerItem
import team._0mods.aeternus.api.client.gui.height
import team._0mods.aeternus.api.client.gui.width
import team._0mods.aeternus.api.client.screen.ImGuiScreen
import team._0mods.aeternus.api.config.CommentedValue
import team._0mods.aeternus.api.config.CommentedValue.Companion.create
import team._0mods.aeternus.api.config.regenerateCfg
import team._0mods.aeternus.api.util.imguiTranslate
import team._0mods.aeternus.common.commonConfig
import team._0mods.aeternus.common.commonConfigInstance
import team._0mods.aeternus.common.init.config.AeternusCommonConfig

fun configScreen() = ImGuiScreen {
    val fileName = commonConfigInstance.fileName
    val defaultConfig = AeternusCommonConfig()

//    ImGui.getBackgroundDrawList().addRectFilled(0F, 0F, width, height, ImGui.colorConvertFloat4ToU32(0F, 0F, 0F, 1F))

    ImGui.setNextWindowSize(1250F, 750F)

    if (ImGui.begin(
        "gui.aeternus.config".imguiTranslate,
        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.AlwaysAutoResize
    )) {

//    ImGui.setWindowPos(width / 2 - ImGui.getWindowSizeX() / 2, height / 2 - ImGui.getWindowSizeY() / 2)

        val experimentalCategory = AeternusCommonConfig.CategoryExperimental(
            CommentedValue.create(
                booleanValue("gui.aeternus.config.experimental_features", commonConfig.debug()),
                *defaultConfig.debug.comment.toTypedArray()
            )
        )

        if (ImGui.button("".imguiTranslate)) {
            regenerateCfg(
                AeternusCommonConfig(
                    CommentedValue.create(
                        booleanValue("gui.aeternus.config.debug", commonConfig.debug()),
                        *defaultConfig.debug.comment.toTypedArray()
                    ),
                    experimentalCategory
                ),
                fileName
            )
        }
    }
}

private fun booleanValue(id: String, boolean: Boolean): Boolean {
    var value = false
    val configValue = if (boolean) ImInt(1) else ImInt(0)

    if (ImGui.combo(id, configValue, arrayOf("true", "false"), 2)) {
        val cfg = configValue.get()
        value = when(cfg) {
            0 -> false
            1 -> true
            else -> false
        }
    }

    return value
}
