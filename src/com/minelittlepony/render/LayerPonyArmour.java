package com.minelittlepony.render;

import java.lang.reflect.Field;
import java.util.Map;

import com.minelittlepony.model.ModelAdvancedPony;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("unchecked")
public class LayerPonyArmour extends LayerBipedArmor {
	
	protected static Map<String, ResourceLocation> armourTexturesMap;
	
	static {
		try {
			Field[] fields = LayerArmorBase.class.getDeclaredFields();
			for (Field i : fields) {
				if (i.getType() == Map.class) {
					i.setAccessible(true);
					armourTexturesMap = (Map<String, ResourceLocation>)i.get(null);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public LayerPonyArmour(RenderLivingBase<?> rendererIn) {
		super(rendererIn);
	}
	
	protected void initArmor() {
		modelLeggings = new ModelAdvancedPony(0.5F, true);
		modelArmor = new ModelAdvancedPony(1.0F, true);
    }
	
	public ItemStack getItemStackFromSlot(EntityLivingBase living, EntityEquipmentSlot slotIn) {
		ItemStack armourStack = super.getItemStackFromSlot(living, slotIn);
		if (armourStack != null && armourStack.getItem() instanceof ItemArmor) {
			ItemArmor item = (ItemArmor)armourStack.getItem();
			updateArmourResource(item);
		}
		return armourStack;
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
