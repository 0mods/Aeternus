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

import team._0mods.aeternus.api.magic.spell.Spell
import team._0mods.aeternus.api.registry.SpellRegistry
import team._0mods.aeternus.api.util.debugIfEnabled
import team._0mods.aeternus.api.util.fromMapToListByList
import team._0mods.aeternus.api.util.mcemulate.MCResourceLocation
import team._0mods.aeternus.api.util.mcemulate.createRL
import team._0mods.aeternus.api.util.revert
import team._0mods.aeternus.platformredirect.api.item.SpellScroll
import team._0mods.aeternus.platformredirect.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper
import kotlin.collections.set

class SpellRegistryImpl(private val modId: String): SpellRegistry {
    init {
        LOGGER.debugIfEnabled("Initializing Spell Registry for mod id '$modId'")
    }

    companion object {
        internal val spellMap: MutableMap<MCResourceLocation, Spell> = linkedMapOf()
        @get:JvmStatic
        internal val scrolls: MutableList<SpellScroll> = mutableListOf()
    }

    override val spells: List<Spell>
        get() = spellMap.values.toList()

    override fun getById(id: MCResourceLocation): Spell = spellMap[id] ?: throw NullPointerException("Spell with id \"$id\" is not found! Make sure that a spell with that id is actually there.")

    override fun getId(spell: Spell): MCResourceLocation = spellMap.revert()[spell] ?: throw NullPointerException("Spell \"$spell\" is not have an identifier. Why?")

    override fun <T : Spell> register(id: String, spell: T): T {
        val rlId = MCResourceLocation.createRL(modId, id)
        return this.register(rlId, spell)
    }

    override fun <T : Spell> register(id: MCResourceLocation, spell: T): T {
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

    override fun getByIdList(id: List<MCResourceLocation>): List<Spell> = spellMap.fromMapToListByList(id)
}
