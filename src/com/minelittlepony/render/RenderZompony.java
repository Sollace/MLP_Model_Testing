package com.minelittlepony.render;

import java.util.List;

import com.google.common.collect.Lists;
import com.minelittlepony.model.ModelAdvancedPony;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

/**
 * Slightly modified from RenderZombie.
 * Only doing this to be able to test the pony model.
 *
 */
public class RenderZompony extends RenderBiped {
    private static final ResourceLocation zombieTextures = new ResourceLocation("minelittlepony", "textures/entity/zombie/zombie.png");
    private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("minelittlepony", "textures/entity/zombie/zombie_villager.png");
    
    private final ModelBiped normalZombieModel;
    private final ModelZombieVillager zombieVillagerModel;
    private final List zombieVillagerLayerRenderers;
    private final List normalLayerRenderers;

    public RenderZompony(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelAdvancedPony(0, false), 0.5F, 1.0F);
    	//super(renderManagerIn, new ModelUVTest(), 0.5F, 1.0F);
        LayerRenderer var2 = (LayerRenderer)layerRenderers.get(0);
        normalZombieModel = modelBipedMain;
        zombieVillagerModel = new ModelZombieVillager();
        addLayer(new LayerHeldItem(this));
        LayerBipedArmor var3 = new LayerPonyArmour(this);
        addLayer(var3);
        normalLayerRenderers = Lists.newArrayList(this.layerRenderers);

        if (var2 instanceof LayerCustomHead) {
            removeLayer(var2);
            addLayer(new LayerCustomHead(zombieVillagerModel.bipedHead));
        }

        removeLayer(var3);
        addLayer(new LayerVillagerArmor(this));
        zombieVillagerLayerRenderers = Lists.newArrayList(layerRenderers);
    }
    
	protected void rotateCorpse(EntityZombie zombie, float rotX, float rotY, float rotZ) {
	    if (zombie.isConverting()) {
	        rotY += (float)(Math.cos(zombie.ticksExisted * 3.25) * Math.PI * 0.25);
	    }
	    super.rotateCorpse(zombie, rotX, rotY, rotZ);
	}

    public void renderZombie(EntityZombie zombie, double x, double y, double z, float entityYaw, float partialTicks) {
        updateZombieModel(zombie);
        super.doRender(zombie, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getZombieTexture(EntityZombie zombie) {
        return zombie.isVillager() ? zombieVillagerTextures : zombieTextures;
    }

    private void updateZombieModel(EntityZombie zombie) {
        if (zombie.isVillager()) {
            mainModel = zombieVillagerModel;
            layerRenderers = zombieVillagerLayerRenderers;
        } else {
            mainModel = this.normalZombieModel;
            layerRenderers = this.normalLayerRenderers;
        }
        modelBipedMain = (ModelBiped)mainModel;
    }
    
    protected void rotateCorpse(EntityLivingBase zombie, float rotX, float rotY, float rotZ) {
        rotateCorpse((EntityZombie)zombie, rotX, rotY, rotZ);
    }
    
    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
        renderZombie((EntityZombie)entity, x, y, z, entityYaw, partialTicks);
    }
    
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        renderZombie((EntityZombie)entity, x, y, z, entityYaw, partialTicks);
    }
    
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        renderZombie((EntityZombie)entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return getZombieTexture((EntityZombie)entity);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return getZombieTexture((EntityZombie)entity);
    }
    
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(10, 0.1875F, 0);
    }
}