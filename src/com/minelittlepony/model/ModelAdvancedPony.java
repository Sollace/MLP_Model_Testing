package com.minelittlepony.model;

import com.minelittlepony.main.FlyState;
import com.minelittlepony.main.Race;
import com.minelittlepony.main.TailLength;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
 
/**
 * AdvancedPony - Lyra_Hartstrings
 * Created using Tabula 4.1.0
 * Modified by Sollace
 */
public class ModelAdvancedPony extends ModelBiped {
    
	private final boolean isArmour;
	
	public float rock = 0, roll = 0;
    private float hoverMovement = 0;
	
	public Race entityRace;
	public FlyState entityFlyState;
	
	public boolean elytra = false;
	
	//90 degree angle. Used for setting positions
	private static final float QUARTER = (float)Math.PI/2;
	
	//Top level boxes
    private ModelRenderer head;
    private ModelRenderer neck;
	private ModelRenderer torso_front;
	
	private ModelRenderer left_wing_closed, right_wing_closed;
	private ModelRenderer left_wing_bat, right_wing_bat;
	private ModelRenderer left_wing_pega, right_wing_pega;
    private ModelRenderer left_wing_flat, right_wing_flat;
	//
	
    private ModelRenderer horn;
	private ModelRenderer hair;
	
    private ModelRenderer snuzzle_female;
    private ModelRenderer snuzzle_male;
    
    private ModelRenderer leg_front_left;
    private ModelRenderer leg_front_right;
    
    private ModelRenderer knee_front_left;
    private ModelRenderer knee_front_right;
    
    private ModelRenderer knee_back_left;
    private ModelRenderer knee_back_right;
    
    private ModelRenderer leg_back_left;
    private ModelRenderer leg_back_right;
    
    private ModelRenderer tail_stub;
    private ModelRenderer tail_segment_1;
    private ModelRenderer tail_segment_2;
    private ModelRenderer tail_segment_3;
    
    private ModelRenderer ear_flare_left;
    private ModelRenderer ear_flare_right;
    
    public ModelAdvancedPony(float scale, boolean armour) {
        textureWidth = textureHeight = 64;
        isArmour = armour;
        init(scale);
    }
    
    private void init(float scale) {
    	if (torso_front != null) return;
    	bipedBody = torso_front = box();
    	addPlane(torso_front, 8, 24, -4, -3.5f, 4, 8, 7, 4, scale, 0, 8.5f, -6)
    	.setSideVisibility(5, false).setSideVisibility(4, false).setTopFaceUV(8, 32).setBottomFaceUV(8, 40).setSouthFaceUV(36, 20).setNorthFaceUV(32, 20);
    	
    	addPlane(torso_front, 8, 24, -4, -3.5f, 0, 8, 7, 4, scale, 0, 8.5f, -6)
    	.setSideVisibility(5, false).setTopFaceUV(8, 36).setBottomFaceUV(8, 44).setEastFaceUV(20, 20).setSouthFaceUV(16, 20).setNorthFaceUV(28, 20);
    	
    	ModelRenderer TorsoRear = box(torso_front);
    	addPlane(TorsoRear, 8, 32, -4, -4, 0, 8, 8, 4, scale, 0, 0, 8)
    	.setSideVisibility(5, false).setTopFaceUV(0, 36).setBottomFaceUV(0, 44).setEastFaceUV(20, 36).setSouthFaceUV(16, 32).setNorthFaceUV(28, 32);
    	
    	addPlane(TorsoRear, 8, 32, -4, -4, 4, 8, 8, 4, scale, 0, 0, 8)
    	.setSideVisibility(4, false).setTopFaceUV(0, 32).setBottomFaceUV(0, 40).setWestFaceUV(32, 36).setSouthFaceUV(16, 44).setNorthFaceUV(28, 44);
        
        addHead(scale);
        addTail(scale, TorsoRear);
        addLegs(scale, TorsoRear);
        addWings(scale);
    }
    
