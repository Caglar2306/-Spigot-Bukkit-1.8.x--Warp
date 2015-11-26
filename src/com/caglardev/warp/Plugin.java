package com.caglardev.warp;

import org.bukkit.plugin.java.JavaPlugin;

import com.caglardev.warp.commands.Setwarp;
import com.caglardev.warp.commands.Updatewarp;
import com.caglardev.warp.commands.Warp;

public class Plugin extends JavaPlugin {
	
	// General
	public static String PLUGIN_NAME = "Warp";
	
	// Configuration files
	public static Configuration configurationDefault;
	public static String CONFIG_DEFAULT = "Config.yml";

	public static Configuration configurationTemplates;
	public static String CONFIG_TEMPLATES = "Template.yml";
	
	public static Configuration configurationWarps;
	public static String CONFIG_WARPS = "Warps.yml";

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		initializePlugin(this);
	}
	
	public static void initializePlugin(JavaPlugin javaPlugin) {
		// Configuration files
		configurationDefault = new Configuration(javaPlugin, CONFIG_DEFAULT);
		configurationTemplates = new Configuration(javaPlugin, CONFIG_TEMPLATES);
		configurationWarps = new Configuration(javaPlugin, CONFIG_WARPS);

		if(!configurationWarps.getConfig().contains("Warps")) {
			configurationWarps.getConfig().createSection("Warps");
		}

		configurationWarps.saveConfig();
		configurationWarps.reloadConfig();
		
		// Load commands
		javaPlugin.getCommand("warp").setExecutor(new Warp(javaPlugin));
		javaPlugin.getCommand("setwarp").setExecutor(new Setwarp(javaPlugin));
		javaPlugin.getCommand("updatewarp").setExecutor(new Updatewarp(javaPlugin));
	}
	
}
