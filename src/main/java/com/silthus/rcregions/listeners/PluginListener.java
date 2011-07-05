package com.silthus.rcregions.listeners;

import org.bukkit.event.server.ServerListener;

import com.silthus.rcregions.RCRegions;

public class PluginListener extends ServerListener {

	private final RCRegions plugin;

	public PluginListener(RCRegions instance) {
		this.plugin = instance;
	}

}
