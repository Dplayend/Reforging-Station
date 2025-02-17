package com.dplayend.reforgingstation.common.screen;

import com.dplayend.reforgingstation.ReforgingStation;
import com.dplayend.reforgingstation.common.quality.QualityUtil;
import com.dplayend.reforgingstation.handler.HandlerMinecraft;
import com.dplayend.reforgingstation.network.common.ChangeQualitySP;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ReforgingStationScreen extends AbstractContainerScreen<ReforgingStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ReforgingStation.MOD_ID, "textures/gui/container/reforging_station.png");
    private static final ResourceLocation HAMMER_PRESSED = new ResourceLocation(ReforgingStation.MOD_ID, "textures/gui/sprites/container/reforging_station/hammer_pressed.png");
    private static final ResourceLocation HAMMER_UNPRESSED = new ResourceLocation(ReforgingStation.MOD_ID, "textures/gui/sprites/container/reforging_station/hammer_unpressed.png");
    private static ResourceLocation BUTTON_TEXTURE = HAMMER_UNPRESSED;
    private final ReforgingStationMenu container;

    public ReforgingStationScreen(ReforgingStationMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.container = container;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    public void init() {
        super.init();
    }

    private void createButton(int x, int y, Button.OnPress onPress) {
        addWidget(Button.builder(CommonComponents.GUI_DONE, onPress).bounds(x, y, 16, 16).build());
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Component component = Component.translatable("container.reforging_station");
        guiGraphics.drawString(this.font, component, 88 - (this.font.width(component.getString()) / 2), 6, -12829636, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (container.canChangeQuality()) {
            createButton(this.leftPos + 80, this.topPos + 39, button -> {
                button.visible = false;
                BUTTON_TEXTURE = HAMMER_PRESSED;
                ChangeQualitySP.send();
                HandlerMinecraft.playSound(SoundEvents.ANVIL_USE);
                rebuildWidgets();
            });
            guiGraphics.blit(BUTTON_TEXTURE, this.leftPos + 80, this.topPos + 39, 0, 0, 16, 16, 16, 16);
        } else {
            rebuildWidgets();
        }
        if (container.slots.get(36).hasItem() && !container.slots.get(36).getItem().getOrCreateTag().getString("quality").isEmpty() && checkMouse(this.leftPos + 80, this.topPos + 39, mouseX, mouseY, 16, 16)) {
            List<Component> tooltipList = new ArrayList<>();
            QualityUtil.createTooltip(container.slots.get(36).getItem(), tooltipList, true);
            guiGraphics.renderComponentTooltip(this.font, tooltipList, mouseX, mouseY);
        }

        RenderSystem.disableBlend();
    }

    public boolean checkMouse(int x, int y, int mouseX, int mouseY, int width, int height) {
        return x <= mouseX && (x + width) >= mouseX && y <= mouseY && (y + height) >= mouseY;
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            onClose();
            return true;
        } else {
            return super.keyPressed(key, b, c);
        }
    }

    @Override
    public boolean mouseReleased(double p_97812_, double p_97813_, int p_97814_) {
        BUTTON_TEXTURE = HAMMER_UNPRESSED;
        return super.mouseReleased(p_97812_, p_97813_, p_97814_);
    }
}
