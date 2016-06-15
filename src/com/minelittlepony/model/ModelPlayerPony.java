package com.minelittlepony.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;

public class ModelPlayerPony extends ModelPlayer {
	private final ModelAdvancedPony wrapped;
	
	public ModelPlayerPony(ModelAdvancedPony in) {
		super(1, false);
		wrapped = in;
		this.bipedBody = in.bipedBody;
		this.bipedHead = in.bipedHead;
		this.bipedBodyWear = in.bipedHeadwear;
		this.bipedLeftArm = in.bipedLeftArm;
		this.bipedRightArm = in.bipedRightArm;
		this.bipedLeftLeg = in.bipedLeftLeg;
		this.bipedRightLeg = in.bipedRightLeg;
		this.boxList = in.boxList;
		super.setModelAttributes(in);
	}
	
	public ModelAdvancedPony getPonyModel() {
		return wrapped;
	}
	
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float stutter, float yaw, float pitch, float scale) {
		wrapped.swingProgress = swingProgress;
		wrapped.isChild = isChild;
		wrapped.isRiding = isRiding;
		wrapped.isSneak = isSneak;
		wrapped.render(entity, limbSwing, limbSwingAmount, stutter, yaw, pitch, scale);
		isChild = wrapped.isChild;
		isRiding = wrapped.isRiding;
		isSneak = wrapped.isSneak;
		swingProgress = wrapped.swingProgress;
	}
	
	public void renderDeadmau5Head(float scale) {
		//TODO: Deadmau5
	}
	
	public void renderCape(float scale) {
		//TODO: Capes
	}
	
	public void setRotationAngles(float time, float walkSpeed, float stutter, float yaw, float pitch, float increment, Entity entity) {
		wrapped.setRotationAngles(time, walkSpeed, stutter, yaw, pitch, increment, entity);
	}
	
	public void setInvisible(boolean invisible) {
		wrapped.setInvisible(invisible);
	}
	
	public ModelRenderer getRandomModelBox(Random rand) {
		return wrapped.getRandomModelBox(rand);
	}
	
	public TextureOffset getTextureOffset(String partName) {
		return wrapped.getTextureOffset(partName);
	}
	
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
		wrapped.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
	}
	
	public void setModelAttributes(ModelBase model) {
		wrapped.setModelAttributes(model);
		super.setModelAttributes(model);
		this.swingProgress = wrapped.swingProgress = model.swingProgress;
	}
	
	public void postRenderArm(float scale, EnumHandSide side) {
		wrapped.swingProgress = this.swingProgress;
		wrapped.postRenderArm(scale, side);
	}
}
