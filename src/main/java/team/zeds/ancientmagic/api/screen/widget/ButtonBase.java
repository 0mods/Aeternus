package team.zeds.ancientmagic.api.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ButtonBase extends Button {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Component text;
    private final ResourceLocation texture;
    private final boolean isBlockTexture;
    private final boolean isItemTexture;

    private ButtonBase(Builder builder, ResourceLocation texture, boolean isBlockTexture, boolean isItemTexture) {
        super(builder);
        this.texture = texture;
        this.text = builder.message;
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.isBlockTexture = isBlockTexture;
        this.isItemTexture = isItemTexture;
    }

    public ButtonBase(Builder builder, ResourceLocation texture) {
        this(builder, texture, false, false);
    }

    public ButtonBase(Builder builder, Block block) {
        this(builder, ForgeRegistries.BLOCKS.getKey(block), true, false);
    }

    public ButtonBase(Builder builder, ItemLike item) {
        this(builder, ForgeRegistries.ITEMS.getKey(item.asItem()), false, true);
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        PoseStack stack = graphics.pose();

        stack.pushPose();
        stack.translate(0.0, 0.0, 100.0);
        graphics.drawString(font, this.text, this.x, this.y + height / 4, 0xFFFFFF);
        stack.popPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        if (isBlockTexture) {
            ResourceLocation loc = InventoryMenu.BLOCK_ATLAS;
            ResourceLocation blockLoc = new ResourceLocation(this.texture.getNamespace(),
                    String.format("block/%s", this.texture.getPath()));
            TextureAtlasSprite texture = minecraft.getTextureAtlas(loc).apply(blockLoc);
            graphics.blit(this.x, this.y, 0, this.width, this.height, texture);
        }

        if (isItemTexture) {
            Item item = ForgeRegistries.ITEMS.getValue(this.texture);
            TextureAtlasSprite texture = minecraft.getItemRenderer().getItemModelShaper().getItemModel(item).getParticleIcon();
            graphics.blit(this.x, this.y, 0, this.width, this.height, texture);
        }

        if (!isItemTexture && !isBlockTexture) {
            graphics.blit(texture, this.x, this.y, 0, isCursorAtButton(x, y) ? this.height : 0, this.width, this.height, this.width, this.height * 2);
        }
    }

    public boolean isCursorAtButton(int cursorX, int cursorY) {
        return cursorX >= this.x && cursorY >= this.y && cursorX <= this.x + this.width && cursorY <= this.y + this.height;
    }
}
