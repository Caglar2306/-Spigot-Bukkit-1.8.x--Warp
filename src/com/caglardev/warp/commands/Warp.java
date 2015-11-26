package com.caglardev.warp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.caglardev.warp.Plugin;
import com.caglardev.warp.TextFormat;

public class Warp implements CommandExecutor {

	@SuppressWarnings("unused")
	private JavaPlugin javaPlugin;
	
	public Warp(JavaPlugin javaPlugin) {
		this.javaPlugin = javaPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player != null) {
				if(args.length == 1 && !args[0].equalsIgnoreCase("remove") && !args[0].equalsIgnoreCase("list")) {
					if(Plugin.configurationWarps.getConfig().contains("Warps." + args[0].toLowerCase())) {
						World world = Bukkit.getWorld(Plugin.configurationWarps.getConfig().getString("Warps." + args[0].toLowerCase() + ".Location.World"));
						double x = Plugin.configurationWarps.getConfig().getDouble("Warps." + args[0].toLowerCase() + ".Location.X");
						double y = Plugin.configurationWarps.getConfig().getDouble("Warps." + args[0].toLowerCase() + ".Location.Y");
						double z = Plugin.configurationWarps.getConfig().getDouble("Warps." + args[0].toLowerCase() + ".Location.Z");
						Location location = new Location(world, x, y, z);
						location.setPitch(Float.valueOf(Plugin.configurationWarps.getConfig().getString("Warps." + args[0].toLowerCase() + ".Location.Pitch")));
						location.setYaw(Float.valueOf(Plugin.configurationWarps.getConfig().getString("Warps." + args[0].toLowerCase() + ".Location.Yaw")));
						
						if(!Plugin.configurationWarps.getConfig().contains("Warps." + args[0].toLowerCase() + ".Visitor." + player.getDisplayName())) {
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Visitor." + player.getDisplayName(), "1");
						} else {
							int visits = Plugin.configurationWarps.getConfig().getInt("Warps." + args[0].toLowerCase() + ".Visitor." + player.getDisplayName());
							visits++;
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Visitor." + player.getDisplayName(), visits);
						}
						
						Plugin.configurationWarps.saveConfig();
						Plugin.configurationWarps.reloadConfig();
						
						player.teleport(location);
						player.sendMessage(getText("WARP_SUCCESSFULLY"));
					} else {
						player.sendMessage(getText("WARP_NOT_EXISTS"));
					}
				} else if(args.length >= 1) {
					if(args[0].equalsIgnoreCase("list")) {
						player.sendMessage(getText("WARP_LIST_TITLE"));
						String string = "";
						for(String key : Plugin.configurationWarps.getConfig().getConfigurationSection("Warps").getKeys(false)) {
							string = getText("WARP_LIST_ITEM")
									.replace("%name%", key)
									.replace("%owner%", Plugin.configurationWarps.getConfig().getString("Warps." + key + ".Owner"))
									.replace("%visitor%", String.valueOf(Plugin.configurationWarps.getConfig().getConfigurationSection("Warps." + key + ".Visitor").getKeys(false).size()));

							player.sendMessage(string);
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(args.length == 2) {
							if(Plugin.configurationWarps.getConfig().contains("Warps." + args[1].toLowerCase())) {
								if(Plugin.configurationWarps.getConfig().getString("Warps." + args[1].toLowerCase() + ".Owner").equalsIgnoreCase(player.getDisplayName())) {
									Plugin.configurationWarps.getConfig().set("Warps." + args[1].toLowerCase(), null);
									
									Plugin.configurationWarps.saveConfig();
									Plugin.configurationWarps.reloadConfig();
									
									player.sendMessage(getText("WARP_REMOVE_SUCCESSFULLY"));
								} else  {
									player.sendMessage(getText("WARP_REMOVE_ERROR"));
								}
							} else  {
								player.sendMessage(getText("WARP_REMOVE_NOT_EXISTS"));
							}
						} else {
							player.sendMessage(getText("WARP_REMOVE_ARGS_EXISTS"));
						}
					} else {
						
					}
				} else {
					player.sendMessage(getText("WARP_ARGS_EXISTS"));
				}
			}
		}
		return false;
	}
	
	public String getText(String path) {
		return TextFormat.getColoredText(Plugin.configurationTemplates.getConfig().getString(path));
	}

	public boolean isInteger(String string) {
	    try {
	        Integer.parseInt(string);
	        return true;
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	}

}
