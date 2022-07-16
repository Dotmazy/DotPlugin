package fr.dotmazy.dotplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Translate;

public class OnChat implements Listener {
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void OnChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(Main.getInstance().getDatabaseManager().getNickName(player.getUniqueId())!=null) {
			event.setFormat(Translate.translateConfigText(player, (org.bukkit.event.Event)event, "config.chat.nick_format"));
		}else if(Main.getInstance().getConfig().getBoolean("config.allow_plugin_chat")){
			event.setFormat(Translate.translateConfigText(player, (org.bukkit.event.Event)event, "config.chat.format"));
		}
		if(Main.getInstance().mute.contains(player)) {
			event.setCancelled(true);
			player.sendMessage(Translate.translateConfigText(player, (org.bukkit.event.Event)event, "config.chat.mute_msg"));
		}
		if(Main.getInstance().adminchat.contains(player)) {
			if(player.hasPermission("dotplugin.adminchat")) {
				event.setCancelled(true);
				player.sendMessage(Translate.translateConfigText(player, (org.bukkit.event.Event)event, "config.chat.admin_chat_format"));
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.hasPermission("dotplugin.adminchat")&&p!=player) {
						p.sendMessage(Translate.translateConfigText(p, (org.bukkit.event.Event)event, "config.chat.admin_chat_format"));
					}
				}
			}else {
				Main.getInstance().adminchat.remove(player);
				player.sendMessage(Translate.translateConfigText(player, (org.bukkit.event.Event)event, "config.chat.admin_chat_error"));
			}
		}
	}

}
