package team.zeds.ancientmagic.event;

import kotlin.Unit;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import team.zeds.ancientmagic.client.render.AMShaders;
import team.zeds.ancientmagic.client.render.entity.AltarBlockEntityRender;
import team.zeds.ancientmagic.client.render.entity.AltarPedestalBlockEntityRender;
import team.zeds.ancientmagic.init.registries.AMRegister;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class AMModEvents {
    public static void registerShaders(RegisterShadersEvent event) {
        AMShaders.getInstance().init((id, format, inst) -> {
            try {
                event.registerShader(new ShaderInstance(event.getResourceProvider(), id, format), inst);
                return Unit.INSTANCE;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    public static void registerBlockEntityR3nders(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AMRegister.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntityRender::new);
        event.registerBlockEntityRenderer(AMRegister.ALTAR_PEDESTAL_BLOCK_ENTITY.get(), AltarPedestalBlockEntityRender::new);
    }
}
