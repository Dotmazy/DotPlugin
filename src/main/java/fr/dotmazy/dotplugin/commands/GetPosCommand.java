package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Translate;

public class GetPosCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==1){
			Player p = null;
			try {
				p=Bukkit.getOfflinePlayer(args[0]).getPlayer();
			}catch(Exception e){
				sender.sendMessage(Translate.translateConfigText(null, "commands.unknow_player"));
				return false;
			}
			sender.sendMessage("§aLe joueur §6"+p.getName()+" §aest au coordoner §6x:"+p.getLocation().getX()+" y:"+p.getLocation().getY()+" z:"+p.getLocation().getZ());
		}else {
			sender.sendMessage("§c/getpos <player>");
		}
		return false;
	}

}
