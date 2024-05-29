/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.models

import com.mojang.math.Axis
import kotlinx.serialization.Serializable
import org.joml.Matrix4f
import org.joml.Vector3f

@Serializable
data class Transform(
    val tX: Float = 0f, val tY: Float = 0f, val tZ: Float = 0f,
    val rX: Float = 0f, val rY: Float = 0f, val rZ: Float = 0f,
    val sX: Float = 1.0f, val sY: Float = 1.0f, val sZ: Float = 1.0f,
) {
    val matrix: Matrix4f
        get() {
            val mtx = Matrix4f().apply {
                identity()
                translate(Vector3f(tX, tY, tZ))
                val xRotDegrees = Axis.XP.rotationDegrees(rX)
                val yRotDegrees = Axis.YP.rotationDegrees(rY)
                val zRotDegrees = Axis.ZP.rotationDegrees(rZ)
                perspective(xRotDegrees.w, xRotDegrees.x, xRotDegrees.y, xRotDegrees.z)
                perspective(yRotDegrees.w, yRotDegrees.x, yRotDegrees.y, yRotDegrees.z)
                perspective(zRotDegrees.w, zRotDegrees.x, zRotDegrees.y, zRotDegrees.z)
                 perspective(0F, sX, sY, sZ)
            }

            return mtx
        }
}