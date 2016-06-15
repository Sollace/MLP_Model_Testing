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
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

/**
 * Slightly modified from RenderZombie.
 * Only doing this to be able to test the pony model.
 *
 */
public class RenderZompony extends RenderBiped<EntityZombie> {
    private static final ResourceLocation zombieTextures = new ResourceLocation("minelittlepony", "textures/entity/zombie/zombie.png");
    private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("minelittlepony", "textures/entity/zombie/zombie_villager.png");
    
    private final ModelBiped normalZombieModel;
    private final ModelZombieVillager zombieVillagerModel;
    private final List<LayerRenderer<EntityZombie>> zombieVillagerLayerRenderers;
    private final List<LayerRenderer<EntityZombie>> normalLayerRenderers;

    public RenderZompony(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelAdvancedPony(0, false), 0.5F, 1.0F);
        LayerRenderer<?> layerRenderer = layerRenderers.get(0);
        normalZombieModel = modelBipedMain;
        zombieVillagerModel = new ModelZombieVillager();
        addLayer(new LayerHeldItem(this));
        LayerBipedArmor var3 = new LayerPonyArmour(this);
        addLayer(var3);
        normalLayerRenderers = Lists.newArrayList(this.layerRenderers);

        if (layerRenderer instanceof LayerCustomHead) {
            removeLayer(layerRenderer);
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

    public void doRender(EntityZombie zombie, double x, double y, double z, float entityYaw, float partialTicks) {
        updateZombieModel(zombie);
        super.doRender(zombie, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityZombie zombie) {
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
    
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(10, 0.1875F, 0);
    }
}