/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic.research.book

import team._0mods.aeternus.api.util.Text
import team._0mods.aeternus.api.util.mcemulate.MCResourceLocation

interface ResearchBookMetadata {
    companion object

    /**
     * Translatable Research name.
     *
     * Returns [Text]
     */
    val title: Text

    /**
     * Translatable Research description.
     *
     * Returns [Text]
     */
    val desc: Text

    /**
     * Research texture.
     *
     * Returns [MCResourceLocation]
     */
    val icon: MCResourceLocation

    /**
     * Research offset on a book
     *
     * Returns [Pair] of Integer and Integer, where first Integer - x pos, but second Integer - y pos.
     */
    val offset: Pair<Int, Int>
        get() = 0 to 0
    /**
     * Research alignment on a book
     *
     * Returns [ResearchAlignment]
     */
    val alignment: ResearchAlignment

    /**
     * Research shape on a book
     *
     * Returns [ResearchShape]
     */
    val shape: ResearchShape
        get() = ResearchShape.CIRCLE
}
