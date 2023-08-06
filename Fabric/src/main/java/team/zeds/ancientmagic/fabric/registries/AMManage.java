package team.zeds.ancientmagic.fabric.registries;

import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import team.zeds.ancientmagic.common.client.render.entity.AltarBlockEntityRender;
import team.zeds.ancientmagic.common.client.render.entity.AltarPedestalBlockEntityRender;
import team.zeds.ancientmagic.common.event.AMCommonnessEvents;
import team.zeds.ancientmagic.common.init.config.AMConfig;
import team.zeds.ancientmagic.common.init.registries.AMTags;
import team.zeds.ancientmagic.fabric.config.AMClient;
import team.zeds.ancientmagic.fabric.config.AMCommon;
import team.zeds.ancientmagic.fabric.event.PlayerTick;
import team.zeds.ancientmagic.fabric.network.AMNetwork;

public class AMManage {
    private static final ConfigHolder<AMCommon> COMMON_CONFIG = AMCommon.register();
    private static final ConfigHolder<AMClient> CLIENT_CONFIG = AMClient.register();

    public static void init() {
        AMConfig.setCommon(AMManage.commonConfig());
        AMTags.getInstance().init();
        AMRegistry.initialize();
        AMNetwork.registerC2S();
        ServerTickEvents.START_SERVER_TICK.register(new PlayerTick());
        ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> AMCommonnessEvents.playerClone(oldPlayer, newPlayer, !alive)));
    }

    public static void initClient() {
        AMConfig.setClient(AMManage.clientConfig());
        AMNetwork.registerS2C();
        ItemTooltipCallback.EVENT.register((stack, flag, tooltip) -> AMCommonnessEvents.tooltipEvent(stack, tooltip, flag));
        BlockEntityRenderers.register(AMRegistry.INSTANCE.getAltarBlockEntityType(), AltarBlockEntityRender::new);
        BlockEntityRenderers.register(AMRegistry.INSTANCE.getAltarPedestalBlockEntityType(), AltarPedestalBlockEntityRender::new);
    }

    public static AMCommon commonConfig() {
        return COMMON_CONFIG.getConfig();
    }

    public static AMClient clientConfig() {
        return CLIENT_CONFIG.getConfig();
    }
}
