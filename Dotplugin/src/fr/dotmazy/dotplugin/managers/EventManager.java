package fr.dotmazy.dotplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.listeners.OnMove;
import fr.dotmazy.dotplugin.listeners.OnPlayerJoin;
import fr.dotmazy.dotplugin.listeners.ReportEvent;
import fr.dotmazy.dotplugin.listeners.Tablist;
import fr.dotmazy.dotplugin.listeners.InventoryClick;
import fr.dotmazy.dotplugin.listeners.ModCancels;
import fr.dotmazy.dotplugin.listeners.ModItemsInteract;
import fr.dotmazy.dotplugin.listeners.OnChat;

public class EventManager {

	public void registers() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new InventoryClick(), Main.getInstance());
		pm.registerEvents(new OnChat(), Main.getInstance());
		pm.registerEvents(new OnMove(), Main.getInstance());
		pm.registerEvents(new OnPlayerJoin(), Main.getInstance());
		pm.registerEvents(new ModCancels(), Main.getInstance());
		pm.registerEvents(new ReportEvent(), Main.getInstance());
		pm.registerEvents(new ModItemsInteract(), Main.getInstance());
		//pm.registerEvents(new OnDamage(), Main.getInstance());
		//Tablist.onTab();
	}
	
}