    private void addHead(float scale) {
    	neck = rotate(addBox(box(torso_front), 0, 54, -2, -6, -2, 4, 6, 4, scale, 0, -1, 2), 0.17453292519943295f, 0, 0);
        bipedHead = head = rotate(addBox(box(neck), 0, 0, -4, -8, -5, 8, 8, 8, scale, 0, -5, 0), -0.17453292519943295f, 0, 0);
        
        ModelRenderer earLeft = addBox(box(head), 6, 51, 0, -3, -1, 2, 4, 3, scale, 2.5F, -7, 1.6f); //Left Ear
        ear_flare_left = rotate(offset(addBox(box(earLeft), 4, 2, 0, 0, 0, 1, 2, 1, scale, 0, 0, 0), 0.03f, -0.25f, 0.13f), -QUARTER/3, 0, 0);
        
        ModelRenderer earRight = addBox(box(head), 6, 51, -2, -3, -1, 2, 4, 3, scale, -2.5f, -7, 1.6f); //Right Ear
        ear_flare_right = rotate(offset(addBox(box(earRight), 4, 2, 0, 0, 0, 1, 2, 1, scale, 0, 0, 0), -0.09f, -0.25f, 0.13f), -QUARTER/3, 0, 0);
        
        snuzzle_female = addBox(box(head), 9, 13, -2, -1, -1, 4, 1, 1, scale, 0, -1, -5);
        addBox(box(snuzzle_female), 10, 12, -1, -2, 0, 2, 3, 1, scale, 0, 0, -1); //F Snuzzle 2
        
        snuzzle_male = addBox(box(head), 9, 12, -2, -1.5f, -1, 4, 3, 1, scale, 0, -1.5f, -5); // Male Snuzzle
        
        horn = rotate(addBox(box(head), 0, 2, -0.5f, -5, 6, 1, 5, 1, scale, 0, -4.6f, -10), 0.5235987755982988f, 0, 0);
        
        bipedHeadwear = hair = rotate(addBox(box(neck), 32, 0, -4, -8, -5, 8, 8, 8, scale + 0.51f, 0, -5, 0), -0.17453292519943295f, 0, 0);
    }
    
    private void addTail(float scale, ModelRenderer torsoRear) {
        tail_stub = rotate(addBox(box(torsoRear), 1, 48, -1, -4, -1, 2, 4, 2, scale, 0, -3, 7), -1.05f, 0, 0);
        
        tail_segment_1 = rotate(box(tail_stub), 1.1f, 0, 0);
        addBoxUV(tail_segment_1, 24, -4, -2, -0.5f, -2f, 4, 4, 4, scale, 0, -4f, -0.5f).setTopFaceUV(16, 16).setBottomFaceUV(36, 16);
        tail_segment_1.rotateAngleX = 1.1f;
        tail_segment_2 = box(tail_segment_1);
        addBoxUV(tail_segment_2, 24, 0, -2, 3.5f, -2, 4, 4, 4, scale, 0, 0, 0).setTopFaceUV(16, 16).setBottomFaceUV(36, 16);
        tail_segment_3 = box(tail_segment_2);
        addBoxUV(tail_segment_3, 20, 12, -2, 7.5f, -2, 4, 4, 4, scale, 0, 0, 0).setTopFaceUV(16, 16).setBottomFaceUV(36, 16);
    }
    
    private void addLegs(float scale, ModelRenderer torsoRear) {
    	bipedLeftArm = leg_front_left = addBox(box(torso_front), 32, 48, -2, -1, -2, 4, 7, 4, scale, 2.5f, 3.5f, 3);
    	knee_front_left = offset(addBox(box(leg_front_left), 16, 52, 0, 0, 0, 4, 1, 4, scale - 0.001f, 2, 6, 2), -0.25f, 0, -0.25f); // Left Front Knee
    	addBoxUV(box(knee_front_left), 32, 55, 0, 0, 0, 4, 5, 4, scale, 0, 1, 0).setBottomFaceUV(40, 48); // Left Front Down*/
    	
        bipedRightArm = leg_front_right = addBox(box(torso_front), 40, 16, -2, -1, -2, 4, 7, 4, scale, -2.5f, 3.5f, 3);
        knee_front_right = addBox(box(leg_front_right), 0, 20, 0, 0, 0, 4, 1, 4, scale - 0.001f, -2, 6, -2); // Right Front Knee
    	addBoxUV(box(knee_front_right), 40, 23, 0, 0, 0, 4, 5, 4, scale, 0, 1, 0).setBottomFaceUV(48, 16); // Right Front Down
    	
        bipedLeftLeg = leg_back_left = box(torsoRear);
        addBoxUV(leg_back_left, 17, 48, -1.5f, 0, -2, 3, 5, 4, scale, 2f, 4, 5.5f).setTopFaceUV(21, 48).setBottomFaceUV(25, 48);
        knee_back_left = box(leg_back_left);
        addBoxUV(knee_back_left, 16, 52, -2, 0, -2, 4, 8, 4, scale, 0.1f, 3.5f, 1).setTopFaceUV(20, 48).setBottomFaceUV(24, 48); //Left Back Down
        
        bipedRightLeg = leg_back_right = box(torsoRear);
        addBoxUV(leg_back_right, 1, 16, -1.5f, 0, -2, 3, 5, 4, scale, -2F, 4, 5.5f).setTopFaceUV(4, 16).setBottomFaceUV(8, 16);
        knee_back_right = box(leg_back_right);
        addBoxUV(knee_back_right, 0, 20, -2, 0, -2, 4, 8, 4, scale, -0.1f, 3.5f, 1).setTopFaceUV(4, 16).setBottomFaceUV(8, 16); // Right Back Down
    }
    
