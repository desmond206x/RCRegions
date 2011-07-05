package com.silthus.rcregions;

import java.util.Set;

import org.bukkit.entity.Player;

import com.silthus.rcregions.config.Config;
import com.silthus.rcregions.database.Regions;
import com.silthus.rcregions.util.Extras;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RCRegion {
	
	private static RCRegions plugin = null;
	private RegionManager rm = null;
	private Regions database = null;
	private ProtectedRegion region = null;
	// Region Attributes
	private int id;
	private String name;
	private int regionSize = 0;
	private double price;
	private String owner;
	private int priceClass = 1;
	private double taxCosts = 0.0;
	private boolean buyable = true;
	private boolean locked = false;
	private String lastChanged = null;
	
	
	public RCRegion(String name) {
		this.name = name;
		this.rm = RCRegion.plugin.getWorldGuard().getRegionManager(RCRegions.world);
		this.region = this.rm.getRegion(getName());
		setupRegion();
		setupDatabase();
	}

	public static void initialize(RCRegions instance) {
		plugin = instance;
	}
	
	/**
	 * Syncs this Region with the database
	 * or vice versa
	 * Saves the database after syncing so no need to saveDatabase()
	 */
	public void sync() {
		// Get values from Database
		this.id = this.database.getId();
		this.buyable = this.database.isBuyable();
		this.locked = this.database.isLocked();
		this.lastChanged = this.database.getLastchanged();
		this.priceClass = this.database.getPriceclass();
		
		if (this.database.getOwner() != getOwner()) {
			this.database.setOwner(getOwner());
			this.database.setLastchanged(Extras.getDate());
		} else {
			this.owner = this.database.getOwner();
		}
		if (this.database.getSize() != getRegionSize()) {
			this.database.setSize(getRegionSize());
			this.database.setLastchanged(Extras.getDate());
		} else {
			this.regionSize = this.database.getSize();
		}
		if (this.database.getPrice() != getPrice()) {
			this.database.setPrice(getPrice());
			this.database.setLastchanged(Extras.getDate());
		} else {
			this.price = this.database.getPrice();
		}
		if (this.database.getTaxcosts() != getTaxCosts()) {
			this.database.setTaxcosts(getTaxCosts());
			this.database.setLastchanged(Extras.getDate());
		} else {
			this.taxCosts = this.database.getTaxcosts();
		}
		saveDatabase();
	}
	
	public void saveDatabase() {
		// write all changes into the database
		this.database.setBuyable(isBuyable());
		this.database.setLocked(isLocked());
		this.database.setLastchanged(getLastChanged());
		this.database.setPriceclass(getPriceClass());
		this.database.setPrice(getPrice());
		this.database.setTaxcosts(getTaxCosts());
		RCRegion.plugin.getDatabase().save(database);
	}
	
	private void setupDatabase() {
		this.database = RCRegion.plugin.getDatabase().find(Regions.class).where()
		.ieq("name", region.getId()).findUnique();
		if (database == null) {
			this.database = new Regions();
			this.database.setName(region.getId());
			this.database.setSize(getRegionSize());
			this.database.setPrice(getPrice());
			this.database.setBuyable(true);
			this.database.setLocked(false);
			this.database.setTaxcosts(getTaxCosts());
			this.database.setOwner(getOwner());
			this.database.setPriceclass(getPriceClass());
		}
		sync();
	}
	
	private void setupRegion() {
		calcSize();
		calcPrice();
		calcTaxCosts();
		setOwner();
	}
	
	public int getRegionSize() {
		return this.regionSize;
	}
	
	public void calcSize() {
		int volume = this.region.volume();
		// get the highest and lowest point
		BlockVector highestPoint = this.region.getMaximumPoint();
		BlockVector lowestPoint = this.region.getMinimumPoint();
		// extract Y (Z for normal people)
		int maxY = highestPoint.getBlockY();
		int lowY = lowestPoint.getBlockY();
		// get the height
		int diff = maxY - lowY;
		// divide the volume by height and get the
		// base square of the region
		int size = volume/diff;
		this.regionSize = size;
	}
	
	public void calcPrice() {
		// Size of the Region * Price per m² * Multiplikator
		setPrice(((getRegionSize() * Config.qmPrice) * getPriceClass()));
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public boolean isOwner(Player player) {
		LocalPlayer lp = RCRegion.plugin.getWorldGuard().wrapPlayer(player);
		return region.isOwner(lp);
	}

	public void setPriceClass(int priceClass) {
		this.priceClass = priceClass;
	}

	public int getPriceClass() {
		return priceClass;
	}

	public void setOwner() {
		// first get all owners and strip it down so that only
		// a String array is used
		Set<String> owners = region.getOwners().getPlayers();
		String[] players = new String[owners.size()];
		players = owners.toArray(players);
		// only use the first owner all others are ignored
		// TODO: add multiple owners
		if (players.length > 0)
			this.owner = players[0];
		else
			this.owner = null;
	}
	
	public void setOwner(Player player) {
		DefaultDomain domain = new DefaultDomain();
		domain.addPlayer(player.getName());
		region.setOwners(domain);
		this.owner = player.getName();
	}

	public String getOwner() {
		return owner;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setBuyable(boolean buyable) {
		this.buyable = buyable;
	}

	public boolean isBuyable() {
		return buyable;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setTaxCosts(double taxCosts) {
		this.taxCosts = taxCosts;
	}

	public double getTaxCosts() {
		return taxCosts;
	}
	
	public void calcTaxCosts() {
		setTaxCosts(
				getPrice() * Config.taxPercentage
				);
		
	}

	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}

	public String getLastChanged() {
		return lastChanged;
	}
	
	public String getName() {
		return this.name;
	}

}
