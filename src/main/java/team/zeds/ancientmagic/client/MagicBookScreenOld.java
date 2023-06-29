package team.zeds.ancientmagic.client;

import net.minecraft.client.gui.GuiGraphics;
import team.zeds.ancientmagic.api.mod.Constant;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MagicBookScreenOld extends Screen {
    private static final int imgWidth = 512;
    private static final int imgHeight = 256;

    private static final ResourceLocation TEXTURE = textureGen("book_screen");

    public MagicBookScreenOld() {
        super(Component.empty());
    }

    private GuiGraphics guiGraphics;

    @Override
    public void render(GuiGraphics guiGraphics, int xMouse, int yMouse, float partialTick) {
        this.guiGraphics = guiGraphics;
        int j = ((this.width / 2) - (imgWidth / 2));
        int k = ((this.height / 2) - (imgHeight / 2));

        guiGraphics.blit(textureGen("player_display"), j + 66, k + 29, j, k, j, k);
        guiGraphics.blit(textureGen("tag_display"), j + 62, k + 119, j, k, j, k);
        guiGraphics.blit(TEXTURE, j, k, 0,0, imgHeight, imgWidth, imgHeight, imgHeight);

        if (this.minecraft != null && this.minecraft.player != null) {
            var player = this.minecraft.player;
            this.renderBackground(guiGraphics);
            int xPos = j + 97;
            int yPos = k + 111;

            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, xPos, yPos, 35, xPos - xMouse, yPos - yMouse, player);
        }

        addTexts();
    }

    private static ResourceLocation textureGen(String name) {
        return new ResourceLocation(Constant.KEY, String.format("textures/screen/magicbook/%s.png", name));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void addTexts() {
        addText("", 0,0);
    }

    private void addText(String message, int x, int y) {
        guiGraphics.drawString(this.font, Component.translatable("magic.%s.%s", Constant.KEY, message), x, y, ChatFormatting.BLACK.getId());
    }
}
