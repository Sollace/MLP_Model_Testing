package com.minelittlepony.main;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import com.minelittlepony.render.RenderPony;
import com.minelittlepony.render.RenderZompony;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.util.ModUtilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.monster.EntityZombie;

public class LiteModMineLP implements LiteMod, InitCompleteListener {
	public static final String NAME = "MineLP Model V2";
	public static final String VERSION = "MineLP Model V2";
	
	public void init(File configPath) { }
	
	public void upgradeSettings(String version, File configPath, File oldConfigPath) { }
	
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) { }
	
	@SuppressWarnings("unchecked")
	public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		ModUtilities.addRenderer(EntityZombie.class, new RenderZompony(renderManager));
    	//ApiRenderPlayer.setPlayerRenderer(SkinType.STEVE, new RenderPony(ApiClient.getRenderManager()));
    	
    	//Non-Blazeloader alternative. Not as good since it requires reflection
    	try {
    		Field skinMap = RenderManager.class.getDeclaredFields()[1];
        	skinMap.setAccessible(true);
			((Map<String , RenderPlayer>)skinMap.get(renderManager)).put("default", new RenderPony(Minecraft.getMinecraft().getRenderManager()));
			((Map<String , RenderPlayer>)skinMap.get(renderManager)).put("slim", new RenderPony(Minecraft.getMinecraft().getRenderManager()));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}
}
