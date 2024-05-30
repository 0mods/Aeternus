/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:JvmName("RLUtils")

package team._0mods.aeternus.api.util

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.vehicle.Minecart
import team._0mods.aeternus.common.ModId
import java.io.FileNotFoundException
import java.io.InputStream

class DoubleResourceLocation(var id: ResourceLocation, val value: ResourceLocation) {
    companion object {
        private fun decompose(id: String, sep: Char): Array<String> {
            val strings = arrayOf("minecraft", id, "minecraft", "air")

            val i = id.indexOf(sep)

            if (i >= 0) {
                strings[1] = id.substring(i + 1)
                if (i == 1) {
                    strings[0] = id.substring(0)
                } else if (i == 2) {
                    strings[2] = id.substring(2)
                } else if (i >= 3) {
                    strings[3] = id.substring(3, i)
                }
            }

            return strings
        }
    }

    private constructor(decomposeLocation: Array<String>) : this("${decomposeLocation[0]}:${decomposeLocation[1]}".rl, "${decomposeLocation[2]}:${decomposeLocation[3]}".rl)

    constructor(location: String): this(decompose(location, ':'))
}

fun resloc(id: String, path: String) = ResourceLocation(id, path)

val String.aRl: ResourceLocation
    get() = "$ModId:$this".rl

val String.rl: ResourceLocation
    get() = ResourceLocation(this)

val ResourceLocation.stream: InputStream
    get() = try {
        Minecraft.getInstance().resourceManager.getResource(this).orElseThrow().open()
    } catch (e: FileSystemException) {
        Thread.currentThread().contextClassLoader.getResourceAsStream("assets/${this.namespace}/${this.path}") ?: throw FileNotFoundException("Resource $this not found!")
    }

val ResourceLocation.texture: AbstractTexture
    get() = Minecraft.getInstance().textureManager.getTexture(this)

val ResourceLocation.exists: Boolean
    get() = Minecraft.getInstance().resourceManager.getResource(this).isPresent
