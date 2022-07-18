package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Translate;

public class TphereCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==1){
			if(sender instanceof Player) {
				Player pl = (Player) sender;
				Player p = null;
				try {
					p=Bukkit.getPlayer(args[0]);
				}catch(Exception e){
					sender.sendMessage(Translate.translateConfigText(null, "commands.unknow_player"));
					return false;
				}
				p.teleport(pl.getLocation());
			}else {
				sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.error_cant_execute_in_console"));
			}
		}else {
			sender.sendMessage("§c/tphere <player>");
		}
		return false;
	}

}
