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

import team._0mods.aeternus.api.item.ToolMaterialCreation
import team._0mods.aeternus.api.item.material.Material
import team._0mods.aeternus.api.item.material.MaterialBuilder

interface MaterialRegistry {
    val materialBuilder: ToolMaterialCreation.Builder
        get() = ToolMaterialCreation.builder

    fun create(id: String): MaterialBuilder

    fun createAndBuild(id: String, builder: (MaterialBuilder) -> Unit): Material

    fun isRegistered(id: String): Boolean

    fun isRegistered(material: Material): Boolean
}
