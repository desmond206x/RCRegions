package com.silthus.rcregions;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import com.silthus.rcregions.util.Extras;

public class RegionManager {
	
	public static Map<Integer,RCRegion> getPlayerRegions(Player player) {
		Set<String> regions = Extras.getAllRegions().keySet();
		Map<Integer,RCRegion> playerRegions = new Hashtable<Integer,RCRegion>();
		int i = 0;
		for (String s : regions) {
			RCRegion region = new RCRegion(s);
			if (region.isOwner(player)) {
				playerRegions.put(region.getId(), region);
				i++;
			}
		}
		return playerRegions;
	}
}
