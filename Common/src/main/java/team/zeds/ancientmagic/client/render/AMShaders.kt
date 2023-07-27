package team.zeds.ancientmagic.client.render

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.resources.ResourceLocation
import team.zeds.ancientmagic.AMConstant.reloc
import java.util.function.Consumer

class AMShaders private constructor() {
    var improvedParticle: ShaderInstance? = null
    var hologram: ShaderInstance? = null

    fun init(reg: (ResourceLocation, VertexFormat, Consumer<ShaderInstance>) -> Unit) {
        reg.invoke(
            reloc("impr_particle"),
            DefaultVertexFormat.POSITION
        ) {
            inst -> improvedParticle = inst
        }
        reg.invoke(
            reloc("holo"),
            DefaultVertexFormat.POSITION
        ) {
            instance -> hologram = instance
        }
    }

    companion object {
        @get:JvmStatic var instance: AMShaders? = null
            get() {
                if (field == null) field = AMShaders(); return field
            }
            private set
    }
}