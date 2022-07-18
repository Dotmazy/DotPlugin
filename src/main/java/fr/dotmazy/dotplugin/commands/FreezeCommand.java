package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class FreezeCommand implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(!sender.isOp()||!Permissions.hasPermission(player.getName(), "dotplugin.freeze")) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
				return false;
			}
			if(args.length == 0 || args.length > 1) {
				player.sendMessage("§cUtilise /freeze §l<joueur>");
				return true;
			}
			if(args.length == 1) {
				String targetName = args[0];
				
				Player target = player;
				
				if(Main.getInstance().getDatabaseManager().exist(targetName)&&Main.getInstance().getDatabaseManager().isConnected(Main.getInstance().getDatabaseManager().getUUID(targetName))) {
					target = Bukkit.getPlayer(targetName);
				}else {
					player.sendMessage(Translate.translateConfigText(player, "commands.freeze.errormsg"));
					return false;
				}
				
				if(!Main.freeze.contains(target)) {
					Main.freeze.add(target);
					player.sendMessage(Translate.translateConfigText(target, "commands.freeze.freezeme"));
					target.sendMessage(Translate.translateConfigText(target, "commands.freeze.freezeother"));
				}else {
					Main.freeze.remove(target);
					player.sendMessage(Translate.translateConfigText(target, "commands.freeze.unfreezeme"));
					target.sendMessage(Translate.translateConfigText(target, "commands.freeze.unfreezeother"));
				}
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_cant_execute_in_console"));
		}
		
		return true;
	}
	
	List<String> arguments = new ArrayList<>();
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(arguments.isEmpty()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				arguments.add(player.getName());
			}
		}
		List<String> resultat = new ArrayList<>();
		
		if(args.length == 1) {
			for(String str : arguments) {
				if(str.toLowerCase().startsWith(args[0].toLowerCase())) {
					resultat.add(str);
				}
			}
			return resultat;
		}
		return new ArrayList<>();
	}

}
