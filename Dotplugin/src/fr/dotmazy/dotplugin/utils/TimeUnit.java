package fr.dotmazy.dotplugin.utils;

import java.util.HashMap;

public enum TimeUnit {
	
	SECONDE("Secondes", "sec",1),
	MINUTE("Minutes", "min",60),
	HEURE("Heures", "h", 360),
	JOUR("Jours", "j",360*24),
	MOIS("Mois", "m",2592000);
	
	private String name;
	private String shortcut;
	private long toSecond;
	
	private static HashMap<String, TimeUnit> ID_SHORTCUT = new HashMap<>();
	
	private TimeUnit(String name, String shortcut, long toSecond) {
		this.name = name;
		this.shortcut = shortcut;
		this.toSecond = toSecond;
	}
	
	static {
		for(TimeUnit units : values()) {
			ID_SHORTCUT.put(units.shortcut, units);
		}
	}
	
	public static TimeUnit getFromShortcut(String shortcut) {
		return ID_SHORTCUT.get(shortcut);
	}

	public String getName() {
		return name;
	}

	public String getShortcut() {
		return shortcut;
	}

	public long getToSecond() {
		return toSecond;
	}
	
	public static boolean existFromShortcut(String shortcut) {
		return ID_SHORTCUT.containsKey(shortcut);
	}
	
}
