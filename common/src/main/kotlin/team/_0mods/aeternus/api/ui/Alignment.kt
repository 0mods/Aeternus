/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.ui

enum class Alignment(override val factorX: Float, override val factorY: Float): IPlacement {
    BOTTOM_CENTER(0.5F, 1.0F),
    BOTTOM_RIGHT(1F, 1.0F),
    BOTTOM_LEFT(0F, 1.0F),

    CENTER(0.5F, 0.5F),
    RIGHT_CENTER(1F, 0.5F),
    LEFT_CENTER(0F, 0.5F),

    TOP_CENTER(0.5F, 0F),
    TOP_RIGHT(1F, 0F),
    TOP_LEFT(0F, 0F);

    fun factorX() = factorX
    fun factorY() = factorY
}


enum class Anchor(val factor: Float) {
    START(0f),
    CENTER(0.5f),
    END(1f)
}


interface IPlacement {
    val factorX: Float
    val factorY: Float
}
