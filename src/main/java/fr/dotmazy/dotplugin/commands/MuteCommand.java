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

public class MuteCommand implements CommandExecutor, TabCompleter {

	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.mute"))) {
			if(args.length == 0 || args.length > 1) {
				sender.sendMessage("§c/mute <joueur>");
				return true;
			}
			if(args.length == 1) {
				String targetName = args[0];
				
				if(Bukkit.getPlayer(targetName) != null) {
					Player target = Bukkit.getPlayer(targetName);
					
					if(!Main.getInstance().mute.contains(target)) {
						Main.getInstance().mute.add(target);
						sender.sendMessage(Translate.translateConfigText(target, "commands.mute.mute_me"));
						sender.sendMessage(Translate.translateConfigText(target, "commands.mute.mute_other"));
					}else {
						Main.getInstance().mute.remove(target);
						sender.sendMessage(Translate.translateConfigText(target, "commands.mute.unmute_me"));
						target.sendMessage(Translate.translateConfigText(target, "commands.mute.unmute_other"));
					}
				}else {
					sender.sendMessage(Translate.translateConfigText(null, "commands.unknow_player"));
				}
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
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
