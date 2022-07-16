package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.RankUnit;
import fr.dotmazy.dotplugin.utils.Translate;

public class MessageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player&&sender.hasPermission("dotplugin.msg")) {
			if(args.length>1) {
				Player p = null;
				String message = "";
				Player player = (Player) sender;
				try {
					p = Bukkit.getPlayer(args[0]);
				}catch(Exception e) {
					p.sendMessage(Translate.translateConfigText(player, "command.unknow_player"));
					return false;
				}
				if(p.getName()==player.getName()) {
					player.sendMessage("§cVous ne pouvez pas vous envoyer de message priver !");
					return false;
				}
				for(int i=1;i<args.length;i++) {
					message+=args[i]+" ";
				}
				String pr = ChatColor.translateAlternateColorCodes('&', RankUnit.getPrefix(player))+"§r";
				p.sendMessage(Translate.translateConfigText(player.getName(), pr, message, "config.chat.private_msg_format_other"));
				player.sendMessage(Translate.translateConfigText(player.getName(), pr, message, "config.chat.private_msg_format_me"));
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

}
