package com.minelittlepony.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.VertexBuffer;

public class ModelPlane extends ModelUVMappedBox {
	
	private boolean[] sidesHidden = new boolean[6];
	
	public ModelPlane(ModelRenderer renderer, int texOffsetX, int texOffsetY, float originX, float originY, float originZ, int width, int height, int depth, float scale) {
    	this(renderer, texOffsetX, texOffsetY, originX, originY, originZ, width, height, depth, scale, renderer.mirror);
    }
    
    public ModelPlane(ModelRenderer renderer, int texOffsetX, int texOffsetY, float originX, float originY, float originZ, int width, int height, int depth, float scale, boolean mirror) {
    	super(renderer, texOffsetX, texOffsetY, originX, originY, originZ, width, height, depth, scale, mirror);
    }
    
    public ModelPlane hideAllSides() {
    	sidesHidden = new boolean[] {true,true,true,true,true,true};
    	return this;
    }
    
    public ModelPlane setSideVisibility(int side, boolean visible) {
    	sidesHidden[side] = !visible;
    	return this;
    }
    
    public void render(VertexBuffer renderer, float scale) {
        for (int i = 0; i < this.quadList.length; i++) {
        	if (!sidesHidden[i]) {
        		quadList[i].draw(renderer, scale);
        	}
        }
    }
}
