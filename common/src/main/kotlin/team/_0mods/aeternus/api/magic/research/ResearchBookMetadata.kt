/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic.research

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

interface ResearchBookMetadata {
    companion object

    /**
     * Translatable Research name.
     *
     * Returns [Component]
     */
    val title: Component

    /**
     * Translatable Research description.
     *
     * Returns [Component]
     */
    val desc: Component

    /**
     * Research texture.
     *
     * Returns [ResourceLocation]
     */
    val icon: ResourceLocation

    /**
     * Research position on a book
     *
     * Returns [Pair] of Integer and Integer, where first Integer - x pos, but second Integer - y pos.
     */
    val position: Pair<Int, Int>
}
