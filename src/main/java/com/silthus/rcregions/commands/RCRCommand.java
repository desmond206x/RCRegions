package com.silthus.rcregions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.silthus.rcregions.RCPlayer;
import com.silthus.rcregions.RCRegions;
import com.silthus.rcregions.util.CommandManager;

public class RCRCommand implements CommandExecutor {
	
	private final RCRegions plugin;
	private boolean handled = false;
	private Player player = null;
	private CommandManager cmd = null;
	
	public RCRCommand (RCRegions instance) {
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// handled is returned at the end of onCommand
		this.handled = false;
		cmd = plugin.getCommandManager();
		
		if (cmd.is("rcr", label)) {
			if (args.length == 0 || args == null) {
				this.handled = true;
				this.player = cmd.getPlayerOfSender(sender);
				if (cmd.has(sender, "rcregions.player.status") && (Player)sender == player) {
					RCPlayer p = new RCPlayer(this.player);
					
				}
			}
		}
		return this.handled;
	}

}
