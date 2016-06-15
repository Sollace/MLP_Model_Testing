package com.minelittlepony.render;

import com.minelittlepony.model.ModelAdvancedPony;
import com.minelittlepony.model.ModelPlayerPony;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

/**
 * Slightly modified from RenderPlayer.
 * Only doing this to be able to test the pony model.
 *
 */
public class RenderPony extends RenderPlayer {
    
	private static final ResourceLocation DARING_DOO = new ResourceLocation("minelittlepony", "textures/entity/steve.png");
	
    public RenderPony(RenderManager renderManager) {
        this(renderManager, false);
    }
    
    public RenderPony(RenderManager renderManager, boolean useSmallArms) {
        super(renderManager, useSmallArms);
        mainModel = new ModelPlayerPony(new ModelAdvancedPony(0, false));
        layerRenderers.clear();
        addLayer(new LayerPonyArmour(this));
        addLayer(new LayerItemInHoof(this));
        addLayer(new LayerArrow(this));
        addLayer(new LayerDeadmau5Head(this));
        addLayer(new LayerCape(this));
        addLayer(new LayerElytraPony(this));
        //addLayer(new LayerCustomHead(this.getPlayerModel().bipedHead));
    }

   
    public ModelAdvancedPony getPonyModel() {
        return ((ModelPlayerPony)getMainModel()).getPonyModel();
    }
    
    public void doRender(AbstractClientPlayer clientPlayer, double p_180596_2_, double p_180596_4_, double p_180596_6_, float p_180596_8_, float p_180596_9_) {
        if (!clientPlayer.isUser() || renderManager.renderViewEntity == clientPlayer) {
            setModelVisibilities(clientPlayer);
            super.doRender(clientPlayer, p_180596_2_, p_180596_4_, p_180596_6_, p_180596_8_, p_180596_9_);
        }
    }
    
    protected void setModelVisibilities(AbstractClientPlayer clientPlayer) {
    	ModelAdvancedPony model = getPonyModel();
        if (clientPlayer.isSpectator()) {
            model.setInvisible(false);
            model.bipedHead.showModel = true;
            model.bipedHeadwear.showModel = true;
        } else {
            model.setInvisible(true);
            model.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
            model.rightArmPose = getArmPose(clientPlayer, clientPlayer.getHeldItemMainhand());
            model.leftArmPose = getArmPose(clientPlayer, clientPlayer.getHeldItemOffhand());
            model.isSneak = clientPlayer.isSneaking();
        }
        
        
    	model.elytra = clientPlayer.isElytraFlying();
    }
    
    private ArmPose getArmPose(AbstractClientPlayer clientPlayer, ItemStack stack) {
        if (stack != null) {
            if (clientPlayer.getItemInUseCount() > 0) {
                EnumAction enumaction = stack.getItemUseAction();
                if (enumaction == EnumAction.BLOCK) {
                    return ArmPose.BLOCK;
                } else if (enumaction == EnumAction.BOW) {
                    return ArmPose.BOW_AND_ARROW;
                }
            }
            return ArmPose.ITEM;
        }
        return ArmPose.EMPTY;
    }

    public void renderRightArm(AbstractClientPlayer clientPlayer) {
    	bindTexture(getEntityTexture(clientPlayer));
        GlStateManager.color(1, 1, 1);
        ModelAdvancedPony model = getPonyModel();
        setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        model.swingProgress = 0.0F;
        model.isSneak = false;
        model.setRotationAngles(0, 0, 0, 0, 0, 0.0625F, clientPlayer);
        model.renderRightArm();
        GlStateManager.disableBlend();
    }

    public void renderLeftArm(AbstractClientPlayer clientPlayer) {
    	bindTexture(getEntityTexture(clientPlayer));
        GlStateManager.color(1, 1, 1);
        ModelAdvancedPony model = getPonyModel();
        setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        model.swingProgress = 0.0F;
        model.isSneak = false;
        model.setRotationAngles(0, 0, 0, 0, 0, 0.0625F, clientPlayer);
        model.renderLeftArm();
        GlStateManager.disableBlend();
    }
    
    protected void rotateCorpse(AbstractClientPlayer entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
        /*if (entityLiving.isEntityAlive() && entityLiving.isPlayerSleeping())
        {
            GlStateManager.rotate(entityLiving.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
        }
        else if (entityLiving.isElytraFlying())
        {
            super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
            float f = (float)entityLiving.getTicksElytraFlying() + partialTicks;
            float f1 = MathHelper.clamp_float(f * f / 100.0F, 0.0F, 1.0F);
            GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
            Vec3d vec3d = entityLiving.getLook(partialTicks);
            double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
            double d1 = vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord;

            if (d0 > 0.0D && d1 > 0.0D)
            {
                double d2 = (entityLiving.motionX * vec3d.xCoord + entityLiving.motionZ * vec3d.zCoord) / (Math.sqrt(d0) * Math.sqrt(d1));
                double d3 = entityLiving.motionX * vec3d.zCoord - entityLiving.motionZ * vec3d.xCoord;
                GlStateManager.rotate((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / (float)Math.PI, 0.0F, 1.0F, 0.0F);
            }
        }
        else
        {*/
            super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
        //}
            
            if (entityLiving.isElytraFlying()) {
            	float f = (float)entityLiving.getTicksElytraFlying() + partialTicks;
                float f1 = MathHelper.clamp_float(f * f / 100.0F, 0.0F, 1.0F);
            	GlStateManager.rotate(-f1 * (-120.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
            	GlStateManager.translate(0, -1, 0);
            }
    }
    
    protected ResourceLocation getEntityTexture(AbstractClientPlayer clientPlayer) {
        return DARING_DOO;
    }
}
