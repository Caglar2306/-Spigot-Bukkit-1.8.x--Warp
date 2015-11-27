package com.caglardev.warp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.caglardev.warp.Plugin;
import com.caglardev.warp.TextFormat;

public class Updatewarp implements CommandExecutor {

	@SuppressWarnings("unused")
	private JavaPlugin javaPlugin;
	
	public Updatewarp(JavaPlugin javaPlugin) {
		this.javaPlugin = javaPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player != null) {
				if(args.length == 1) {
					if(Plugin.warps.containsKey(args[0].toLowerCase())) {
						if(Plugin.warps.get(args[0].toLowerCase()).getOwner().equalsIgnoreCase(player.getDisplayName())) {
							Plugin.warps.get(args[0].toLowerCase()).setLocation(player.getLocation());
							
							player.sendMessage(getText("UPDATEWARP_SUCCESSFULLY"));
						} else {
							player.sendMessage(getText("UPDATEWARP_ERROR"));
						}
					} else  {
						player.sendMessage(getText("UPDATEWARP_NOT_EXISTS"));
					}
				} else {
					player.sendMessage(getText("UPDATEWARP_ARGS_EXISTS"));
				}
			}
		}
		return false;
	}
	
	public String getText(String path) {
		return TextFormat.getColoredText(Plugin.configurationTemplates.getConfig().getString(path));
	}
	
}
