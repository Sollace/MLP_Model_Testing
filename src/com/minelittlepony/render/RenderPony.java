package com.minelittlepony.render;

import com.minelittlepony.model.ModelAdvancedPony;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Slightly modified from RenderPlayer.
 * Only doing this to be able to test the pony model.
 *
 */
public class RenderPony extends RenderPlayer {
    
	private static final ResourceLocation DARING_DOO = new ResourceLocation("minelittlepony", "textures/entity/steve.png");
	
	private ModelBase oldModel;
	
    public RenderPony(RenderManager renderManager) {
        this(renderManager, false);
    }

    public RenderPony(RenderManager renderManager, boolean useSmallArms) {
        super(renderManager, useSmallArms);
        oldModel = mainModel;
        mainModel = new ModelAdvancedPony(0, false);
        layerRenderers.clear();
        addLayer(new LayerPonyArmour(this));
        addLayer(new LayerHeldItem(this));
        addLayer(new LayerArrow(this));
        addLayer(new LayerDeadmau5Head(this));
        addLayer(new LayerCape(this));
        //addLayer(new LayerCustomHead(this.getPlayerModel().bipedHead));
    }

   
    public ModelAdvancedPony getPonyModel() {
        return (ModelAdvancedPony)mainModel;
    }
    
    //Return the old one, so code we can't override doesn't break
    public ModelPlayer getPlayerModel() {
        return (ModelPlayer)(oldModel != null ? oldModel : mainModel);
    }
    
    public ModelBase getMainModel() {
        return getPonyModel();
    }
    
    public void doRender(AbstractClientPlayer clientPlayer, double p_180596_2_, double p_180596_4_, double p_180596_6_, float p_180596_8_, float p_180596_9_) {
        if (!clientPlayer.isUser() || renderManager.livingPlayer == clientPlayer) {
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
            ItemStack heldItem = clientPlayer.inventory.getCurrentItem();
            model.setInvisible(true);
            model.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
            model.heldItemLeft = 0;
            model.aimedBow = false;
            model.isSneak = clientPlayer.isSneaking();
            if (heldItem == null) {
                model.heldItemRight = 0;
            } else {
                model.heldItemRight = 1;
                if (clientPlayer.getItemInUseCount() > 0) {
                    EnumAction itemType = heldItem.getItemUseAction();
                    if (itemType == EnumAction.BLOCK) {
                        model.heldItemRight = 3;
                    } else if (itemType == EnumAction.BOW) {
                        model.aimedBow = true;
                    }
                }
            }
        }
    }

    public void renderRightArm(AbstractClientPlayer clientPlayer) {
        GlStateManager.color(1, 1, 1);
        ModelAdvancedPony model = getPonyModel();
        setModelVisibilities(clientPlayer);
        model.swingProgress = 0.0F;
        model.isSneak = false;
        model.setRotationAngles(0, 0, 0, 0, 0, 0.0625F, clientPlayer);
        model.renderRightArm();
    }

    public void renderLeftArm(AbstractClientPlayer clientPlayer) {
        GlStateManager.color(1, 1, 1);
        ModelAdvancedPony model = getPonyModel();
        setModelVisibilities(clientPlayer);
        model.isSneak = false;
        model.swingProgress = 0.0F;
        model.setRotationAngles(0, 0, 0, 0, 0, 0.0625F, clientPlayer);
        model.renderLeftArm();
    }
    
    protected ResourceLocation getEntityTexture(AbstractClientPlayer clientPlayer) {
        return DARING_DOO;
    }
}
