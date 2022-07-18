package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class AlertCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(Permissions.hasPermission(player.getName(), "dotplugin.broadcast")) {
				if(args.length == 0) {
					player.sendMessage("&&c/alert <message>");
				}else {
					StringBuilder bc = new StringBuilder();
					for(String part : args) {
						bc.append(part + " ");
					}
					
					Bukkit.broadcastMessage(Translate.translateConfigText(player, new AsyncPlayerChatEvent(false, player, bc.toString(), null), "commands.alert.alertformat"));
				}
			}else {
				player.sendMessage(Translate.translateConfigText(player, "commands.error_not_permission"));
			}
		}else {
			if(args.length == 0) {
				sender.sendMessage("&&c/alert <message>");
			}else {
				StringBuilder bc = new StringBuilder();
				for(String part : args) {
					bc.append(part + " ");
				}
				
				Bukkit.broadcastMessage(Translate.translateConfigText(null, new AsyncPlayerChatEvent(false, null, bc.toString(), null), "commands.alert.alertformat"));
			}
		}
		return false;
	}

}
