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

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import team._0mods.aeternus.api.item.SpellScroll
import team._0mods.aeternus.api.magic.spell.Spell
import team._0mods.aeternus.api.registry.SpellRegistry
import team._0mods.aeternus.api.util.*
import team._0mods.aeternus.common.LOGGER
import team._0mods.aeternus.service.PlatformHelper

class SpellRegistryImpl(private val modId: String): SpellRegistry {
    companion object {
        private val spellMap: MutableMap<ResourceLocation, Spell> = linkedMapOf()
        @get:JvmStatic
        internal val scrolls: MutableList<SpellScroll> = mutableListOf()

        @JvmStatic
        fun onReg(e: DeferredRegister<Item>) {
            spellMap.entries.forEach {
                val reg by e.reg(it.key) { SpellScroll(it.value) }
                scrolls.add(reg)
            }
        }
    }

    override val spells: List<Spell>
        get() = spellMap.values.toList()

    override fun getById(id: ResourceLocation): Spell = spellMap[id] ?: throw NullPointerException("Spell with id \"$id\" is not found! Make sure that a spell with that id is actually there.")

    override fun getId(spell: Spell): ResourceLocation = spellMap.revert()[spell] ?: throw NullPointerException("Spell \"$spell\" is not have an identifier. Why?")

    override fun <T : Spell> register(id: String, spell: T): T {
        val rlId = "${this.modId}:$id".rl
        return this.register(rlId, spell)
    }

    override fun <T : Spell> register(id: ResourceLocation, spell: T): T {
        if (spellMap.keys.stream().noneMatch { it == id })
            spellMap[id] = spell
        else
            LOGGER.warn(
                "Oh... Mod: {} trying to register a spell with id {}, because spell with this id is already registered! Skipping...",
                PlatformHelper.getModNameByModId(id.namespace),
                id
            )

        return spell
    }

    override fun getByIdList(id: List<ResourceLocation>): List<Spell> = spellMap.fromMapToListByList(id)
}
