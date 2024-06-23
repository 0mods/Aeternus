/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.registry

import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.api.util.mcemulate.MCResourceLocation

interface ResearchRegistry {
    /**
     * List of registered researches.
     * Is not mutable!
     *
     * Returns [List] of [Research]es
     */
    val researches: List<Research>

    fun getById(id: MCResourceLocation): Research?

    fun getId(research: Research): MCResourceLocation

    fun <T: Research> register(id: String, research: T): T

    @ApiStatus.Experimental
    fun <T: Research> register(id: MCResourceLocation, research: T): T

    fun registerAll(vararg researches: Pair<String, Research>) {
        researches.forEach {
            val id = it.first
            val research = it.second
            register(id, research)
        }
    }

    @ApiStatus.Experimental
    fun registerAllRl(vararg researches: Pair<MCResourceLocation, Research>) {
        researches.forEach {
            val id = it.first
            val research = it.second
            register(id, research)
        }
    }

    fun getByIdList(id: List<MCResourceLocation>): List<Research>
}
