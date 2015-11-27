package com.caglardev.warp;

import org.bukkit.ChatColor;

public class TextFormat {
	
	public static String getColoredDisplayName(String text) {
		text = text.replaceAll("&0", "§0");
        text = text.replaceAll("&1", "§1");
        text = text.replaceAll("&2", "§2");
        text = text.replaceAll("&3", "§3");
        text = text.replaceAll("&4", "§4");
        text = text.replaceAll("&5", "§5");
        text = text.replaceAll("&6", "§6");
        text = text.replaceAll("&7", "§7");
        text = text.replaceAll("&8", "§8");
        text = text.replaceAll("&9", "§9");
        text = text.replaceAll("&a", "§a");
        text = text.replaceAll("&b", "§b");
        text = text.replaceAll("&c", "§c");
        text = text.replaceAll("&d", "§d");
        text = text.replaceAll("&e", "§e");
        text = text.replaceAll("&f", "§f");
        text = text.replaceAll("&g", "§g");
        return text;
	}

	public static String getColoredText(String text) {
		text = text.replaceAll("&0", ChatColor.BLACK + "");
        text = text.replaceAll("&1", ChatColor.DARK_BLUE + "");
        text = text.replaceAll("&2", ChatColor.DARK_GREEN + "");
        text = text.replaceAll("&3", ChatColor.DARK_AQUA + "");
        text = text.replaceAll("&4", ChatColor.DARK_RED + "");
        text = text.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        text = text.replaceAll("&6", ChatColor.GOLD + "");
        text = text.replaceAll("&7", ChatColor.GRAY + "");
        text = text.replaceAll("&8", ChatColor.DARK_GRAY + "");
        text = text.replaceAll("&9", ChatColor.BLUE + "");
        text = text.replaceAll("&a", ChatColor.GREEN + "");
        text = text.replaceAll("&b", ChatColor.AQUA + "");
        text = text.replaceAll("&c", ChatColor.RED + "");
        text = text.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        text = text.replaceAll("&e", ChatColor.YELLOW + "");
        text = text.replaceAll("&f", ChatColor.WHITE + "");
        text = text.replaceAll("&g", ChatColor.MAGIC + "");
        return text;
	}
	
}
