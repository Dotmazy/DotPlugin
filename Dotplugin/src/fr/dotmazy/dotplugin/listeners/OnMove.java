package fr.dotmazy.dotplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.dotmazy.dotplugin.Main;

public class OnMove implements Listener {

	@SuppressWarnings("static-access")
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(Main.getInstance().freeze.contains(player)) {
			event.setCancelled(true);
		}
	}
	
}
