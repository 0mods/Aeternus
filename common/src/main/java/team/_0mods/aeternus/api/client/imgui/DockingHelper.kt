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
import imgui.ImGuiWindowClass
import imgui.internal.ImGui as ImGuiInternal
import imgui.internal.flag.ImGuiDockNodeFlags as ImGuiDockNodeFlagsInternal
import imgui.flag.ImGuiDir
import imgui.flag.ImGuiDockNodeFlags
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import net.minecraft.client.Minecraft

object DockingHelper {
    var DOCKING_ID = 0

    fun splitHorizontally(left: () -> Unit = {}, right: () -> Unit = {}, ratio: Float = 0.5f) {
        ImGui.setNextWindowPos(0f, 0f)
        val window = Minecraft.getInstance().window
        ImGui.setNextWindowSize(window.width.toFloat(), window.height.toFloat())
        val shouldDrawWindowContents = ImGui.begin(
            "DockingWindow",
            ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoTitleBar
        )
        val dockspaceID = ImGui.getID("MyWindow_DockSpace_${DOCKING_ID++}")
        val workspaceWindowClass = ImGuiWindowClass()
        workspaceWindowClass.setClassId(dockspaceID)
        workspaceWindowClass.dockingAllowUnclassed = false

        if (ImGuiInternal.dockBuilderGetNode(dockspaceID).ptr == 0L) {
            ImGuiInternal.dockBuilderAddNode(
                dockspaceID, ImGuiDockNodeFlagsInternal.DockSpace or
                        ImGuiDockNodeFlagsInternal.NoWindowMenuButton or
                        ImGuiDockNodeFlagsInternal.NoCloseButton
            )
            val region = ImGui.getContentRegionAvail()
            ImGuiInternal.dockBuilderSetNodeSize(dockspaceID, region.x, region.y)

            val leftDockID = ImInt(0)
            val rightDockID = ImInt(0)
            ImGuiInternal.dockBuilderSplitNode(dockspaceID, ImGuiDir.Left, ratio, leftDockID, rightDockID);

            val pLeftNode = ImGuiInternal.dockBuilderGetNode(leftDockID.get())
            val pRightNode = ImGuiInternal.dockBuilderGetNode(rightDockID.get())
            pLeftNode.localFlags = pLeftNode.localFlags or ImGuiDockNodeFlagsInternal.NoTabBar or
                    ImGuiDockNodeFlagsInternal.NoDockingSplitMe or ImGuiDockNodeFlagsInternal.NoDockingOverMe or
                    ImGuiDockNodeFlagsInternal.NoTabBar
            pRightNode.localFlags = pRightNode.localFlags or ImGuiDockNodeFlagsInternal.NoTabBar or
                    ImGuiDockNodeFlagsInternal.NoDockingSplitMe or ImGuiDockNodeFlagsInternal.NoDockingOverMe or
                    ImGuiDockNodeFlagsInternal.NoTabBar

            // Dock windows
            ImGuiInternal.dockBuilderDockWindow("##LeftPanel", leftDockID.get())
            ImGuiInternal.dockBuilderDockWindow("##RightPanel", rightDockID.get())

            ImGuiInternal.dockBuilderFinish(dockspaceID)
        }

        val dockFlags = if (shouldDrawWindowContents) ImGuiDockNodeFlags.None
        else ImGuiDockNodeFlags.KeepAliveOnly
        val region = ImGui.getContentRegionAvail()
        ImGui.dockSpace(dockspaceID, region.x, region.y, dockFlags, workspaceWindowClass)
        ImGui.end()

        val windowClass = ImGuiWindowClass()
        windowClass.dockNodeFlagsOverrideSet = ImGuiDockNodeFlagsInternal.NoTabBar

        ImGui.setNextWindowClass(windowClass)

        ImGui.begin("##LeftPanel")
        left()
        ImGui.end()

        ImGui.begin("##RightPanel")
        right()
        ImGui.end()
    }

    fun splitVertically(up: () -> Unit = {}, down: () -> Unit = {}, ratio: Float = 0.5f) {
        val window = Minecraft.getInstance().window
        val size = ImGui.getContentRegionMax()
        ImGui.setNextWindowSize(size.x, size.y)
        val shouldDrawWindowContents = ImGui.begin(
            "DockingWindow",
            ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoTitleBar
        )
        val dockspaceID = ImGui.getID("MyWindow_DockSpace_${DOCKING_ID++}")
        val workspaceWindowClass = ImGuiWindowClass()
        workspaceWindowClass.setClassId(dockspaceID)
        workspaceWindowClass.dockingAllowUnclassed = false

        if (ImGuiInternal.dockBuilderGetNode(dockspaceID).ptr == 0L) {
            ImGuiInternal.dockBuilderAddNode(
                dockspaceID, ImGuiDockNodeFlagsInternal.DockSpace or
                        ImGuiDockNodeFlagsInternal.NoWindowMenuButton or
                        ImGuiDockNodeFlagsInternal.NoCloseButton
            )
            val region = ImGui.getContentRegionAvail()
            ImGuiInternal.dockBuilderSetNodeSize(dockspaceID, size.x, size.y)

            val leftDockID = ImInt(0)
            val rightDockID = ImInt(0)
            ImGuiInternal.dockBuilderSplitNode(dockspaceID, ImGuiDir.Up, ratio, leftDockID, rightDockID);

            val pLeftNode = ImGuiInternal.dockBuilderGetNode(leftDockID.get())
            val pRightNode = ImGuiInternal.dockBuilderGetNode(rightDockID.get())
            pLeftNode.localFlags = pLeftNode.localFlags or ImGuiDockNodeFlagsInternal.NoTabBar or
                    ImGuiDockNodeFlagsInternal.NoDockingSplitMe or ImGuiDockNodeFlagsInternal.NoDockingOverMe or
                    ImGuiDockNodeFlagsInternal.NoTabBar
            pRightNode.localFlags = pRightNode.localFlags or ImGuiDockNodeFlagsInternal.NoTabBar or
                    ImGuiDockNodeFlagsInternal.NoDockingSplitMe or ImGuiDockNodeFlagsInternal.NoDockingOverMe or
                    ImGuiDockNodeFlagsInternal.NoTabBar

            // Dock windows
            ImGuiInternal.dockBuilderDockWindow("##UpPanel", leftDockID.get())
            ImGuiInternal.dockBuilderDockWindow("##DownPanel", rightDockID.get())

            ImGuiInternal.dockBuilderFinish(dockspaceID)
        }

        val dockFlags = if (shouldDrawWindowContents) ImGuiDockNodeFlags.None
        else ImGuiDockNodeFlags.KeepAliveOnly
        val region = ImGui.getContentRegionAvail()
        ImGui.dockSpace(dockspaceID, region.x, region.y, dockFlags, workspaceWindowClass)
        ImGui.end()

        val windowClass = ImGuiWindowClass()
        windowClass.dockNodeFlagsOverrideSet = ImGuiDockNodeFlagsInternal.NoTabBar

        ImGui.setNextWindowClass(windowClass)

        ImGui.begin("##UpPanel")
        up()
        ImGui.end()

        ImGui.begin("##DownPanel")
        down()
        ImGui.end()
    }
}
