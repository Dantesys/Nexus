package org.dantesys.nexus.telas;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.dantesys.nexus.Nexus;

public class InfusorScreen extends AbstractContainerScreen<InfusorMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Nexus.MODID, "textures/gui/infusor.png");

    public InfusorScreen(InfusorMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,TEXTURE, x + 63, y + 23, 176, 3, menu.getScaledArrowProgress()[0],26, 256, 256);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,TEXTURE, x + 113-menu.getScaledArrowProgress()[0], y + 23, 226-menu.getScaledArrowProgress()[0], 3, menu.getScaledArrowProgress()[0],26, 256, 256);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,TEXTURE, x + 84, y + 20, 197, 0, 8,menu.getScaledArrowProgress()[1], 256, 256);
        }
    }
    @Override
    public void render(GuiGraphics pPoseStack, int mouseX, int mouseY, float delta) {
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
