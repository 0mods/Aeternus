package team.zeds.ancientmagic.client.render

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.resources.ResourceLocation
import team.zeds.ancientmagic.api.mod.AMConstant.reloc
import java.util.function.Consumer

class ModShaders {
    var improvedParticle: ShaderInstance? = null

    fun init(registration: (ResourceLocation, VertexFormat, Consumer<ShaderInstance>) -> Unit = {_, _, _ -> }) {
        registration.invoke(
            reloc("impr_particle"),
            DefaultVertexFormat.POSITION
        ) { inst -> improvedParticle = inst }
    }

    companion object {
        @get:JvmStatic val instance: ModShaders = ModShaders()
    }
}
