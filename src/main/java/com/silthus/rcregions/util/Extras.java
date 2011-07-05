package com.silthus.rcregions.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.silthus.rcregions.RCRegions;
import com.silthus.rcregions.config.Config;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Extras {
	
	private static RCRegions plugin = null;
	
	public static void initialize(RCRegions instance) {
		Extras.plugin = instance;
	}
	
	/**
	 * formats a Date into a String with dd-MM-yyyy
	 * 
	 * @param date
	 * @return formated Date
	 */
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(date);
	}
	
	public static Map<String, ProtectedRegion> getAllRegions() {
		Map <String,ProtectedRegion> regions = plugin.getWorldGuard().getRegionManager(plugin.getServer().
													getWorld(Config.worldName)).getRegions();
		return regions;
	}

}
