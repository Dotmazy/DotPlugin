package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Translate;

public class TpallCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==1) {
			Player p = null;
			try {
				p=Bukkit.getPlayer(args[0]);
			}catch(Exception e){
				sender.sendMessage(Translate.translateConfigText(null, "commands.unknow_player"));
				return false;
			}
			for(Player pl : Bukkit.getOnlinePlayers()) {
				pl.teleport(p);
			}
		}
		return false;
	}

}
