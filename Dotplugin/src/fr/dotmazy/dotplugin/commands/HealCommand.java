package fr.dotmazy.dotplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Translate;

public class HealCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("dotplugin.heal")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(label.equalsIgnoreCase("heal")) {
					player.setHealth(player.getMaxHealth());
				}else if(label.equalsIgnoreCase("feed")) {
					player.setFoodLevel(20);
				}
			}else {
				sender.sendMessage("Vous dever etre un joueur pour executer cette commande !");
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return true;
	}

}
