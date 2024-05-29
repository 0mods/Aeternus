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

import imgui.ImFont
import imgui.ImFontConfig
import imgui.ImFontGlyphRangesBuilder
import imgui.ImGui
import imgui.extension.imnodes.ImNodes
import imgui.flag.ImGuiBackendFlags
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiConfigFlags
import imgui.flag.ImGuiFreeTypeBuilderFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import net.minecraft.client.Minecraft
import org.lwjgl.glfw.GLFW
import team._0mods.aeternus.api.util.aRl
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.api.util.stream

object ImguiHandler {
    val imGuiGlfw = ImGuiImplGlfw()
    val imGuiGl3 = ImGuiImplGl3()
    var windowHandle: Long = 0
    val FONTS = hashMapOf<Int, ImFont>()

    fun onGlfwInit(handle: Long) {
        initializeImGui(handle)
        imGuiGlfw.init(handle, true)
        if (!Minecraft.ON_OSX) {
            imGuiGl3.init("#version 410")
        } else {
            imGuiGl3.init("#version 120")
        }

        ImNodes.createContext()
        ImGui.styleColorsDark()
        windowHandle = handle
    }

    fun drawFrame(renderable: Renderable) {
        imguiBuffer.clear(Minecraft.ON_OSX)
        Minecraft.getInstance().mainRenderTarget.bindWrite(true)
        imGuiGlfw.newFrame()
        ImGui.newFrame()
        ImGui.setNextWindowViewport(ImGui.getMainViewport().id)

        ImGui.pushFont(FONTS[Minecraft.getInstance().window.guiScale.toInt().coerceAtMost(6)])

        renderable.theme?.preRender()
        renderable.render()
        renderable.theme?.postRender()

        ImGui.popFont()

        ImGui.render()
        endFrame()

        buffers.forEach { it.destroyBuffers() }
        DockingHelper.DOCKING_ID = 0
    }

    private fun initializeImGui(glHandle: Long) {
        ImGui.createContext()

        val io = ImGui.getIO()
        io.iniFilename = null
        io.addBackendFlags(ImGuiBackendFlags.HasSetMousePos)
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard); // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable); // Enable Docking
        io.configViewportsNoTaskBarIcon = true

        val fontAtlas = io.fonts
        val fontConfig = ImFontConfig()
        val rangesBuilder = ImFontGlyphRangesBuilder().apply {
            addRanges(fontAtlas.glyphRangesDefault)
            addRanges(fontAtlas.glyphRangesCyrillic)
            addRanges(fontAtlas.glyphRangesJapanese)
            addRanges(FontAwesomeIcons._IconRange)
        }
        fontConfig.oversampleH = 1
        fontConfig.oversampleV = 1
        fontConfig.fontBuilderFlags = ImGuiFreeTypeBuilderFlags.LoadColor

        val ranges = rangesBuilder.buildRanges()

        fun loadFont(i: Int, size: Float) {
            FONTS[i] = fontAtlas.addFontFromMemoryTTF(
                "fonts/noto/notosans.ttf".aRl.stream.readAllBytes(), size, fontConfig, ranges
            )
            fontConfig.mergeMode = true
            fontAtlas.addFontFromMemoryTTF("fonts/fa_regular.ttf".aRl.stream.readAllBytes(), size, fontConfig, ranges)
            fontAtlas.addFontFromMemoryTTF("fonts/fa_solid.ttf".aRl.stream.readAllBytes(), size, fontConfig, ranges)
            fontConfig.mergeMode = false
        }

        loadFont(6, 64f)
        loadFont(5, 48f)
        loadFont(4, 40f)
        loadFont(3, 30f)
        loadFont(2, 20f)
        loadFont(1, 12f)

        fontConfig.pixelSnapH = true
        fontConfig.destroy()

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val style = ImGui.getStyle()
            style.windowRounding = 0.0f
            style.setColor(ImGuiCol.WindowBg, ImGui.getColorU32(ImGuiCol.WindowBg, 1f))
        }
    }

    fun endFrame() {
        imGuiGl3.renderDrawData(ImGui.getDrawData())
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val backupWindowPtr = GLFW.glfwGetCurrentContext()
            ImGui.updatePlatformWindows()
            ImGui.renderPlatformWindowsDefault()
            GLFW.glfwMakeContextCurrent(backupWindowPtr)
        }
    }
}
