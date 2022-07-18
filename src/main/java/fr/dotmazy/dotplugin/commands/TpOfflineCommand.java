package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Translate;

public class TpOfflineCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==1) {
			if(sender instanceof Player) {
				OfflinePlayer p = null;
				try {
					p=Bukkit.getOfflinePlayer(args[0]);
				}catch(Exception e){
					sender.sendMessage(Translate.translateConfigText(null, "commands.unknow_player"));
					return false;
				}
				((Player)sender).teleport(p.getPlayer().getLocation());
			}
		}else {
			
		}
		return false;
	}

}
