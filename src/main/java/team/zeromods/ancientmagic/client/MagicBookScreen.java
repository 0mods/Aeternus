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

    private static final ResourceLocation TEXTURE = textureGen("main");

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

        this.renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, TEXTURE);

//        for (int i = 0; i < j; i++) {
//            for (int l = 0; l < k; l++) {
//                if (i == 66 && l == 29)
//                    RenderSystem.setShaderTexture(1, textureGen("player_display"));
//                if (i == 62 && l == 119)
//                    RenderSystem.setShaderTexture(2, textureGen("tag_display"));
//            }
//        }

        blit(pPoseStack, j, k, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);

        int xPos = j+97;
        int yPos = k+111;

        InventoryScreen.renderEntityInInventoryFollowsMouse(
                pPoseStack, xPos, yPos, 35, (float) xPos - xMouse, (float) yPos - yMouse,
                player
        );


        this.minecraft.font.draw(pPoseStack, Component.translatable("magic.ancientmagic.level",
                        MagicTypes.getByNumeration(ClientPlayerMagicData.getPlayerData()).getTranslation()),
                j + 56, k + 122, ChatFormatting.BLACK.getId());
    }

    private static ResourceLocation textureGen(String name) {
        return new ResourceLocation(Constant.Key, String.format("textures/screen/magicbook/%s.png", name));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
