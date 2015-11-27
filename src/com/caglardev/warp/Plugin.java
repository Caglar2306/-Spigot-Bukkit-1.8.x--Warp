package com.caglardev.warp;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.caglardev.warp.commands.Setwarp;
import com.caglardev.warp.commands.Updatewarp;
import com.caglardev.warp.commands.Warp;
import com.caglardev.warp.objects.Visitor;

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

	public static HashMap<String, com.caglardev.warp.objects.Warp> warps;
	
	@Override
	public void onDisable() {
		System.out.println("Saving warps...");

		for(String key : configurationWarps.getConfig().getConfigurationSection("Warps").getKeys(false)) {
			configurationWarps.getConfig().set("Warps." + key, null);
		}
		
		for(Entry<String, com.caglardev.warp.objects.Warp> entry : warps.entrySet()) {
			com.caglardev.warp.objects.Warp warp = entry.getValue();
			
			configurationWarps.getConfig().createSection("Warps." + warp.getName() + ".Visitor");
			
			for(Entry<String, Visitor> visitor : warp.getVisitor().entrySet()) {
				Plugin.configurationWarps.getConfig().set("Warps." + warp.getName() + ".Visitor." + visitor.getValue().getPlayerName(), visitor.getValue().getPlayerVisits());
			}
			
			configurationWarps.getConfig().set("Warps." + warp.getName() + ".Owner", warp.getOwner());
			configurationWarps.getConfig().set("Warps." + warp.getName() + ".Location.World", warp.getLocation().getWorld().getName());
			configurationWarps.getConfig().set("Warps." + warp.getName() + ".Location.X", warp.getLocation().getX());
			configurationWarps.getConfig().set("Warps." + warp.getName() + ".Location.Y", warp.getLocation().getY());
			configurationWarps.getConfig().set("Warps." + warp.getName() + ".Location.Z", warp.getLocation().getZ());
			configurationWarps.getConfig().set("Warps." + warp.getName() + ".Location.Pitch", warp.getLocation().getPitch());
			configurationWarps.getConfig().set("Warps." + warp.getName() + ".Location.Yaw", warp.getLocation().getYaw());
		}
		
		configurationWarps.saveConfig();
		
		System.out.println("Warps saved.");
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

		// Loading warps
		if(!configurationWarps.getConfig().contains("Warps")) {
			configurationWarps.getConfig().createSection("Warps");
		}

		configurationWarps.saveConfig();
		configurationWarps.reloadConfig();
		
		warps = new HashMap<String, com.caglardev.warp.objects.Warp>();
		
		for(String key : configurationWarps.getConfig().getConfigurationSection("Warps").getKeys(false)) {
			com.caglardev.warp.objects.Warp warp = new com.caglardev.warp.objects.Warp();

			warp.setName(key.toLowerCase());
			warp.setOwner(configurationWarps.getConfig().getString("Warps." + key + ".Owner", ""));
			
			HashMap<String, Visitor> visitor = new HashMap<String, Visitor>();
			for(String visitorKey : configurationWarps.getConfig().getConfigurationSection("Warps." + key + ".Visitor").getKeys(false)) {
				visitor.put(visitorKey, new Visitor(visitorKey, configurationWarps.getConfig().getInt("Warps." + key + ".Visitor." + visitorKey, 0)));
			}
			
			warp.setVisitor(visitor);
			
			World world = Bukkit.getWorld(configurationWarps.getConfig().getString("Warps." + key + ".Location.World", null));
			double x = configurationWarps.getConfig().getDouble("Warps." + key + ".Location.X", 0);
			double y = configurationWarps.getConfig().getDouble("Warps." + key + ".Location.Y", 0);
			double z = configurationWarps.getConfig().getDouble("Warps." + key + ".Location.Z", 0);
			float pitch = Float.valueOf(configurationWarps.getConfig().getString("Warps." + key + ".Location.Pitch", "0"));
			float yaw = Float.valueOf(configurationWarps.getConfig().getString("Warps." + key + ".Location.Yaw", "0"));
			
			Location location = new Location(world, x, y, z);
			location.setPitch(pitch);
			location.setYaw(yaw);
			
			warp.setLocation(location);
			
			warps.put(key, warp);
		}
		
		// Load commands
		javaPlugin.getCommand("warp").setExecutor(new Warp(javaPlugin));
		javaPlugin.getCommand("setwarp").setExecutor(new Setwarp(javaPlugin));
		javaPlugin.getCommand("updatewarp").setExecutor(new Updatewarp(javaPlugin));
	}
	
}
