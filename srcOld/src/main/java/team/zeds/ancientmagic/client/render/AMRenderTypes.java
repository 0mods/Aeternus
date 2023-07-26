package team.zeds.ancientmagic.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL14;

import static team.zeds.ancientmagic.api.mod.AMConstant.reloc;

public final class AMRenderTypes extends RenderType {
    private static final TransparencyStateShard HOLO_TRANSPARENCY = new TransparencyStateShard(reloc("hologram_transparency").toString(),
            ()-> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                GL14.glBlendColor(1.0f, 1.0f, 1.0f, 0.45f);
            },
            ()-> {
                GL14.glBlendColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            }
    );

    public static final RenderType MAGICAL_HOLOGRAM = create(
            reloc("magical_hologram").toString(),
            DefaultVertexFormat.BLOCK,
            VertexFormat.Mode.QUADS,
            2100000,
            true,
            false,
            CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(RENDERTYPE_SOLID_SHADER)
                    .setTextureState(BLOCK_SHEET)
                    .setTransparencyState(AMRenderTypes.HOLO_TRANSPARENCY)
                    .createCompositeState(false)
    );

    public AMRenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static class AMShaderStateShards {
        public static final RenderStateShard.ShaderStateShard HOLO_STATE = new ShaderStateShard(AMShaders.getInstance()::getHologram);
        public static final RenderStateShard.ShaderStateShard IMPROVED_PARTICLE = new ShaderStateShard(AMShaders.getInstance()::getImprovedParticle);
    }
}
