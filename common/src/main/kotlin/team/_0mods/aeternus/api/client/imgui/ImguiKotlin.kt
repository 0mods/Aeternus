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
import imgui.flag.ImGuiTreeNodeFlags
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation

inline fun begin(text: String = "", params: Int = 0, action: () -> Unit) {
    if(ImGui.begin(text, params)) {
        action()
    }
    ImGui.end()
}

fun setWindowSize(width: Float, height: Float) {
    ImGui.setWindowSize(width, height)
}

fun setWindowPos(x: Float, y: Float) {
    ImGui.setWindowPos(x, y)
}

inline fun treeNode(text: String = "", params: Int = 0, action: () -> Unit) {
    if(ImGui.treeNodeEx(text, params)) {
        action()
        if(params and ImGuiTreeNodeFlags.SpanFullWidth == 0) ImGui.treePop()
    }
}

fun image(texture: ResourceLocation, width: Float, height: Float) {
    ImGui.image(Minecraft.getInstance().textureManager.getTexture(texture).id, width, height)
}
