package team.zeds.ancientmagic.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import team.zeds.ancientmagic.common.client.render.entity.AltarBlockEntityRender;
import team.zeds.ancientmagic.common.client.render.entity.AltarPedestalBlockEntityRender;
import team.zeds.ancientmagic.fabric.registries.AMManage;
import team.zeds.ancientmagic.fabric.registries.AMRegistry;

public class AMFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AMManage.initClient();
    }
}
