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

fun Renderable.Companion.create(name: String? = null, theme: Theme? = null, renderable: () -> Unit): Renderable = object : Renderable {
    override val name: String?
        get() = name

    override val theme: Theme?
        get() = theme

    override fun render() {
        renderable()
    }
}

fun interface Renderable {
    companion object

    val name: String?
        get() = null

    val theme: Theme?
        get() = null

    fun render()
}

interface Theme {
    fun preRender()

    fun postRender()
}
