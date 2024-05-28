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
import imgui.extension.imguizmo.ImGuizmo
import imgui.extension.imguizmo.flag.Mode
import imgui.extension.imguizmo.flag.Operation
import org.joml.Vector4f
import kotlin.math.tan

val cameraMatrix = floatArrayOf(
    1f, 0f, 0f, 0f,
    0f, 1f, 0f, 0f,
    0f, 0f, 1f, 0f,
    0f, 0f, 0f, 1f,
)
val objectMatrix = floatArrayOf(
    1f, 0f, 0f, 0f,
    0f, 1f, 0f, 0f,
    0f, 0f, 1f, 0f,
    0f, 0f, 0f, 1f,
)

fun FloatArray.identity() {
    for (i in this.indices) {
        if (i == 0 || i == 5 || i == 10 || i == 15) this[i] = 1f
        else this[i] = 0f
    }
}

fun test() = Renderable.create {
    ImGui.setNextWindowSize(1024f, 1024f)
    centredWindow {
        val pos = Vector4f(0f, 0f, 0f, 1f)
        val u = pos.x() / pos.z() / 50
        val v = pos.y() / pos.z() / 50

        ImGuizmo.beginFrame()

        ImGuizmo.setOrthographic(false)
        ImGuizmo.setEnabled(true)
        ImGuizmo.setDrawList()

        val windowWidth = ImGui.getWindowWidth()
        val windowHeight = ImGui.getWindowHeight()
        ImGuizmo.setRect(ImGui.getWindowPosX(), ImGui.getWindowPosY() + 120F, windowWidth, windowHeight)

        val aspect = ImGui.getWindowWidth() / ImGui.getWindowHeight()
        val projectionMatrix = perspective(27f, aspect, 0.1f, 100f)

        ImGuizmo.recomposeMatrixFromComponents(
            cameraMatrix,
            floatArrayOf(0F, 0F, 0F),
            floatArrayOf(0F, 0F, 0F),
            floatArrayOf(1F, 1F, 1F)
        )

        val INPUT_MATRIX_TRANSLATION = FloatArray(3)
        val INPUT_MATRIX_SCALE = FloatArray(3)
        val INPUT_MATRIX_ROTATION = FloatArray(3)

        ImGuizmo.decomposeMatrixToComponents(
            objectMatrix,
            INPUT_MATRIX_TRANSLATION,
            INPUT_MATRIX_ROTATION,
            INPUT_MATRIX_SCALE
        )

        ImGui.dragFloat3("Tr", INPUT_MATRIX_TRANSLATION, 0.1f, -100f, 100f, "%.3f")
        ImGui.dragFloat3("Rt", INPUT_MATRIX_ROTATION, 1f, -365f, 365f, "%.3f")
        ImGui.dragFloat3("Sc", INPUT_MATRIX_SCALE, 0.1f, -100f, 100f, "%.3f")

        ImGuizmo.recomposeMatrixFromComponents(
            objectMatrix,
            floatArrayOf(u, v, -1f),
            floatArrayOf(INPUT_MATRIX_ROTATION[0], INPUT_MATRIX_ROTATION[1], INPUT_MATRIX_ROTATION[2]),
            INPUT_MATRIX_SCALE
        )

        ImGuizmo.manipulate(cameraMatrix, projectionMatrix, objectMatrix, Operation.ROTATE, Mode.LOCAL)

        val viewManipulateRight = ImGui.getWindowPosX() + windowWidth
        val viewManipulateTop = ImGui.getWindowPosY()
        ImGuizmo.drawGrid(
            cameraMatrix,
            projectionMatrix,
            floatArrayOf(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f),
            100
        )
        ImGuizmo.viewManipulate(
            cameraMatrix,
            8f,
            floatArrayOf(viewManipulateRight - 128, viewManipulateTop),
            floatArrayOf(128f, 128f),
            0x10101010
        )

        ImGuizmo.decomposeMatrixToComponents(
            objectMatrix,
            INPUT_MATRIX_TRANSLATION,
            INPUT_MATRIX_ROTATION,
            INPUT_MATRIX_SCALE
        )
    }
}

private fun perspective(fovY: Float, aspect: Float, near: Float, far: Float): FloatArray {
    val xmax: Float
    val ymax = (near * tan(fovY * Math.PI / 180.0f)).toFloat()
    xmax = ymax * aspect
    return frustum(-xmax, xmax, -ymax, ymax, near, far)
}

private fun frustum(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): FloatArray {
    val r = FloatArray(16)
    val temp = 2.0f * near
    val temp2 = right - left
    val temp3 = top - bottom
    val temp4 = far - near
    r[0] = temp / temp2
    r[1] = 0.0f
    r[2] = 0.0f
    r[3] = 0.0f
    r[4] = 0.0f
    r[5] = temp / temp3
    r[6] = 0.0f
    r[7] = 0.0f
    r[8] = (right + left) / temp2
    r[9] = (top + bottom) / temp3
    r[10] = (-far - near) / temp4
    r[11] = -1.0f
    r[12] = 0.0f
    r[13] = 0.0f
    r[14] = (-temp * far) / temp4
    r[15] = 0.0f
    return r
}
