/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.client.models.manager

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceManagerReloadListener
import org.lwjgl.opengl.*
import team._0mods.aeternus.api.client.models.*
import team._0mods.aeternus.api.client.textures.GIF_TEXTURES
import team._0mods.aeternus.api.client.textures.GlTexture
import team._0mods.aeternus.api.util.rl
import team._0mods.aeternus.api.util.stream
import java.nio.ByteBuffer
import java.nio.ByteOrder

object GltfManager : ResourceManagerReloadListener {
    lateinit var lightTexture: AbstractTexture
    private val models = HashMap<ResourceLocation, GltfModel>()
    var glProgramSkinning = -1

    fun getOrCreate(location: ResourceLocation) = models.computeIfAbsent(location) { model ->
        GltfModel(GltfTree.parse(model))
    }

    private fun createSkinningProgramGL33() {
        val glShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER)
        GL20.glShaderSource(glShader, ("shaders/core/gltf_skinning.vsh").rl.stream.readBytes().decodeToString())
        GL20.glCompileShader(glShader)

        glProgramSkinning = GL20.glCreateProgram()
        GL20.glAttachShader(glProgramSkinning, glShader)
        GL20.glDeleteShader(glShader)
        GL30.glTransformFeedbackVaryings(
            glProgramSkinning,
            arrayOf<CharSequence>("outPosition", "outNormal"),
            GL30.GL_SEPARATE_ATTRIBS
        )
        GL20.glLinkProgram(glProgramSkinning)
    }

    override fun onResourceManagerReload(manager: ResourceManager) {
        models.values.forEach { it.destroy() }
        models.clear()

        manager.listResources("models") { it.path.endsWith(".gltf") or it.path.endsWith(".glb") }.keys
            .forEach { getOrCreate(it) }

        GIF_TEXTURES.forEach { it.value.releaseId() }
        GIF_TEXTURES.clear()
    }

    fun initialize() {
        val textureManager = Minecraft.getInstance().getTextureManager()

        lightTexture = textureManager.getTexture(ResourceLocation("dynamic/light_map_1"))

        val currentTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)

        val defaultColorMap = GL11.glGenTextures()
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, defaultColorMap)
        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 2, 2, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, create(
                byteArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)
            )
        )
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 0)

        val defaultNormalMap = GL11.glGenTextures()
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, defaultNormalMap)
        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 2, 2, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, create(
                byteArrayOf(-128, -128, -1, -1, -128, -128, -1, -1, -128, -128, -1, -1, -128, -128, -1, -1)
            )
        )
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 0)

        textureManager.register("hc:default_color_map".rl, GlTexture(defaultColorMap))
        textureManager.register("hc:default_normal_map".rl, GlTexture(defaultNormalMap))
        textureManager.register("hc:default_specular_map".rl, GlTexture(0))

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture)

        createSkinningProgramGL33()
    }
}

fun create(data: ByteArray): ByteBuffer {
    return create(data, 0, data.size)
}

fun create(data: ByteArray?, offset: Int, length: Int): ByteBuffer {
    val byteBuffer = ByteBuffer.allocateDirect(length)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    byteBuffer.put(data, offset, length)
    byteBuffer.position(0)
    return byteBuffer
}