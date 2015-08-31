package com.minelittlepony.model;

import java.lang.reflect.Field;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;

public class ModelUVMappedBox extends ModelBox {
	
	private static final Field quadsListField;
	private static final Field vertexPositionsField;
	
	static {
		vertexPositionsField = ModelBox.class.getDeclaredFields()[0];
		vertexPositionsField.setAccessible(true);
		quadsListField = ModelBox.class.getDeclaredFields()[1];
		quadsListField.setAccessible(true);
	}
	
	protected TexturedQuad[] quadList;
	private PositionTextureVertex[] vertexPositions;
	
	private final float textureWidth;
	private final float textureHeight;
	
	private final int boxDepth;
	private final int boxWidth;
	private final int boxHeight;
	
	private final boolean mirrored;
	
    public ModelUVMappedBox(ModelRenderer renderer, int texOffsetX, int texOffsetY, float originX, float originY, float originZ, int width, int height, int depth, float scale) {
    	this(renderer, texOffsetX, texOffsetY, originX, originY, originZ, width, height, depth, scale, renderer.mirror);
    }
    
    public ModelUVMappedBox(ModelRenderer renderer, int texOffsetX, int texOffsetY, float originX, float originY, float originZ, int width, int height, int depth, float scale, boolean mirror) {
    	super(renderer, texOffsetX, texOffsetY, originX, originY, originZ, width, height, depth, scale, mirror);
    	try {
			quadList = (TexturedQuad[])quadsListField.get(this);
			vertexPositions = (PositionTextureVertex[])vertexPositionsField.get(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    	textureWidth = renderer.textureWidth;
    	textureHeight = renderer.textureHeight;
    	boxDepth = depth;
    	boxWidth = width;
    	boxHeight = height;
    	mirrored = mirror;
    }
    
    public ModelUVMappedBox setNorthFaceUV(int x, int y) {//^^ Front
    	quadList[0] = new TexturedQuad(new PositionTextureVertex[] {vertexPositions[5], vertexPositions[1], vertexPositions[2], vertexPositions[6]},
    			x, y, x + boxDepth, y + boxHeight, textureWidth, textureHeight);
    	if (mirrored) quadList[0].flipFace();
    	return this;
    }
    
    public ModelUVMappedBox setSouthFaceUV(int x, int y) {//^^ back
    	quadList[1] = new TexturedQuad(new PositionTextureVertex[] {vertexPositions[0], vertexPositions[4], vertexPositions[7], vertexPositions[3]},
    			x, y, x + boxDepth, y + boxHeight, textureWidth, textureHeight);
    	if (mirrored) quadList[1].flipFace();
    	return this;
    }
    
    public ModelUVMappedBox setTopFaceUV(int x, int y) { //^^
    	quadList[2] = new TexturedQuad(new PositionTextureVertex[] {vertexPositions[5], vertexPositions[4], vertexPositions[0], vertexPositions[1]},
    			x, y, x + boxWidth, y + boxDepth, textureWidth, textureHeight);
    	if (mirrored) quadList[2].flipFace();
    	return this;
    }
    
    public ModelUVMappedBox setBottomFaceUV(int x, int y) {//^^
    	quadList[3] = new TexturedQuad(new PositionTextureVertex[] {vertexPositions[2], vertexPositions[3], vertexPositions[7], vertexPositions[6]},
    			x, y + boxDepth, x + boxWidth, y, textureWidth, textureHeight);
    	if (mirrored) quadList[3].flipFace();
    	return this;
    }
    
    public ModelUVMappedBox setEastFaceUV(int x, int y) {//^^ left
    	quadList[4] = new TexturedQuad(new PositionTextureVertex[] {vertexPositions[1], vertexPositions[0], vertexPositions[3], vertexPositions[2]},
    			x, y, x + boxWidth, y + boxHeight, textureWidth, textureHeight);
    	if (mirrored) quadList[4].flipFace();
    	return this;
    }
    
    public ModelUVMappedBox setWestFaceUV(int x, int y) {//^^ Right
    	quadList[5] = new TexturedQuad(new PositionTextureVertex[] {vertexPositions[4], vertexPositions[5], vertexPositions[6], vertexPositions[7]},
    			x, y, x + boxWidth, y + boxHeight, textureWidth, textureHeight);
    	if (mirrored) quadList[5].flipFace();
    	return this;
    }
    
    /*public void ModelBox(ModelRenderer renderer, int texOffsetX, int texOffsetY, float originX, float originY, float originZ, int width, int height, int depth, float scale, boolean mirror) {
        this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[6];
        float var12 = originX + (float)width;
        float var13 = originY + (float)height;
        float var14 = originZ + (float)depth;
        originX -= scale;
        originY -= scale;
        originZ -= scale;
        var12 += scale;
        var13 += scale;
        var14 += scale;

        if (mirror)
        {
            float var15 = var12;
            var12 = originX;
            originX = var15;
        }

        PositionTextureVertex var24 = new PositionTextureVertex(originX, originY, originZ, 0.0F, 0.0F);
        PositionTextureVertex var16 = new PositionTextureVertex(var12, originY, originZ, 0.0F, 8.0F);
        PositionTextureVertex var17 = new PositionTextureVertex(var12, var13, originZ, 8.0F, 8.0F);
        PositionTextureVertex var18 = new PositionTextureVertex(originX, var13, originZ, 8.0F, 0.0F);
        PositionTextureVertex var19 = new PositionTextureVertex(originX, originY, var14, 0.0F, 0.0F);
        PositionTextureVertex var20 = new PositionTextureVertex(var12, originY, var14, 0.0F, 8.0F);
        PositionTextureVertex var21 = new PositionTextureVertex(var12, var13, var14, 8.0F, 8.0F);
        PositionTextureVertex var22 = new PositionTextureVertex(originX, var13, var14, 8.0F, 0.0F);
        this.vertexPositions[0] = var24;
        this.vertexPositions[1] = var16;
        this.vertexPositions[2] = var17;
        this.vertexPositions[3] = var18;
        this.vertexPositions[4] = var19;
        this.vertexPositions[5] = var20;
        this.vertexPositions[6] = var21;
        this.vertexPositions[7] = var22;
        this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] {var20, var16, var17, var21}, texOffsetX + depth + width, texOffsetY + depth, texOffsetX + depth + width + depth, texOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] {var24, var19, var22, var18}, texOffsetX, texOffsetY + depth, texOffsetX + depth, texOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] {var20, var19, var24, var16}, texOffsetX + depth, texOffsetY, texOffsetX + depth + width, texOffsetY + depth, renderer.textureWidth, renderer.textureHeight);
        this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] {var17, var18, var22, var21}, texOffsetX + depth + width, texOffsetY + depth, texOffsetX + depth + width + width, texOffsetY, renderer.textureWidth, renderer.textureHeight);
        this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] {var16, var24, var18, var17}, texOffsetX + depth, texOffsetY + depth, texOffsetX + depth + width, texOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);
        this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] {var19, var20, var21, var22}, texOffsetX + depth + width + depth, texOffsetY + depth, texOffsetX + depth + width + depth + width, texOffsetY + depth + height, renderer.textureWidth, renderer.textureHeight);

        if (mirror)
        {
            for (int var23 = 0; var23 < this.quadList.length; ++var23)
            {
                this.quadList[var23].flipFace();
            }
        }
    }*/
}
