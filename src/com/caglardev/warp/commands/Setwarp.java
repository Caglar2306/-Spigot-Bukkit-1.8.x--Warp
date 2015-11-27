package com.caglardev.warp.commands;

import java.util.Map.Entry;

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
						if(canPlayerSetWarp(player)) {
							if(!Plugin.warps.containsKey(args[0].toLowerCase())) {
								com.caglardev.warp.objects.Warp warp = new com.caglardev.warp.objects.Warp();
								
								warp.setName(args[0].toLowerCase());
								warp.setOwner(player.getDisplayName());
								warp.setLocation(player.getLocation());

								Plugin.warps.put(args[0], warp);
								player.sendMessage(getText("SETWARP_SUCCESSFULLY"));
							} else {
								player.sendMessage(getText("SETWARP_ALREADY_EXISTS"));
							}
						} else {
							player.sendMessage(getText("SETWARP_YOU_CANT_CREATE_MORE"));
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
	
	
	public boolean canPlayerSetWarp(Player player) {
		String permissionName = Plugin.configurationDefault.getConfig().getString("PermissionName", "com.caglardev.warp.permission");
		
		if(player.isOp()) {
			int maxWarps = Plugin.configurationDefault.getConfig().getInt("MaxWarps.Op", -1);
			if(maxWarps == -1 || maxWarps > getUserWarps(player.getDisplayName())) {
				return true;
			}
		} else if(player.hasPermission(permissionName)) {
			int maxWarps = Plugin.configurationDefault.getConfig().getInt("MaxWarps.Permission", -1);
			if(maxWarps == -1 || maxWarps > getUserWarps(player.getDisplayName())) {
				return true;
			}
		} else {
			int maxWarps = Plugin.configurationDefault.getConfig().getInt("MaxWarps.Default", -1);
			if(maxWarps == -1 || maxWarps > getUserWarps(player.getDisplayName())) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getUserWarps(String playerName) {
		int warps = 0;
		
		for(Entry<String, com.caglardev.warp.objects.Warp> entry : Plugin.warps.entrySet()) {
			if(entry.getValue().getOwner().equalsIgnoreCase(playerName)) {
				warps++;
			}
		}
		
		return warps;
	}
	
	public String getText(String path) {
		return TextFormat.getColoredText(Plugin.configurationTemplates.getConfig().getString(path));
	}

}
