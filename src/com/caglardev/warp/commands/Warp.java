package com.caglardev.warp.commands;

import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.caglardev.warp.Plugin;
import com.caglardev.warp.TextFormat;
import com.caglardev.warp.objects.Visitor;

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
					if(Plugin.warps.containsKey(args[0].toLowerCase())) {
						int visits = Plugin.warps.get(args[0].toLowerCase()).getPlayerVisits(player.getDisplayName());
						visits++;
						
						if(Plugin.warps.get(args[0].toLowerCase()).getVisitor().containsKey(player.getDisplayName())) {
							Plugin.warps.get(args[0].toLowerCase()).getVisitor().get(player.getDisplayName()).setPlayerVisits(visits);
						} else {
							Visitor visitor = new Visitor();
							visitor.setPlayerName(player.getDisplayName());
							visitor.setPlayerVisits(visits);
							
							Plugin.warps.get(args[0].toLowerCase()).getVisitor().put(player.getDisplayName(), visitor);
						}
						
						player.teleport(Plugin.warps.get(args[0].toLowerCase()).getLocation());
						player.sendMessage(getText("WARP_SUCCESSFULLY"));
					} else {
						player.sendMessage(getText("WARP_NOT_EXISTS"));
					}
				} else if(args.length >= 1) {
					if(args[0].equalsIgnoreCase("list")) {
						player.sendMessage(getText("WARP_LIST_TITLE"));
						String string = "";
						for(Entry<String, com.caglardev.warp.objects.Warp> entry : Plugin.warps.entrySet()) {
							com.caglardev.warp.objects.Warp warp = entry.getValue();
							
							string = getText("WARP_LIST_ITEM")
									.replace("%name%", warp.getName())
									.replace("%owner%", warp.getOwner())
									.replace("%visitor%", String.valueOf(warp.getVisitor().size()));

							player.sendMessage(string);
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(args.length == 2) {
							if(Plugin.warps.containsKey(args[1].toLowerCase())) {
								if(Plugin.warps.get(args[1].toLowerCase()).getOwner().equalsIgnoreCase(player.getDisplayName())) {
									Plugin.warps.remove(args[1].toLowerCase());
									
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
