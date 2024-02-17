/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.gui.widget

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource.BufferSource

abstract class BookWidget(
    @Expose
    @SerializedName("page")
    val displayPage: Int,
    @Expose
    val type: Type,
    @Expose
    val x: Int,
    @Expose
    val y: Int,
    @Expose
    val scale: Float
) {
    abstract fun render(pose: PoseStack, buffer: BufferSource, partialTick: Float, onFlip: Boolean)

    enum class Type(val widget: Class<out BookWidget>)
}
