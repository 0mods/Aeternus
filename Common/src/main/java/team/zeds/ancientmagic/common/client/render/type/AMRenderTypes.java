package team.zeds.ancientmagic.common.client.render.type;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL14;
import team.zeds.ancientmagic.common.client.render.shader.AMShaders;

import static team.zeds.ancientmagic.common.AMConstant.reloc;

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

    private AMRenderTypes(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, Runnable $$6, Runnable $$7) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }

    public static class AMShaderStateShards {
        public static final ShaderStateShard HOLO_STATE = new ShaderStateShard(AMShaders.getInstance()::getHologram);
        public static final ShaderStateShard IMPROVED_PARTICLE = new ShaderStateShard(AMShaders.getInstance()::getImprovedParticle);
    }
}
