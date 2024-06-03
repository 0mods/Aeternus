/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.impl.research.reload

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.PreparableReloadListener
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.profiling.ProfilerFiller
import team._0mods.aeternus.api.impl.research.json.*
import team._0mods.aeternus.api.registry.ResearchRegistry
import team._0mods.aeternus.common.LOGGER
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

class ResearchReloadListener(private val registry: ResearchRegistry): PreparableReloadListener {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun reload(
        preparationBarrier: PreparableReloadListener.PreparationBarrier,
        resourceManager: ResourceManager,
        preparationsProfiler: ProfilerFiller,
        reloadProfiler: ProfilerFiller,
        backgroundExecutor: Executor,
        gameExecutor: Executor
    ): CompletableFuture<Void> {
        @Suppress("UNCHECKED_CAST")
        return CompletableFuture.supplyAsync({
            resourceManager.listResources("researches", ::endWithSuf).forEach {
                val id = it.key
                val resource = it.value
                val research = json.decodeFromStream(JSONResearch.serializer(), resource.open())
                registry.register(id, research.asResearch)
                LOGGER.debug("Research with id {} is registered!", id)
            }
        }, backgroundExecutor) as CompletableFuture<Void>
    }

    override fun getName(): String = "Aeternus JSON Research Listener"

    private fun endWithSuf(rl: ResourceLocation): Boolean = rl.path.endsWith(".json")
}
