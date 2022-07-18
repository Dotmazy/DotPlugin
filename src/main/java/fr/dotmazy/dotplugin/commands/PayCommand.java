package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Economy;
import fr.dotmazy.dotplugin.utils.Translate;

public class PayCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length==2) {
				Player pl = null;
				int amount = 0;
				try {
					pl=Bukkit.getOfflinePlayer(args[0]).getPlayer();
				}catch(Exception e){
					sender.sendMessage(Translate.translateConfigText(null, "commands.unknow_player"));
					return false;
				}
				try {
					amount=Integer.valueOf(args[1]);
				}catch(Exception e){
					sender.sendMessage("§cLa valeur 'amount' doit être un nombre !");
					return false;
				}
				try {
					Economy.removeBalance(p.getName(), amount);
					Economy.addBalance(pl.getName(), amount);
				}catch(Exception e) {
					sender.sendMessage("Une erreur est survenue !");
				}
			}else {
				sender.sendMessage("§c/pay <player> <amount>");
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.error_cant_execute_in_console"));
		}
		return false;
	}

}
