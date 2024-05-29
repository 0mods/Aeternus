/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.client.screen

import imgui.ImGui
import imgui.flag.ImGuiDir
import imgui.type.ImBoolean
import imgui.type.ImInt
import team._0mods.aeternus.api.client.screen.ImGuiScreen
import team._0mods.aeternus.api.util.imguiTranslate
import team._0mods.aeternus.common.commonConfig
import team._0mods.aeternus.common.commonConfigInstance
import team._0mods.aeternus.common.init.config.AeternusCommonConfig

fun configScreen() = ImGuiScreen {
    val fileName = commonConfigInstance.fileName
    val defaultConfig = AeternusCommonConfig()
    val currentConfig = commonConfig

//    ImGui.setNextWindowSize(1250F, 750F)
//
//    if (ImGui.begin(
//        "gui.aeternus.config".imguiTranslate,
//        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.AlwaysAutoResize
//    )) {

//    ImGui.setWindowPos(width / 2 - ImGui.getWindowSizeX() / 2, height / 2 - ImGui.getWindowSizeY() / 2)

//        val experimentalCategory = AeternusCommonConfig.CategoryExperimental(
//            CommentedValue.create(
//                booleanValue("gui.aeternus.config.experimental_features", commonConfig.debug()),
//                *defaultConfig.debug.comment.toTypedArray()
//            )
//        )
//
//        if (ImGui.button("".imguiTranslate)) {
//            regenerateCfg(
//                AeternusCommonConfig(
//                    CommentedValue.create(
//                        booleanValue("gui.aeternus.config.debug", commonConfig.debug()),
//                        *defaultConfig.debug.comment.toTypedArray()
//                    ),
//                    experimentalCategory
//                ),
//                fileName
//            )
//        }
//    }

    ImGui.setNextWindowSize(1250F, 750F)
    var categoryCombo = false
    val categoryChoiced = ImInt(0)
    if (ImGui.begin("", ImBoolean(true))) {
        ImGui.setCursorPos(25F, 40F)
        if (ImGui.beginChild(1, 190F, 695F, true)) {
            ImGui.setCursorPos(60F, 15F)

            ImGui.text("gui.aeternus.config.categories".imguiTranslate)

            ImGui.setCursorPos(20F, 73.5F)
            ImGui.pushItemWidth(150F)

            val cat: MutableSet<String> = hashSetOf()

            defaultConfig.categories.forEach {
                cat.add(it.name)
            }

            categoryCombo = ImGui.combo("", categoryChoiced, cat.toTypedArray())
            ImGui.popItemWidth()

            ImGui.setCursorPos(24F, 50F)
            ImGui.text("gui.aeternus.config.common.categories".imguiTranslate)

            ImGui.endChild()
        }

        ImGui.setCursorPos(1045F, 700F)
        if (ImGui.button("gui.aeternus.config.regen", 80F, 20F)) {
            // Send a config update packet
        }

        ImGui.setCursorPos(1140F, 700F)
        if (ImGui.button("gui.aeternus.config.save", 80F, 20F)) {
            // Send a config update packet
        }

        ImGui.setCursorPos(985F, 700F)
        if (ImGui.arrowButton("##left", ImGuiDir.Left)) {
            // Open Next
        }
        ImGui.sameLine()
        if (ImGui.arrowButton("##right", ImGuiDir.Right)) {
            // Open Next
        }

        ImGui.setCursorPos(993F, 685F)
        ImGui.text("gui.aeternus.config.next".imguiTranslate)

        ImGui.setCursorPos(495.5F, 350F)
        ImGui.text("gui.aeternus.config.welcome".imguiTranslate)
    }

    ImGui.end()
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
