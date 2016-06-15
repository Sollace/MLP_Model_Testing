package com.minelittlepony.render;

import com.minelittlepony.main.FlyState;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.util.math.MathHelper;

public class LayerElytraPony extends LayerElytra {
	
	public RenderPony renderer;
	
	public LayerElytraPony(RenderPony renderPlayerIn) {
		super(renderPlayerIn);
		renderer = renderPlayerIn;
	}
	
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float stutter, float netHeadYaw, float headPitch, float scale) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.8, 0.8, 0.8);
		float vert = 0.6f;
		FlyState flystate = renderer.getPonyModel().entityFlyState;
		if (flystate == FlyState.HOVERING) {
			vert += MathHelper.cos(stutter * 0.57F) * scale;
		}
		if (flystate == FlyState.LANDED && entitylivingbaseIn.isSneaking()) {
			vert += 0.3f;
		}
		GlStateManager.translate(0, vert, 0);
		GlStateManager.rotate(renderer.getPonyModel().rock * 20, 0, 0, 1);
		GlStateManager.rotate(85 - renderer.getPonyModel().roll, 1, 0, 0);
		super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, stutter, netHeadYaw, headPitch, scale);
		GlStateManager.popMatrix();
    }
}
