package team.zeromods.ancientmagic.client;

import api.ancientmagic.magic.MagicTypes;
import api.ancientmagic.mod.Constant;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import team.zeromods.ancientmagic.init.AMCapability;

public class MagicBookScreen extends Screen {
    private static final int imgWidth = 512;
    private static final int imgHeight = 256;

    public MagicBookScreen() {
        super(Component.empty());
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int xMouse, int yMouse, float partialTick) {
        super.render(pPoseStack, xMouse, yMouse, partialTick);

        int j = ((this.width / 2) - (imgWidth / 2));
        int k = ((this.height / 2) - (imgHeight / 2));

        assert this.minecraft != null;
        var player = this.minecraft.player;

        assert player != null;
        blit(pPoseStack, j, k, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
        RenderSystem.setShaderTexture(0, new ResourceLocation(Constant.Key,
                "textures/screen/magicbook/main.png"));
        InventoryScreen.renderEntityInInventoryFollowsMouse(
                pPoseStack, 68, 31, 30, 68 - (float) xMouse, (float) yMouse,
                player
        );

        player.getCapability(AMCapability.PLAYER_MAGIC_HANDLER).ifPresent(cap -> {
            this.minecraft.font.draw(pPoseStack, MagicTypes.getByNumeration(cap.getMagicLevel()).getTranslation(),
                    69, 122, ChatFormatting.BLACK.getId());
        });
    }
}