    private void addWings(float scale) {
        left_wing_closed = addBox(box(torso_front), 49, 48, 0, -3.5f, 0, 2, 6, 5, scale, 4, 0, 4);
        addBox(box(left_wing_closed), 32, 30, -1, -3, 0, 2, 4, 2, scale, 1, 0.5f, 5); // Left Wing 2 //32,26
        
        right_wing_closed = addBox(mirror(torso_front), 41, 32, 0, -3.5f, 0, 2, 6, 5, scale, -6, 0, 4);
        addBox(mirror(right_wing_closed), 20, 30, -1, -3, 0, 2, 4, 2, scale, 1, 0.5f, 5); // Right Wing 2 //32,20
        
        addFlatWings(scale);
        addBatWings(scale);
        addPegaWings(scale);
    }
    
    private void addFlatWings(float scale) {
    	float wingAngle = 0.09F;
    	left_wing_flat = rotate(box(), 0, wingAngle, wingAngle);
    	addPlane(left_wing_flat, 40, 16, 0, 0, -3, 8, 0, 16, scale, 3.5f, 7, -0.5f).hideAllSides().setSideVisibility(2, true);
    	
    	right_wing_flat = rotate(mirror(), 0, -wingAngle, -wingAngle);
    	addPlane(right_wing_flat, 40, 16, -8, 0, -3, 8, 0, 16, scale, -3.5F, 7, -0.5f).hideAllSides().setSideVisibility(2, true);
    }
    
    private void addBatWings(float scale) {
    	left_wing_bat = rotate(addBox(box(), 56, 39, -0.45f, 0, -0.5f, 1, 6, 1, scale, 4, 7, -0.5F), -0.27f, 0f, -1.56f);
    	ModelRenderer BatwingLeft2 = rotate(addBox(box(left_wing_bat), 56, 39, -6.4f, 4.8f, -4.1f, 1, 6, 1, scale, 6, 0, 0), 0.6373942428283291f, 0, 0);
    	
    	rotate(addBox(box(left_wing_bat), 56, 39, -6.4f, -5.1F, 5.45f, 1, 6, 1, scale, 6, 0, 0.5F), -1.42f, 0, 0); // Bat Stick 1
    	rotate(addBox(box(BatwingLeft2), 56, 39, -12.4f, 2.8f, -11.5f, 1, 6, 1, scale, 6, 0, 0), 0.99f, 0, 0); // Bat Stick 2
    	addPlane(rotate(box(left_wing_bat), 0f, -1.3F, -1.57f), 38, 16, -1, 0, 0, 7, 0, 12, scale + 0.001f, 0, 0, 0).hideAllSides().setSideVisibility(3, true); // Left Leather
    	
    	right_wing_bat = rotate(addBox(box(), 60, 39, -0.45f, 0, -0.5f, 1, 6, 1, scale, -4, 7, -0.5f), -0.27f, 0f, 1.56f);
    	ModelRenderer BatwingRight2 = rotate(addBox(box(right_wing_bat), 60, 39, -6.4f, 4.8f, -4.1f, 1, 6, 1, scale, 6, 0, 0), 0.6373942428283291f, 0, 0);
    	
    	rotate(addBox(box(right_wing_bat), 60, 39, -6.4f, -5.1F, 5.45F, 1, 6, 1, scale, 6, 0, 0.5f), -1.52f, 0, 0); // Bat Stick 1
    	rotate(addBox(box(BatwingRight2), 60, 39, -12.4f, 2.8f, -11.5f, 1, 6, 1, scale, 6, 0, 0), 0.99f, 0, 0); // Bat Stick 2
    	addPlane(rotate(box(right_wing_bat), 0f, -1.3f, -1.57f), 45, 16, -1, 0, 0, 7, 0, 12, scale + 0.001f, 0, 0, 0).hideAllSides().setSideVisibility(2, true); // Right Leather
    }
    
