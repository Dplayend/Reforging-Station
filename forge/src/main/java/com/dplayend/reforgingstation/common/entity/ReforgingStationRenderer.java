package com.dplayend.reforgingstation.common.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class ReforgingStationRenderer implements BlockEntityRenderer<ReforgingStationBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ReforgingStationRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ReforgingStationBlockEntity reforgingStationBlock, float tick, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        ItemStack inputItem = reforgingStationBlock.getItem(0);
        ItemStack materialStack = reforgingStationBlock.getItem(1);

        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.NORTH)) {
            poseStack.pushPose();
            poseStack.translate(0.7f, 0.9f, 0.7f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(0));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(inputItem, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);
            poseStack.popPose();
        }
        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST)) {
            poseStack.pushPose();
            poseStack.translate(0.3f, 0.9f, 0.7f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(inputItem, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);

            poseStack.popPose();
        }
        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.SOUTH)) {
            poseStack.pushPose();
            poseStack.translate(0.3f, 0.9f, 0.3f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(inputItem, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);

            poseStack.popPose();
        }
        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)) {
            poseStack.pushPose();
            poseStack.translate(0.7f, 0.9f, 0.3f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(270));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(inputItem, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);

            poseStack.popPose();
        }

        ///////////////////////////////////////////////////////////////////////////////////////////

        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.NORTH)) {
            poseStack.pushPose();
            poseStack.translate(0.7f, 0.9f, 0.3f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(0));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(materialStack, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);

            poseStack.popPose();
        }
        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST)) {
            poseStack.pushPose();
            poseStack.translate(0.7f, 0.9f, 0.7f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(materialStack, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);

            poseStack.popPose();
        }
        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.SOUTH)) {
            poseStack.pushPose();
            poseStack.translate(0.3f, 0.9f, 0.7f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(materialStack, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);

            poseStack.popPose();
        }
        if (reforgingStationBlock.getBlockState().getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)) {
            poseStack.pushPose();
            poseStack.translate(0.3f, 0.9f, 0.3f);
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(270));

            int lightBlock = reforgingStationBlock.getLevel().getBrightness(LightLayer.BLOCK, reforgingStationBlock.getBlockPos());
            int lightSky = reforgingStationBlock.getLevel().getBrightness(LightLayer.SKY, reforgingStationBlock.getBlockPos());

            this.itemRenderer.renderStatic(materialStack, ItemDisplayContext.FIXED, LightTexture.pack(lightBlock, lightSky), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, reforgingStationBlock.getLevel(), 1);

            poseStack.popPose();
        }
    }
}
