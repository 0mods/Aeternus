package team.zeds.ancientmagic.event;

import kotlin.Unit;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import team.zeds.ancientmagic.client.render.ModShaders;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class AMModEvents {
    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        ModShaders.getInstance().init((id, format, inst) -> {
            try {
                event.registerShader(new ShaderInstance(event.getResourceProvider(), id, format), inst);
                return Unit.INSTANCE;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