    private void addPegaWings(float scale) {
    	left_wing_pega = rotate(addBox(box(), 56, 36, 0, 0, -0.5f, 1, 5, 2, scale, 4.25f, 7.5f, -0.5F), -0.27314402793711257f, 0, -1.85f);
    	ModelRenderer PegawingLeft2 = rotate(addBox(box(left_wing_pega), 56, 36, 0, 0, 0, 1, 5, 2, scale, 0f, 5f, -0.45f), -0.4553564018453205f, 0, -0.4553564018453205f);
    	
    	rotate(addBox(box(PegawingLeft2), 58, 16, 0, 0, 0, 1, 8, 2, scale, 0.1f, 4f, 0), 0.9105382707654417f, 0, 0); // Feather Left 1
    	rotate(addBox(box(PegawingLeft2), 58, 16, 0, 0, 0, 1, 8, 2, scale, 0.1f, 1.4f, 0), 1.0016444577195458f, 0, 0); // Feather Left 2
    	rotate(addBox(box(left_wing_pega), 58, 26, 0, 0, 0, 1, 8, 2, scale + 0.1f, 0, 3.8f, 0), 0.6373942428283291f, 0, -0.22759093446006054f); // Feather Left 3
    	rotate(addBox(box(left_wing_pega), 58, 26, 0, 0, 0, 1, 8, 2, scale, 0.2f, 0.6f, 0), 0.6829473363053812f, 0, 0); // Feather Left 4
    	
    	right_wing_pega = rotate(addBox(box(), 56, 1, 0, 0, -0.5f, 1, 5, 2, scale, -4.15f, 7.5f, -1.0F), 0.01f, 0f, 1.85f);
    	ModelRenderer PegawingRight2 = rotate(addBox(box(right_wing_pega), 56, 1, 0, 0, 0, 1, 5, 2, scale, 0.2f, 4.3f, -0.4f), -0.4553564018453205f, 0, 0.4553564018453205f);
    	
    	rotate(addBox(box(PegawingRight2), 58, 16, 0, 1, -0.5f, 1, 8, 2, scale, 0.1f, 4, 0), 0.9105382707654417f, 0, 0); // Feather Right 1
    	rotate(addBox(box(PegawingRight2), 58, 16, 0, 1, -0.5f, 1, 8, 2, scale, 0.1f, 1.4f, 0), 1.0016444577195458f, 0, 0); //Feather Right 2
    	rotate(addBox(box(right_wing_pega), 58, 26, 0, 1, -0.5f, 1, 8, 2, scale, 0, 3.8f, 0), 0.6373942428283291f, 0, 0.22759093446006054f); // Feather Right 3
    	rotate(addBox(box(right_wing_pega), 58, 26, 0, 1, -0.5f, 1, 8, 2, scale, 0.1f, 0.6f, 0), 0.6829473363053812f, 0, 0); // Feather Right 4
    }
    
    @Override
    public void render(Entity entity, float time, float walkSpeed, float stutter, float yaw, float pitch, float scale) {
    	updateState(entity);
    	setPonyRotationAngles(time, walkSpeed, stutter, yaw, pitch, scale, entity);
    	GlStateManager.pushMatrix();
    	
        boolean male = isEntityMale(entity);
        snuzzle_female.isHidden = male;
    	snuzzle_male.isHidden = !male;
    	
    	horn.isHidden = !entityRace.canCast();
    	ear_flare_left.isHidden = entityRace != Race.THESTRAL;
    	ear_flare_right.isHidden = entityRace != Race.THESTRAL;
    	left_wing_closed.isHidden = right_wing_closed.isHidden = !entityRace.canFly() || entityFlyState != FlyState.LANDED;
    	
    	setHairVisibility(entity);
    	setTailLength(entity);
    	
    	if (entityFlyState == FlyState.LANDED && isSneak && !isRiding) {
            GlStateManager.translate(0, 4.5F * scale, 0);
        }
    	
    	
        float ageRatio = getScale(entity);
        if (ageRatio != 1) {
            GlStateManager.scale(1/ageRatio, 1/ageRatio, 1/ageRatio);
            GlStateManager.translate(0, 24.0F * scale, 0);
        }
        
        boolean sleep = isEntityAsleep(entity);
        if (sleep) {
        	GlStateManager.translate(0, 9 * scale, -10 * scale);
        	GlStateManager.rotate(90, 1, 0, 0);
        } else {
            if (isRiding) {
            	GlStateManager.translate(0, -8.0F * scale, 0);
        	}
        	if (entityFlyState == FlyState.HOVERING) {
        		hoverMovement = MathHelper.cos(stutter * 0.57F) * scale;
        		GlStateManager.translate(0, hoverMovement, 0);
        	}
        	
        	if (entityFlyState == FlyState.DASHING && entity instanceof EntityPlayer) {
        		float strafing = ((EntityPlayer)entity).moveStrafing;
        		if (strafing != 0) {
        			if (Math.abs(rock) < Math.abs(strafing)) {
        				rock += strafing/20;
        			}
        		} else {
        			rock *= 0.8;
        		}
        		GlStateManager.rotate(rock * 20, 0, 0, 1);
        	} else {
        		rock = 0;
        		roll = 0;
        	}
        	
        	if (!elytra && entityFlyState != FlyState.LANDED) {
        		double strafing = entity.motionY * 50;
        		if (strafing != 0) {
        			if (Math.abs(roll) < Math.abs(strafing)) {
        				roll += strafing;
        			}
        		} else {
        			roll *= 0.8;
        		}
        		GlStateManager.rotate(-roll, 1, 0, 0);
        	} else {
        		roll = 0;
        	}
        }
    	
        torso_front.render(scale);
    	if (!sleep && entityFlyState != FlyState.LANDED) {
        	getLeftWing().render(scale);
        	getRightWing().render(scale);
    	}
    	
    	GlStateManager.popMatrix();
    }
    
