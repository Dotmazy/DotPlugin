package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Translate;

public class BurnCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==2) {
			Player p = null;
			try {
				p=Bukkit.getPlayer(args[0]);
			}catch(Exception e){
				sender.sendMessage(Translate.translateConfigText(null, "commands.unknow_player"));
				return false;
			}
			int v=0;
			try {
				v=Integer.valueOf(args[1]);
			}catch(Exception e) {
				sender.sendMessage("§cLa valeur 'secondes' doit être un nombre !");
				return false;
			}
			p.setFireTicks(v*20);
		}else {
			sender.sendMessage("§c/burn <player> <secondes>");
		}
		return false;
	}

}
