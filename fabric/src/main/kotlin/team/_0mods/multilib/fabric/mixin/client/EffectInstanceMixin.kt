package team._0mods.multilib.fabric.mixin.client

import com.mojang.blaze3d.shaders.Program
import net.minecraft.client.renderer.EffectInstance
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Redirect

@Unique
@Mixin(value = [EffectInstance::class], priority = 950)
class EffectInstanceMixin {
    @Redirect(
        method = ["<init>"],
        at = At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", ordinal = 0)
    )
    private fun mojangPls(s: String, rm: ResourceManager, str: String): ResourceLocation {
        return mojangPls(ResourceLocation(str), ".json")
    }

    @Redirect(
        method = ["getOrCreate"],
        at = At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", ordinal = 0)
    )
    private fun mojangPls(s: String, rm: ResourceManager, type: Program.Type, str: String): ResourceLocation {
        return mojangPls(ResourceLocation(str), type.extension)
    }

    private fun mojangPls(rl: ResourceLocation, ext: String): ResourceLocation {
        return ResourceLocation(rl.namespace, "shaders/program/" + rl.path + ext)
    }
}