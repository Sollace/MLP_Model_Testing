package com.minelittlepony.render;

import com.minelittlepony.model.ModelAdvancedPony;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
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
	
    public RenderPony(RenderManager renderManager) {
        this(renderManager, false);
    }

    public RenderPony(RenderManager renderManager, boolean useSmallArms) {
        super(renderManager, useSmallArms);
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
    
    public ModelBase getMainModel() {
        return getPonyModel();
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
