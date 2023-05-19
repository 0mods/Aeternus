package com.algorithmlx.ancientmagic.mixin;

import api.ancientmagic.mod.Constant;
import net.minecraftforge.fml.ModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    public static final String MIXIN_COMPACT_PACKAGE = "com.algorithmlx.ancientmagic.mixin.compact.";

    @Override
    public void onLoad(String mixinPackage) {
        Constant.LOGGER.debug("Loading mixins {}", mixinPackage);
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean check = mixinClassName.startsWith(MIXIN_COMPACT_PACKAGE) && mixinClassName.contains("WaystoneInject");

        if (ModList.get().isLoaded("waystones")) return check;

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
