package com.caglardev.warp.objects;

public class Visitor {
	private String playerName;
	private int playerVisits;
	
	public Visitor() {
		this.playerVisits = 0;
	}
	
	public Visitor(String playerName, int playerVisits) {
		this.playerName = playerName;
		this.playerVisits = playerVisits;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void setPlayerVisits(int playerVisits) {
		this.playerVisits = playerVisits;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public int getPlayerVisits() {
		return this.playerVisits;
	}
}
