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

import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.magic.spell.Spell

interface SpellRegistry {
    val spells: List<Spell>

    fun getById(id: ResourceLocation): Spell?

    fun getId(spell: Spell): ResourceLocation

    fun <T: Spell> register(id: String, spell: T): T

    @ApiStatus.Experimental
    fun <T: Spell> register(id: ResourceLocation, spell: T): T

    fun registerAll(vararg spells: Pair<String, Spell>) {
        spells.forEach {
            val id = it.first
            val research = it.second
            register(id, research)
        }
    }

    @ApiStatus.Experimental
    fun registerAllRl(vararg spells: Pair<ResourceLocation, Spell>) {
        spells.forEach {
            val id = it.first
            val research = it.second
            register(id, research)
        }
    }

    fun getByIdList(id: List<ResourceLocation>): List<Spell>
}
