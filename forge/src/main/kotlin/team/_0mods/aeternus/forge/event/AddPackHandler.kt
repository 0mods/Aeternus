/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.forge.event

import net.minecraft.server.packs.PackType
import net.minecraftforge.event.AddPackFindersEvent
import team._0mods.aeternus.api.event.AddPackEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object AddPackHandler {
    @JvmStatic
    fun init() {
        MOD_BUS.addListener(this::event)
    }

    private fun event(e: AddPackFindersEvent) {
        if (e.packType == PackType.CLIENT_RESOURCES) {
            e.addRepositorySource {
                AddPackEvent.ASSETS.invoker().onAdd(it, AddPackEvent.PackCreator())
            }
        } else {
            e.addRepositorySource {
                AddPackEvent.DATA.invoker().onAdd(it, AddPackEvent.PackCreator())
            }
        }
    }
}