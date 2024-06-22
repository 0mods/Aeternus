package team._0mods.aeternus.platformredirect.mixin;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import team._0mods.aeternus.api.util.Text;

@Mixin(Component.class)
public class ComponentMixin implements Text {}
