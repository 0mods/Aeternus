/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.common.world

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.biome.Biome
import team._0mods.multilib.util.resloc

object BiomeRegistryHolder {
    lateinit var biomeRegistry: Registry<Biome>

    @JvmStatic
    fun loadBiomeReg(server: MinecraftServer) {
        biomeRegistry = server.registryAccess().registryOrThrow(Registries.BIOME)
    }

    @JvmStatic
    fun Int.idToRL() = (if (this == -1) resloc("a", "empty") else biomeRegistry.getHolder(this).get().key().location())!!

    fun ResourceLocation.rlToId() = biomeRegistry.getId(biomeRegistry.get(this))
}