    private void setTailLength(Entity entity) {
    	if (isArmour) {
    		tail_stub.isHidden = true;
    		return;
    	}
    	TailLength tailLength = getEntityTailLength(entity);
    	tail_segment_1.isHidden = tailLength == TailLength.QUARTER;
    	tail_segment_2.isHidden = tailLength == TailLength.HALF;
    	tail_segment_3.isHidden = tailLength == TailLength.THREE_QUARTER;
    }
    
    private void setHairVisibility(Entity entity) {
    	if (isArmour) {
    		hair.isHidden = false;
    		return;
    	}
    	if (entity instanceof EntityPlayer) {
    		hair.isHidden = !((EntityPlayer)entity).isWearing(EnumPlayerModelParts.HAT);
    	} else {
    		hair.isHidden = true;
    	}
    }
    
    public void setRotationAngles(float time, float walkSpeed, float stutter, float yaw, float pitch, float increment, Entity entity) {
    	
    }
    
    private void setPonyRotationAngles(float time, float walkSpeed, float stutter, float yaw, float pitch, float increment, Entity entity) {
    	resetAngles();
    	
    	head.rotateAngleY = yaw / 57.29578F;
    	head.rotateAngleX = pitch / 57.29578F;
    	
		tail_stub.rotateAngleZ = (MathHelper.cos(time * 0.6662F) * 1.4F * walkSpeed)/14;
		tail_stub.rotateAngleY = (MathHelper.cos(time * 0.6662F + (float)Math.PI) * 1.4F * walkSpeed)/5; 
    	tail_segment_1.rotateAngleY = (MathHelper.sin(time * 0.6662F + (float)Math.PI) * 1.4F * walkSpeed)/10;
    	
		if (isEntityAsleep(entity)) {
			leg_back_left.rotateAngleX = QUARTER;
			leg_back_right.rotateAngleX = QUARTER;
			leg_front_left.rotateAngleX = -QUARTER;
			leg_front_right.rotateAngleX = -QUARTER;
			head.rotateAngleX = 0;
		} else if (isRiding) {
			torso_front.rotateAngleX = -QUARTER/2;
			leg_back_left.rotateAngleX = -QUARTER/2 + 0.3f;
			leg_back_left.rotateAngleY -= QUARTER/4;
			leg_back_left.rotateAngleZ -= 0.3f;
    		leg_back_right.rotateAngleX = -QUARTER/2 + 0.3f;
    		leg_back_right.rotateAngleY += QUARTER/4;
			leg_back_right.rotateAngleZ += 0.3f;
			leg_front_left.rotateAngleX += QUARTER/2;
			leg_front_right.rotateAngleX += QUARTER/2;
			neck.rotateAngleX += QUARTER/2;
			tail_stub.rotateAngleX += QUARTER;
		} else {
			if (entityFlyState == FlyState.LANDED) {
				leg_back_right.rotateAngleX = MathHelper.cos(time * 0.6662F) * 1.4F * walkSpeed;
				leg_back_left.rotateAngleX = MathHelper.cos(time * 0.6662F + (float)Math.PI) * 1.4F * walkSpeed;
				if (entity instanceof EntityZombie) {
					if (yaw > Math.PI/4) {
						holdUp(leg_front_right, stutter);
				    	leg_front_left.rotateAngleX = -(leg_back_right.rotateAngleX);
					} else {
						leg_front_right.rotateAngleX = -(leg_back_left.rotateAngleX);
						holdUp(leg_front_left, stutter);
					}
				} else {
			    	leg_front_right.rotateAngleX = -(leg_back_left.rotateAngleX);
			    	leg_front_left.rotateAngleX = -(leg_back_right.rotateAngleX);
				}
				
				if (isSneak) {
					leg_front_left.rotateAngleX -= 1;
					leg_front_left.rotateAngleY -= 0.5f;
					leg_front_right.rotateAngleX -= 1;
					leg_front_right.rotateAngleY += 0.5f;
					leg_back_left.rotateAngleX -= 2;
					leg_back_left.rotateAngleY -= 0.5f;
					leg_back_right.rotateAngleX -= 2;
					leg_back_right.rotateAngleY += 0.5f;
					knee_front_left.rotateAngleX += 1;
					knee_front_right.rotateAngleX += 1;
					knee_back_left.rotateAngleX += 2;
					knee_back_right.rotateAngleX += 2;
				} else if (entity.isSprinting()) {
					neck.rotateAngleX += QUARTER/4;
					head.rotateAngleX -= QUARTER/3;
				}
				
				leg_front_right.rotateAngleY = 0.0F;
				leg_front_right.rotateAngleZ = 0.0F;
				
				if (!entityRace.canCast()) {
					switch (leftArmPose) {
			            case EMPTY:
			                break;
			            case BLOCK:
			            case ITEM:
			            	knee_front_left.rotateAngleX = 0;
			            	leg_front_left.rotateAngleX = leg_front_left.rotateAngleX/3 - (float)Math.PI/10;
			            	leg_front_left.rotateAngleZ = -((float)Math.PI/30);
		                default:
			        }
					
					switch (rightArmPose) {
			            case EMPTY:
			                break;
			            case BLOCK:
			            case ITEM:
			            	knee_front_right.rotateAngleX = 0;
			            	leg_front_right.rotateAngleX = leg_front_right.rotateAngleX/3 - (float)Math.PI/10;
			            	leg_front_right.rotateAngleZ = (float)Math.PI/20;
		                default:
			        }
				}
	    	} else if (entityFlyState == FlyState.HOVERING) {
	    		float flyLegAngle = -QUARTER/2;
	    		float waveLeft = MathHelper.sin(stutter * 0.47F) * 0.1f;
	    		float waveRight = MathHelper.sin(stutter * 0.48F) * 0.1f;
	    		
	    		leg_front_left.rotateAngleX = flyLegAngle - waveLeft;
	    		leg_front_right.rotateAngleX = flyLegAngle - waveRight;
	    		
	    		leg_back_left.rotateAngleX = (flyLegAngle / 2) - waveLeft;
	    		leg_back_right.rotateAngleX = (flyLegAngle / 2) - waveRight;
	    		
	    		flyLegAngle = (float)Math.PI/2.5f;
	    		
	    		knee_front_right.rotateAngleX = flyLegAngle + waveRight;
	    		knee_front_left.rotateAngleX = flyLegAngle - waveLeft;
	    		
	    		knee_back_right.rotateAngleX = flyLegAngle + waveRight;
	    		knee_back_left.rotateAngleX = flyLegAngle - waveLeft;
	    	} else {
	    		holdUp(leg_front_right, 1);
	    		holdUp(leg_front_left, 1);
	    		leg_back_right.rotateAngleX = QUARTER;
				leg_back_left.rotateAngleX = QUARTER;
	    		tail_segment_1.rotateAngleX += QUARTER;
	    		tail_stub.rotateAngleZ = 0;
	    		tail_stub.rotateAngleY = 0;
	    	}
		}
		
    	if (!entityRace.canCast() && swingProgress > -9990) {
    		updateArmSwing(leg_front_right);
    	}
    	
    	if (!entityRace.canCast()) {
    		if (rightArmPose == ArmPose.BOW_AND_ARROW) {
    			knee_front_right.rotateAngleX = 0;
        		leg_front_right.rotateAngleX = -QUARTER - 0.1f;
        		leg_front_right.rotateAngleY = -0.1f;
    		} else if (leftArmPose == ArmPose.BOW_AND_ARROW) {
    			knee_front_left.rotateAngleX = 0;
        		leg_front_left.rotateAngleX = -QUARTER - 0.1f;
        		leg_front_left.rotateAngleY = -0.1f;
    		}
    	}
    	
    	if (entityFlyState != FlyState.LANDED) {
    		float angle = MathHelper.sin(stutter - 0.5f);
    		if (entityFlyState == FlyState.HOVERING) {
    			angle = MathHelper.sin(stutter * 0.57F);
    		}
    		
	        left_wing_flat.rotateAngleZ = angle;
	    	left_wing_pega.rotateAngleZ = angle - 1;
	    	left_wing_bat.rotateAngleZ = angle - 1;
	    	
	    	right_wing_flat.rotateAngleZ = -angle;
	        right_wing_pega.rotateAngleZ = 1 - angle;
	        right_wing_bat.rotateAngleZ = 1 - angle;
    	}
    	
        copyModelAngles(head, hair);
    }
    
