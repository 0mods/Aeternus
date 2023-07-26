package team.zeds.ancientmagic.mixin;

import team.zeds.ancientmagic.AMConstant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
    
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        
        AMConstant.LOGGER.info("This line is printed by an example mod mixin from Fabric!");
        AMConstant.LOGGER.info("MC Version: {}", Minecraft.getInstance().getVersionType());
        AMConstant.LOGGER.info("Classloader: {}", this.getClass().getClassLoader());
    }
}