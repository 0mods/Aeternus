/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.impl.registry

import team._0mods.aeternus.platformredirect.api.item.SpellScroll
import team._0mods.aeternus.api.magic.spell.Spell
import team._0mods.aeternus.api.registry.SpellRegistry
import team._0mods.aeternus.api.util.*
import team._0mods.aeternus.platformredirect.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper

class SpellRegistryImpl(private val modId: String): SpellRegistry {
    init {
        LOGGER.debugIfEnabled("Initializing Spell Registry for mod id '$modId'")
    }

    companion object {
        internal val spellMap: MutableMap<APIResourceLocation, Spell> = linkedMapOf()
        @get:JvmStatic
        internal val scrolls: MutableList<SpellScroll> = mutableListOf()
    }

    override val spells: List<Spell>
        get() = spellMap.values.toList()

    override fun getById(id: APIResourceLocation): Spell = spellMap[id] ?: throw NullPointerException("Spell with id \"$id\" is not found! Make sure that a spell with that id is actually there.")

    override fun getId(spell: Spell): APIResourceLocation = spellMap.revert()[spell] ?: throw NullPointerException("Spell \"$spell\" is not have an identifier. Why?")

    override fun <T : Spell> register(id: String, spell: T): T {
        val rlId = APIResourceLocation.createRL(modId, id)
        return this.register(rlId, spell)
    }

    override fun <T : Spell> register(id: APIResourceLocation, spell: T): T {
        LOGGER.debugIfEnabled("Registering spell with id '$id'")

        if (spellMap.keys.stream().noneMatch { it == id })
            spellMap[id] = spell
        else
            LOGGER.warn(
                "Oh... Mod: {} trying to register a spell with id {}, because spell with this id is already registered! Skipping...",
                PlatformHelper.getModNameByModId(id.rlNamespace),
                id
            )

        return spell
    }

    override fun getByIdList(id: List<APIResourceLocation>): List<Spell> = spellMap.fromMapToListByList(id)
}
