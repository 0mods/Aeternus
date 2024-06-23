/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.impl.research

import team._0mods.aeternus.api.magic.research.book.ResearchAlignment
import team._0mods.aeternus.api.magic.research.book.ResearchBookMetadata
import team._0mods.aeternus.api.magic.research.book.ResearchShape
import team._0mods.aeternus.api.util.Text
import team._0mods.aeternus.api.util.mcemulate.MCResourceLocation

internal class ResearchBookMetadataImpl(
    override val title: Text,
    override val desc: Text,
    override val icon: MCResourceLocation,
    override val offset: Pair<Int, Int>,
    override val alignment: ResearchAlignment,
    override val shape: ResearchShape
) : ResearchBookMetadata
