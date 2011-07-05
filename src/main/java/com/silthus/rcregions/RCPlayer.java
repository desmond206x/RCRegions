package com.silthus.rcregions;

import java.util.Map;

import org.bukkit.entity.Player;

import com.silthus.rcregions.database.Players;
import com.silthus.rcregions.util.Extras;

public class RCPlayer {
	
	private static RCRegions plugin = null;
	
	private Player player = null;
	private Players playersDatabase = null;
	private Map<Integer,RCRegion> playerRegions = null;
	// Database Values
	private int id;
	private int regionCount = 0;
	private boolean locked = false;
	private String lastLogin = null;
	private double taxDebt = 0.0;
	
	
	public RCPlayer(Player player) {
		
		this.player = player;
		// initialize and load Database
		loadDatabase();
		// load all regions owned by this player
		loadPlayerRegions();
	}
	
	public static void initialize(RCRegions instance) {
		plugin = instance;
	}
	
	/**
	 * Loads the Database into memory or creates a new player entry
	 * if none exists
	 */
	private void loadDatabase() {
		this.playersDatabase = RCPlayer.plugin.getDatabase().find(Players.class).where()
		.ieq("playerName", this.player.getName()).findUnique();
		// create new player set if it doesnt exist
		if (playersDatabase == null) {
			this.playersDatabase = new Players();
			playersDatabase.setPlayername(player.getName());
			// new players normally dont have regions or a debt
			// so we set this to 0
			playersDatabase.setRegioncount(0);
			playersDatabase.setTaxdebt(0.0);
			// set the current date
			playersDatabase.setLastlogin(Extras.getDate());
		}
		this.setId(playersDatabase.getId());
		this.setRegionCount(playersDatabase.getRegioncount());
		this.setTaxDebt(playersDatabase.getTaxdebt());
		this.setLastLogin(playersDatabase.getLastlogin());
	}
	
	/**
	 * Write our changes back to the database and save it
	 */
	public void saveDatabase() {
		this.playersDatabase.setId(getId());
		this.playersDatabase.setLastlogin(getLastLogin());
		this.playersDatabase.setPlayername(player.getName());
		this.playersDatabase.setRegioncount(getRegionCount());
		this.playersDatabase.setTaxdebt(getTaxDebt());
		RCPlayer.plugin.getDatabase().save(playersDatabase);
	}

	private void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setRegionCount(int regionCount) {
		this.regionCount = regionCount;
	}

	public int getRegionCount() {
		return regionCount;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setTaxDebt(double taxDebt) {
		this.taxDebt = taxDebt;
	}

	public double getTaxDebt() {
		return taxDebt;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}
	
	public void loadPlayerRegions() {
		this.playerRegions = RegionManager.getPlayerRegions(player);
	}

	public Map<Integer,RCRegion> getPlayerRegions() {
		return this.playerRegions;
	}
}
