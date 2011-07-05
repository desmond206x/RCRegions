package com.silthus.rcregions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.silthus.rcregions.util.RCLogger;

/**
 * @description Handles all plugin permissions
 * @author Tagette
 */
public class RCPermissions {

	public static Plugin permissionPlugin;
	private static PermissionHandler permissionsHandler;
	private static RCRegions plugin;
	
	/**
	 * loads the Permissions plugin
	 * @param instance
	 */
	public static void initialize(RCRegions instance) {
		RCPermissions.plugin = instance;
		Plugin Permissions = plugin.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (Permissions != null) {
			permissionPlugin = Permissions;
			String version = permissionPlugin.getDescription().getVersion();
			RCLogger.info("Permissions version " + version + " loaded.");
			RCPermissions.permissionsHandler = ((Permissions) RCPermissions.permissionPlugin).getHandler();
		}
	}

	public static void onEnable(Plugin plugin) {
		if (permissionPlugin == null) {
			String pluginName = plugin.getDescription().getName();

			if (pluginName.equals("Permissions")) {
				permissionPlugin = plugin;
				String version = plugin.getDescription().getVersion();
				RCLogger.info("Permissions version " + version + " loaded.");
			}
		}
	}
	
	/**
	 * checks if player has permission
	 * @param player
	 * @param permission
	 * @return hasPermission
	 */
	public static boolean permission(Player player, String permission) {
		return RCPermissions.permissionsHandler.has(player, permission);
	}
	
	/**
	 * checks if the player is admin
	 * @param player
	 * @return isAdmin
	 */
	public static boolean isAdmin(Player player) {
		if (permission(player, "rcr.admin") || player.isOp())
			return true;
		return false;
	}
}
