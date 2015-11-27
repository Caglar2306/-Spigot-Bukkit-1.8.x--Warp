package com.caglardev.warp.objects;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Warp {

	private String Name;
	private String Owner;
	private HashMap<String, Visitor> visitor;
	private Location location;
	
	public Warp(String Name, String Owner, HashMap<String, Visitor> visitor, Location location) {
		this.Name = Name;
		this.Owner = Owner;
		this.visitor = visitor;
		this.location = location;
	}
	
	public Warp() {
		this.Name = "";
		this.Owner = "";
		this.visitor = new HashMap<String, Visitor>();
		this.location = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
	}
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public void setOwner(String owner) {
		this.Owner = owner;
	}
	
	public void setVisitor(HashMap<String, Visitor> visitor) {
		this.visitor = visitor;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public String getOwner() {
		return this.Owner;
	}
	
	public HashMap<String, Visitor> getVisitor() {
		return this.visitor;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public int getPlayerVisits(String playerName) {
		if(this.visitor.containsKey(playerName)) {
			return this.visitor.get(playerName).getPlayerVisits();
		}
		
		return 0;
	}
}
