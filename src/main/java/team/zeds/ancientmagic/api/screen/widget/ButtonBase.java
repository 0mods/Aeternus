package team.zeds.ancientmagic.api.screen.widget;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ButtonBase extends Button {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Component text;
    private final ResourceLocation texture;
    private final ItemStack stack;
    private final boolean isStackedTexture;

    private ButtonBase(Builder builder, ResourceLocation texture) {
        super(builder);
        this.texture = texture;
        this.text = builder.message;
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.stack = null;
        this.isStackedTexture = false;
    }

    public ButtonBase(Builder builder, ItemStack stack) {
        super(builder);
        this.texture = new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "");
        this.text = builder.message;
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.stack = stack;
        this.isStackedTexture = true;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int x, int y, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        if (this.text != null)
            graphics.drawString(font, this.text, this.x, this.y + height / 4, 0xFFFFFF);

        if (isStackedTexture) {
            if (!this.stack.isEmpty()) {
                Lighting.setupFor3DItems();

                graphics.renderItem(this.stack, this.x + 2, this.y + 2);
                graphics.renderItemDecorations(font, this.stack, this.x + 2, this.y + 2);
            }
        } else {
            graphics.blit(texture, this.x, this.y, 0, isCursorAtButton(x, y) ? this.height : 0, this.width, this.height, this.width, this.height * 2);
        }
    }

    public boolean isCursorAtButton(int cursorX, int cursorY) {
        return cursorX >= this.x && cursorY >= this.y && cursorX <= this.x + this.width && cursorY <= this.y + this.height;
    }
}
