/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.metadata.MetadataSectionSerializer
import net.minecraft.server.packs.repository.Pack
import net.minecraft.server.packs.resources.IoSupplier
import team._0mods.aeternus.api.util.rl
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.function.BooleanSupplier

class AutomaticPackResources private constructor(): PackResources {
    companion object {
        private val genSounds: MutableMap<String, JsonObject> = hashMapOf()

        var genItemModels: MutableList<ResourceLocation> = arrayListOf()

        var genBlockData: MutableList<ResourceLocation> = arrayListOf()

        private var genParticles: MutableList<ResourceLocation> = arrayListOf()

        private var packInstance: AutomaticPackResources? = null

        val resourceMap: MutableMap<ResourceLocation, IResourceStreamSupplier> = hashMapOf()

        private fun ofText(text: String) = IResourceStreamSupplier.create({ true }) { text.byteInputStream() }

        @JvmStatic
        fun packInstance(): AutomaticPackResources {
            if (packInstance == null) packInstance = AutomaticPackResources()
            packInstance!!.init()
            return packInstance!!
        }
    }

    fun generatePostShader(loc: ResourceLocation) {
        resourceMap["${loc.namespace}:shaders/post/${loc.path}.json".rl] =
            ofText("{\"targets\": [\"swap\"],\"passes\": [{\"name\": \"$loc\",\"intarget\": \"minecraft:main\",\"outtarget\": \"swap\",\"uniforms\": []},{\"name\": \"$loc\",\"intarget\": \"swap\",\"outtarget\": \"minecraft:main\",\"uniforms\": []}]}")
        resourceMap["${loc.namespace}:shaders/program/${loc.path}.json".rl] =
            ofText("{\"blend\":{\"func\":\"add\",\"srcrgb\":\"one\",\"dstrgb\":\"zero\"},\"vertex\":\"sobel\",\"fragment\":\"$loc\",\"attributes\":[\"Position\"],\"samplers\":[{\"name\":\"DiffuseSampler\"}],\"uniforms\":[{\"name\":\"ProjMat\",\"type\":\"matrix4x4\",\"count\":16,\"values\":[1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0]},{\"name\":\"InSize\",\"type\":\"float\",\"count\":2,\"values\":[1.0,1.0]},{\"name\":\"OutSize\",\"type\":\"float\",\"count\":2,\"values\":[1.0,1.0]},{\"name\":\"Time\",\"type\":\"float\",\"count\":1,\"values\":[0.0]}]}")
    }

    private fun addItemModel(location: ResourceLocation) {
        val modelLocation = ResourceLocation(location.namespace, "models/item/" + location.path + ".json")
        resourceMap[modelLocation] =
            ofText("{\"parent\":\"item/handheld\",\"textures\":{\"layer0\":\"" + location.namespace + ":items/" + location.path + "\"}}")
    }

    private fun addParticleModel(location: ResourceLocation) {
        val particle = ResourceLocation(location.namespace, "particles/" + location.path + ".json")
        resourceMap[particle] = ofText("{\"textures\":[\"$location\"]}")
    }

    private fun addBlockModel(location: ResourceLocation) {
        val blockstate = ResourceLocation(location.namespace, "blockstates/" + location.path + ".json")
        val model = ResourceLocation(location.namespace, "models/item/" + location.path + ".json")
        resourceMap[blockstate] =
            ofText("{\"variants\":{\"\":{\"model\":\"" + location.namespace + ":item/" + location.path + "\"}}}")
        resourceMap[model] =
            ofText("{\"parent\":\"block/cube_all\",\"textures\":{\"all\":\"" + location.namespace + ":blocks/" + location.path + "\"}}")
    }

    private fun addSoundJson(modid: String, sound: JsonObject) {
        resourceMap[ResourceLocation(modid, "sounds.json")] = ofText(sound.toString())
    }

    fun init() {
        genItemModels.forEach(::addItemModel)
        genParticles.forEach(::addParticleModel)
        genBlockData.forEach(::addBlockModel)
        genSounds.entries.forEach { addSoundJson(it.key, it.value) }

        genItemModels.clear()
        genParticles.clear()
        genBlockData.clear()
        genSounds.clear()
    }

    fun resourceSupplier() = resourceSupplier(this)

    private fun resourceSupplier(resources: PackResources) = object : Pack.ResourcesSupplier {
        override fun openPrimary(id: String): PackResources = resources

        override fun openFull(id: String, info: Pack.Info): PackResources = resources
    }

    override fun close() {}

    @Throws(IOException::class)
    override fun getRootResource(vararg elements: String?): IoSupplier<InputStream> = throw FileNotFoundException()

    @Throws(IOException::class)
    override fun getResource(packType: PackType, location: ResourceLocation): IoSupplier<InputStream> =
        IoSupplier { resourceMap[location]!!.create() }

    override fun listResources(
        packType: PackType,
        namespace: String,
        path: String,
        resourceOutput: PackResources.ResourceOutput
    ) {}

    override fun getNamespaces(type: PackType): MutableSet<String> {
        val hSet = hashSetOf<String>()
        resourceMap.keys.forEach { hSet.add(it.namespace) }
        return hSet
    }

    override fun <T : Any?> getMetadataSection(deserializer: MetadataSectionSerializer<T>): T? {
        if (deserializer.metadataSectionName.equals("pack")) {
            val obj = JsonObject()
            val supportedFormats = JsonArray()
            val allValues = 6..22
            allValues.forEach(supportedFormats::add)
            obj.addProperty("pack_format", allValues.last)
            obj.add("supported_formats", supportedFormats)
            obj.addProperty("description", "Autogenerated resources for Aeternus")

            return deserializer.fromJson(obj)
        }
        return null
    }

    override fun packId(): String = "Aeternus Resources"

    interface IResourceStreamSupplier {
        companion object {
            fun create(exists: BooleanSupplier, stream: IIOSupplier<InputStream>): IResourceStreamSupplier = object : IResourceStreamSupplier {
                override fun exists(): Boolean = exists.asBoolean

                override fun create(): InputStream = stream()
            }
        }

        fun exists(): Boolean

        @Throws(IOException::class)
        fun create(): InputStream
    }

    fun interface IIOSupplier<T> : () -> T {
        @Throws(IOException::class)
        override fun invoke(): T
    }
}