    /**
     * Place all the parts back in their neutral locations
     */
    private void resetAngles() {
    	torso_front.rotateAngleX = 0;
    	leg_front_left.rotateAngleX = leg_front_right.rotateAngleX = 0;
    	leg_front_left.rotateAngleY = leg_front_right.rotateAngleY = 0;
    	leg_front_left.rotateAngleZ = leg_front_right.rotateAngleZ = 0;
    	leg_back_left.rotateAngleX = leg_front_left.rotateAngleX;
    	leg_back_right.rotateAngleX = leg_front_right.rotateAngleX;
    	leg_back_right.rotateAngleY = leg_back_left.rotateAngleY = 0;
    	leg_back_right.rotateAngleZ = leg_back_left.rotateAngleZ = 0;
		knee_front_left.rotateAngleX = knee_front_right.rotateAngleX = 0;
		knee_back_left.rotateAngleX = knee_back_right.rotateAngleX = 0;
		tail_stub.rotateAngleX = -1.05f;
		tail_segment_1.rotateAngleX = 1.1f;
		tail_segment_2.rotateAngleX = tail_segment_3.rotateAngleX = 0;
		neck.rotateAngleX = 0.17453292519943295f;
    }
    
    /**
     * We don't know what's going to happen in 1.9 but it's definately going to involve both arms being able to swing.
     * @param arm	The arm to swing
     */
    private void updateArmSwing(ModelRenderer arm) {
    	float var8 = 1.0F - swingProgress;
        var8 = 1 - (var8 * var8 * var8);
        float var9 = MathHelper.sin(var8 * (float)Math.PI);
        float var10 = MathHelper.sin(swingProgress * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
        float swing = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float)Math.PI * 2.0F) * 0.2F;
        arm.rotateAngleX -= (float)(var9 * 1.2 + var10);
        arm.rotateAngleY += swing * 2.0F;
        arm.rotateAngleZ += MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F;
    }
    
    public void setInvisible(boolean invisible) {
        head.showModel = invisible;
        horn.showModel = invisible;
    	torso_front.showModel = invisible;
    	left_wing_closed.showModel = invisible;
    	right_wing_closed.showModel = invisible;
    	left_wing_bat.showModel = invisible;
    	right_wing_bat.showModel = invisible;
    	left_wing_pega.showModel = invisible;
    	right_wing_pega.showModel = invisible;
        left_wing_flat.showModel = invisible;
        right_wing_flat.showModel = invisible;
    }
    
    public void postRenderArm(float scale, EnumHandSide side) {
    	ModelRenderer arm = getArmForSide(side);
    	if (!entityRace.canCast()) {
    		if (entityFlyState == FlyState.HOVERING) {
    			GlStateManager.translate(0.0625F, hoverMovement + 0.7675F, -0.1325F);
    		} else {
    			GlStateManager.translate(0.0625F, 0.6675F, -0.5325F);
    		}
    	} else {
    		GlStateManager.translate(0.0625F, 0, -0.4525F);
    		GlStateManager.rotate(-45, 1, 0, 0);
    		arm.rotateAngleX = 0;
    		if (swingProgress > -9990) updateArmSwing(arm);
    	}
    	arm.postRender(scale);
    }
    
    public void renderLeftArm() {
    	resetAngles();
    	leg_front_left.render(0.0625F);
    }
    
    public void renderRightArm() {
    	resetAngles();
    	leg_front_right.render(0.0625F);
    }
    
    /**
     * Larger value -> smaller model
     */
    private float getScale(Entity entity) {
    	if (entity instanceof EntityPlayer) {
    		//return ???
    	}
    	return isChild ? 2 : 1;
    }
        
    private boolean isEntityMale(Entity entity) {
    	return false;
    }
    
    private boolean isEntityAsleep(Entity entity) {
    	return entity instanceof EntityPlayer && ((EntityPlayer)entity).isPlayerSleeping();
    }
    
    public void updateState(Entity entity) {
    	entityRace = getEntityRace(entity);
		entityFlyState = getEntityFlyState(entity);
    }
    
    private Race getEntityRace(Entity entity) {
    	if (entity instanceof EntityZombie) {
    		return Race.THESTRAL;
    	}
    	return Race.PEGASUS;
    }
    
    private FlyState getEntityFlyState(Entity entity) {
    	if (entityRace.canFly() && !entity.onGround && !isRiding || (entity instanceof EntityPlayer && ((EntityPlayer)entity).isElytraFlying())) {
    		if ((entity instanceof EntityPlayer && ((EntityPlayer)entity).isElytraFlying())) {
    			return FlyState.DASHING;
    		}
    		if ((entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isFlying) || !isEntityOnFluid(entity)) {
	    		if (MathHelper.sqrt_double(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ) > 0.5f) {
	    			return FlyState.DASHING;
	    		}
	    		return FlyState.HOVERING;
    		}
    	}
    	return FlyState.LANDED;
    }
    
    private boolean isEntityOnFluid(Entity entity) {
    	int yy = MathHelper.floor_double(entity.posY - 0.1d - entity.getYOffset() - (entity.onGround ? 0d : 0.25d));
    	return entity.isInWater() || entity.isInLava() || entity.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(entity.posX), yy, MathHelper.floor_double(entity.posZ))).getMaterial().isLiquid();
    }
    
    private TailLength getEntityTailLength(Entity entity) {
    	return TailLength.FULL;
    }
    
    public ModelRenderer getLeftWing() {
    	switch (entityRace) {
	    	case ALICORN:
	    	case PEGASUS: return left_wing_pega;
	    	case THESTRAL: return left_wing_bat;
	    	case BUTTERFLY: return left_wing_flat;
	    	default: return null;
    	}
    }
    
    public ModelRenderer getRightWing() {
    	switch (entityRace) {
	    	case ALICORN:
	    	case PEGASUS: return right_wing_pega;
	    	case THESTRAL: return right_wing_bat;
	    	case BUTTERFLY: return right_wing_flat;
	    	default: return null;
    	}
	}
    
    private void holdUp(ModelRenderer arm, float stutter) {
    	arm.rotateAngleX = -QUARTER - MathHelper.sin(stutter * 0.067F) * 0.1f;
    }
        
    private ModelRenderer offset(ModelRenderer model, float x, float y, float z) {
    	model.offsetX = x;
    	model.offsetY = y;
    	model.offsetZ = z;
    	return model;
    }
    
    private ModelRenderer rotate(ModelRenderer model, float x, float y, float z) {
    	model.rotateAngleX = x;
    	model.rotateAngleY = y;
    	model.rotateAngleZ = z;
        return model;
    }
    
    private ModelRenderer addBox(ModelRenderer box, int texOffsetX, int texOffsetY, float oriX, float oriY, float oriZ, int width, int height, int depth, float scale, float rotX, float rotY, float rotZ) {
    	box.setTextureOffset(texOffsetX, texOffsetY);
        box.setRotationPoint(rotX, rotY, rotZ);
        box.addBox(oriX, oriY, oriZ, width, height, depth, scale);
        return box;
    }
    
    private ModelUVMappedBox addBoxUV(ModelRenderer box, int texOffsetX, int texOffsetY, float oriX, float oriY, float oriZ, int width, int height, int depth, float scale, float rotX, float rotY, float rotZ) {
    	box.setTextureOffset(texOffsetX, texOffsetY);
        box.setRotationPoint(rotX, rotY, rotZ);
        ModelUVMappedBox result = new ModelUVMappedBox(box, texOffsetX, texOffsetY, oriX, oriY, oriZ, width, height, depth, scale, box.mirror);
        box.cubeList.add(result);
        return result;
    }
    
    private ModelPlane addPlane(ModelRenderer box, int texOffsetX, int texOffsetY, float oriX, float oriY, float oriZ, int width, int height, int depth, float scale, float rotX, float rotY, float rotZ) {
    	box.setTextureOffset(texOffsetX, texOffsetY);
        box.setRotationPoint(rotX, rotY, rotZ);
        ModelPlane result = new ModelPlane(box, texOffsetX, texOffsetY, oriX, oriY, oriZ, width, height, depth, scale, box.mirror);
        box.cubeList.add(result);
        return result;
    }
    
    private ModelRenderer box(ModelRenderer parent) {
    	ModelRenderer result = box();
    	parent.addChild(result);
    	return result;
    }
    
    private ModelRenderer box() {
    	return new ModelRenderer(this);
    }
    
    private ModelRenderer mirror(ModelRenderer parent) {
    	ModelRenderer result = box(parent);
    	result.mirror = true;
    	return result;
    }
    
    private ModelRenderer mirror() {
    	ModelRenderer result = box();
    	result.mirror = true;
    	return result;
    }
}