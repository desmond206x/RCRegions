package com.silthus.rcregions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.silthus.rcregions.commands.RCRCommand;
import com.silthus.rcregions.config.Config;
import com.silthus.rcregions.database.Players;
import com.silthus.rcregions.database.Regions;
import com.silthus.rcregions.util.CommandManager;
import com.silthus.rcregions.util.Extras;
import com.silthus.rcregions.util.RCLogger;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class RCRegions extends JavaPlugin {

	private final CommandManager commandManager = new CommandManager(this);

	public static String name;
	public static String version;
	public static World world;
	private WorldGuardPlugin worldguard = null;

	public void onEnable() {
		name = this.getDescription().getName();
		version = this.getDescription().getVersion();

		// Logger
		RCLogger.initialize(Logger.getLogger("Minecraft"));
		// Config
		Config.initialize(this);
		world = this.getServer().getWorld(Config.worldName);
		// WorldGuard and Regions
		Extras.initialize(this);
		setupWorldGuard();
		RCRegion.initialize(this);
		// Database
		setupDatabase();
		// Commands
		setupCommands();

		RCLogger.info(name + " version " + version + " is enabled!");
	}

	/*
	 * Sets up the core commands of the plugin.
	 */
	private void setupCommands() {
		// Add command labels here.
		addCommand("rc", new RCRCommand(this));
	}

	/*
	 * Executes a command when a command event is received.
	 * 
	 * @param sender The thing that sent the command.
	 * 
	 * @param cmd The complete command object.
	 * 
	 * @param label The label of the command.
	 * 
	 * @param args The arguments of the command.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		return commandManager.dispatch(sender, cmd, label, args);
	}

	/*
	 * Adds the specified command to the command manager and server.
	 * 
	 * @param command The label of the command.
	 * 
	 * @param executor The command class that excecutes the command.
	 */
	private void addCommand(String command, CommandExecutor executor) {
		getCommand(command).setExecutor(executor);
		commandManager.addCommand(command, executor);
	}

	public CommandManager getCommandManager() {
		return this.commandManager;
	}

	public void onDisable() {

		RCLogger.info(name + " disabled.");
	}
	
	private void setupWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	    	this.worldguard = null;
	    	RCLogger.warning("WorldGuard could not be loaded!");
	    }
	 
	    this.worldguard = (WorldGuardPlugin) plugin;
	}
	
	public WorldGuardPlugin getWorldGuard() {
		return this.worldguard;
	}
	
	private void setupDatabase() {
        try {
            getDatabase().find(Players.class).findRowCount();
            getDatabase().find(Regions.class).findRowCount();
        } catch (PersistenceException ex) {
            RCLogger.info("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }
    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(Players.class);
        list.add(Regions.class);
        return list;
    }

}
