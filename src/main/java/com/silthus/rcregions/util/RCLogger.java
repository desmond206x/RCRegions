package com.silthus.rcregions.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.silthus.rcregions.RCRegions;

public class RCLogger {

	private static Logger log;
	private static String prefix;

	public static void initialize(Logger logger) {
		RCLogger.log = logger;
		prefix = "[" + RCRegions.name + "] ";
	}

	public static Logger getLog() {
		return log;
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setPrefix(String prefix) {
		RCLogger.prefix = prefix;
	}

	public static void info(String message) {
		RCLogger.info(prefix + message);
	}

	public static void error(String message) {
		RCLogger.error(prefix + message);
	}

	public static void warning(String message) {
		RCLogger.warning(prefix + message);
	}

	public static void config(String message) {
		RCLogger.config(prefix + message);
	}

	public static void log(Level level, String message) {
		RCLogger.log(level, prefix + message);
	}
}
