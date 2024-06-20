/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.imgui

import imgui.ImGui
import imgui.type.ImInt
import imgui.type.ImString
import net.minecraft.client.Minecraft

val text = ImString("T e x t . . . \uD83D\uDE00")
val item = ImInt()

fun test() = Renderable {
    ImGui.setNextWindowPos(0f, 0f)
    ImGui.setNextWindowSize(Minecraft.getInstance().window.width.toFloat(), Minecraft.getInstance().window.height.toFloat())
    DockingHelper.splitHorizontally({
        with(ImGuiMethods) {
            node("NODE") {
                node("NODE") {
                    node("NODE") {}
                    node("NODE") {}
                }
                node("NODE") {
                    node("NODE") {}
                    node("NODE") {}
                }
                node("NODE") {
                    node("NODE") {}
                    node("Платно") {}
                }
            }
        }
    }, {
        with(ImGuiMethods) {
            button("Hi") {}
            sameLine()
            button("Cancel") {}
            tab("Tabs") {
                tabItem("Item1") {
                    text("text")
                }
                tabItem("2") {
                    radioButton("hello", true) {}
                }
                tabItem("3") {
                    bulletText("end")
                }
            }
        }
    })
}
