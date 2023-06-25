package team.zeds.ancientmagic.mixin;

import team.zeds.ancientmagic.api.mod.Constant;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import team.zeds.ancientmagic.compact.CompactInitializer;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    public static final String MIXIN_COMPACT_PACKAGE = "team.zeromods.ancientmagic.mixin.compact.";

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

        return (CompactInitializer.getWaystonesLoaded() == check) && !check || !check;
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
