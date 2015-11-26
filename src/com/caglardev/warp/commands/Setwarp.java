package com.caglardev.warp.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.caglardev.warp.Plugin;
import com.caglardev.warp.TextFormat;

public class Setwarp implements CommandExecutor {

	@SuppressWarnings("unused")
	private JavaPlugin javaPlugin;
	
	public Setwarp(JavaPlugin javaPlugin) {
		this.javaPlugin = javaPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player != null) {
				if(args.length == 1) {
					if(!args[0].equalsIgnoreCase("list") && !args[0].equalsIgnoreCase("remove")) {
						if(!Plugin.configurationWarps.getConfig().contains("Warps." + args[0].toLowerCase())) {
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Owner", player.getDisplayName());
							//Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Visitor", 0);
							
							if(!Plugin.configurationWarps.getConfig().contains("Warps." + args[0].toLowerCase() + ".Visitor")) {
								Plugin.configurationWarps.getConfig().createSection("Warps." + args[0].toLowerCase() + ".Visitor");
							}
							
							Location location = player.getLocation();
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Location.World", player.getWorld().getName());
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Location.X", location.getX());
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Location.Y", location.getY());
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Location.Z", location.getZ());
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Location.Yaw", location.getYaw());
							Plugin.configurationWarps.getConfig().set("Warps." + args[0].toLowerCase() + ".Location.Pitch", location.getPitch());
							
							Plugin.configurationWarps.saveConfig();
							Plugin.configurationWarps.reloadConfig();
							
							player.sendMessage(getText("SETWARP_SUCCESSFULLY"));
						} else  {
							player.sendMessage(getText("SETWARP_ALREADY_EXISTS"));
						}
					} else {
						player.sendMessage(getText("SETWARP_NOT_ALLOWED_NAMES"));
					}
				} else {
					player.sendMessage(getText("SETWARP_ARGS_EXISTS"));
				}
			}
		}
		return false;
	}
	
	public String getText(String path) {
		return TextFormat.getColoredText(Plugin.configurationTemplates.getConfig().getString(path));
	}

}
