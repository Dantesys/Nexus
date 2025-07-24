package org.dantesys.nexus.telas;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.dantesys.nexus.Nexus;
import org.dantesys.nexus.utilidade.NexusTipos;

public class ColetorScreen extends AbstractContainerScreen<ColetorMenu> {
    private static ResourceLocation TEXTURE ;

    public ColetorScreen(ColetorMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
        this.inventoryLabelY = 100000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        TEXTURE = ResourceLocation.fromNamespaceAndPath(Nexus.MODID, "textures/gui/coletor_"+ NexusTipos.getNomeTipo(menu.isType())+".png");
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderProgressArrow(guiGraphics, x, y);
        renderProgressEssence(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,TEXTURE, x + 64, y + 36, 176, 0, menu.getScaledArrowProgress(), 10, 256, 256);
        }
    }
    private void renderProgressEssence(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isLoading()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,TEXTURE, x + 48, y + 74, 176, 10, menu.getScaledArrowLoader(), 6, 256, 256);
        }
    }
    @Override
    public void render(GuiGraphics pPoseStack, int mouseX, int mouseY, float delta) {
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}