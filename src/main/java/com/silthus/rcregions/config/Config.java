package com.silthus.rcregions.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.util.config.Configuration;

import com.silthus.rcregions.RCRegions;
import com.silthus.rcregions.util.RCLogger;

public class Config {
	
	private static RCRegions plugin;
	
	protected static String configFileName = "config.yml";
	private static Configuration config = null;
	private static File configFile;
	
	public static String worldName = null;
	public static double qmPrice;
	public static double taxPercentage;

	public static void initialize(RCRegions instance) {
		Config.plugin = instance;
		load();
	}

	public static void load() {
		
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}

		configFile = new File(plugin.getDataFolder().getAbsolutePath()
				+ File.separator + configFileName);

		if (!configFile.exists()) {
			createNew(configFile);
			RCLogger.warning("No config found! Creating new one...");
		}
		config = new Configuration(configFile);
		config.load();
		if (config.getInt("configversion", 1) < 1) {
			File renameFile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + configFileName + "_old");
			if (renameFile.exists())
				renameFile.delete();
			configFile.renameTo(renameFile);
			createNew(configFile);
			RCLogger.warning("Old config found! Renaming and creating new one...");
			config = new Configuration(configFile);
			config.load();
		}
		Config.setup(config);
	}

	private static boolean createNew(File configFile) {

		try {
			InputStream stream = RCRegions.class.getResourceAsStream("/"
					+ configFileName);
			OutputStream out = new FileOutputStream(configFile);

			byte[] buf = new byte[1024];
			int len;
			while ((len = stream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			stream.close();
			out.close();
			return true;
		} catch (IOException iex) {
			RCLogger.warning("Could not create config file! Aborting...");
			return false;
		}
	}

	private static void setup(Configuration file) {
		
		worldName = file.getString("world", "world");
		qmPrice = file.getDouble("qmPrice", 1.0);
		taxPercentage = file.getDouble("taxPercentage", 0.10);
	}
}
