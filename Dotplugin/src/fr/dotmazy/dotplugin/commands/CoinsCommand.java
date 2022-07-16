package fr.dotmazy.dotplugin.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.listeners.Tablist;
import fr.dotmazy.dotplugin.utils.CoinUnit;
import fr.dotmazy.dotplugin.utils.Translate;

public class CoinsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("dotplugin.coins")) {
			if(args.length>1) {
				String targetName = args[1];
				if(!Main.getInstance().getDatabaseManager().exist(targetName)) {
					sender.sendMessage("§cCe joueur ne s'est jamais connecté au server !");
					return false;
				}
				UUID targetUUID = Main.getInstance().getDatabaseManager().getUUID(targetName);
				if(args[0].equalsIgnoreCase("get")) {
					sender.sendMessage(targetName+" §aà §6"+Translate.transformMoney(CoinUnit.getCoins(targetUUID)));
				}else {
					if(args.length>2) {
						int coins = 0;
						try {
							coins = Integer.valueOf(args[2]);
						}catch(Exception e){
							sender.sendMessage("§cLa valeur 'value' doit être un nombre !");
							return false;
						}
						if(args[0].equalsIgnoreCase("set")) {
							CoinUnit.setCoins(targetUUID, coins);
							sender.sendMessage("§aVous avez set l'argent de §6"+targetName+" §aà §6"+Translate.transformMoney(coins));
						}else if(args[0].equalsIgnoreCase("add")) {
							CoinUnit.addCoins(targetUUID, coins);
							sender.sendMessage("§aVous avez ajouter §6"+Translate.transformMoney(coins)+" §aà §6"+targetName);
						}else if(args[0].equalsIgnoreCase("remove")) {
							CoinUnit.removeCoins(targetUUID, coins);
							sender.sendMessage("§aVous avez enlever §6"+Translate.transformMoney(coins)+" §aà §6"+targetName);
						}
						for(Player online:Bukkit.getOnlinePlayers()) {
							Tablist.createBoard(online);
						}
					}
				}
			}else {
				sender.sendMessage("§cVeuiller utiliser cette commande: /coin <add/set/remove/get> (<player>) (<value>)");
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

}
