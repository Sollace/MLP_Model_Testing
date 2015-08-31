package com.minelittlepony.render;

import java.lang.reflect.Field;
import java.util.Map;

import com.minelittlepony.model.ModelAdvancedPony;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerPonyArmour extends LayerBipedArmor {
	
	protected static Map armourTexturesMap;
	
	static {
		try {
			Field[] fields = LayerArmorBase.class.getDeclaredFields();
			for (Field i : fields) {
				if (i.getType() == Map.class) {
					i.setAccessible(true);
					armourTexturesMap = (Map)i.get(null);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public LayerPonyArmour(RendererLivingEntity rendererIn) {
		super(rendererIn);
	}
	
	protected void func_177177_a() {
        field_177189_c = new ModelAdvancedPony(0.5F, true);
        field_177186_d = new ModelAdvancedPony(1.0F, true);
    }
	
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float armorSlot) {
		for (int i = 1; i < 5; i++) {
			ItemStack armourStack = getCurrentArmor(entitylivingbaseIn, i);
			if (armourStack != null && armourStack.getItem() instanceof ItemArmor) {
				ItemArmor item = (ItemArmor)armourStack.getItem();
				updateArmourResource(item);
			}
		}
		super.doRenderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, armorSlot);
		
	}
	
	private void updateArmourResource(ItemArmor item) {
		updateArmorResource(item, null);
		updateArmorResource(item, "overlay");
	}
	
	private void updateArmorResource(ItemArmor item, String extension) {
		updateArmorResource(item, true, extension);
		updateArmorResource(item, false, extension);
	}
	
    private void updateArmorResource(ItemArmor item, boolean isAdditionalLayer, String extension) {
        String path = String.format("textures/models/armor/%s_layer_%d%s.png", item.getArmorMaterial().getName(), isAdditionalLayer ? 2 : 1, extension == null ? "" : "_" + extension);
        if (!armourTexturesMap.containsKey(path)) {
            armourTexturesMap.put(path, new ResourceLocation("minelittlepony", path));
        }
    }
}
