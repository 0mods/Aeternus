package team._0mods.aeternus.platformredirect.mixin;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import team._0mods.aeternus.api.util.APIResourceLocation;

@Mixin(ResourceLocation.class)
public abstract class ResourceLocationMixin implements APIResourceLocation {
    @Shadow public abstract String getPath();

    @Shadow public abstract String getNamespace();

    @Shadow public abstract String toString();

    @Shadow public abstract String toDebugFileName();

    @NotNull
    @Override
    public String getRlPath() {
        return this.getPath();
    }

    @NotNull
    @Override
    public String getRlNamespace() {
        return this.getNamespace();
    }

    @NotNull
    @Override
    public String getAsString() {
        return this.toString();
    }

    @NotNull
    @Override
    public String getDebugName() {
        return this.toDebugFileName();
    }
}
