package com.minelittlepony.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class LayerItemInHoof implements LayerRenderer<EntityLivingBase> {
	
	protected final RenderPony livingEntityRenderer;
	
	public LayerItemInHoof(RenderPony renderer) {
		livingEntityRenderer = renderer;
	}
	
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean swap = entity.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack offhand = swap ? entity.getHeldItemOffhand() : entity.getHeldItemMainhand();
        ItemStack mainhand = swap ? entity.getHeldItemMainhand() : entity.getHeldItemOffhand();
        if (offhand != null || mainhand != null) {
            GlStateManager.pushMatrix();
            if (livingEntityRenderer.getPonyModel().isChild) {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                GlStateManager.scale(f, f, f);
            }
            renderHeldItem(entity, mainhand, TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            renderHeldItem(entity, offhand, TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }
	
	private void renderHeldItem(EntityLivingBase entity, ItemStack stack, TransformType transform, EnumHandSide handSide) {
        if (stack != null) {
            GlStateManager.pushMatrix();
            livingEntityRenderer.getPonyModel().postRenderArm(0.0625F, handSide);
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean swap = handSide == EnumHandSide.LEFT;
            GlStateManager.translate(swap ? -0.0625F : 0.0625F, 0.125F, -0.625F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, transform, swap);
            GlStateManager.popMatrix();
        }
    }
	
	public boolean shouldCombineTextures() {
		return true;
	}
}
