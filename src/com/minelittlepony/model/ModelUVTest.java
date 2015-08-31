package com.minelittlepony.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUVTest extends ModelBiped {
	
	private ModelRenderer box;
	private ModelUVMappedBox cube;
	
	public ModelUVTest() {
		super(0);
		box = new ModelRenderer(this, 0,0);
		cube = new ModelUVMappedBox(box, 0, 0, 0, 0, 0, 5, 5, 5, 0, false);
		box.cubeList.add(cube);
	}
	
	public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
		box.render(scale);
	}
}












