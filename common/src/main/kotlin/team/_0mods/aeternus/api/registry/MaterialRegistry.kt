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