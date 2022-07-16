package fr.dotmazy.dotplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Translate;

public class AdminChatCommand implements CommandExecutor {

	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("dotplugin.adminchat")) {
				if(!Main.getInstance().adminchat.contains(player)) {
					Main.getInstance().adminchat.add(player);
					player.sendMessage(Translate.translateConfigText(player, "commands.adminchat.activate"));
				}else {
					Main.getInstance().adminchat.remove(player);
					player.sendMessage(Translate.translateConfigText(player, "commands.adminchat.deactivate"));
				}
			}else {
				player.sendMessage(Translate.translateConfigText(player, "commands.error_not_permission"));
			}
		}
		return false;
	}

}
