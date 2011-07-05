package com.silthus.rcregions.util;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.silthus.rcregions.RCPermissions;
import com.silthus.rcregions.RCRegions;

/**
 * 
 * @author brenda
 */
public class CommandManager {

	@SuppressWarnings("unused")
	private final RCRegions plugin;
	private Map<String, CommandExecutor> commands = new Hashtable<String, CommandExecutor>();

	public CommandManager(RCRegions instance) {
		this.plugin = instance;
	}

	public void addCommand(String label, CommandExecutor executor) {
		commands.put(label, executor);
	}

	public boolean dispatch(CommandSender sender, Command command,
			String label, String[] args) {
		if (!commands.containsKey(label)) {
			return false;
		}

		boolean handled = true;

		CommandExecutor ce = commands.get(label);
		handled = ce.onCommand(sender, command, label, args);

		return handled;
	}

	// Simplifies and shortens the if statements for commands.
	public boolean is(String entered, String label) {
		return entered.equalsIgnoreCase(label);
	}

	// Checks if the current user is actually a player and returns the name of
	// that player.
	public String getName(CommandSender sender) {
		String name = "";
		if (isPlayer(sender)) {
			Player player = (Player) sender;
			name = player.getName();
		}
		return name;
	}

	// Checks if the current user is actually a player.
	public boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	// Converts the sender into a sender
	public Player getPlayerOfSender(CommandSender sender) {
		if (isPlayer(sender))
			return (Player) sender;
		Messaging.sendMessage(sender, "Sorry but I don't know who you are.");
		return null;
	}

	// Gets the player if the current user is actually a player.
	// TODO: implement sanity check
	public Player getPlayer(CommandSender sender, String[] args, int index) {
		if (args.length > index) {
			// gets all players on the server and filters them for the partial
			// name
			List<Player> players = sender.getServer().matchPlayer(args[index]);

			if (players.isEmpty()) {
				sender.sendMessage("I don't know who '" + args[index] + "' is!");
				return null;
			} else {
				return players.get(0);
			}
		} else {
			if (isPlayer(sender)) {
				return null;
			} else {
				return (Player) sender;
			}
		}
	}

	public String join(String[] split, String delimiter) {
		String joined = "";
		for (String s : split) {
			joined += s + delimiter;
		}
		joined = joined.substring(0, joined.length() - (delimiter.length()));
		return joined;
	}
	
	public boolean has(CommandSender sender, String permission) {
		if (RCPermissions.permission(getPlayerOfSender(sender), permission))
			return true;
		return false;
	}
}