package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class NickCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.nick"))) {
			if(args.length==1) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(args[0].equalsIgnoreCase("delete")) {
						if(Main.getInstance().getDatabaseManager().getNickName(p.getUniqueId())==null) {
							sender.sendMessage(Translate.translateConfigText(p, "commands.nick.delete_error_me"));
							return false;
						}
						Main.getInstance().getDatabaseManager().setNickName(p.getUniqueId(), null);
						sender.sendMessage(Translate.translateConfigText(p, "commands.nick.delete_me"));
						return false;
					}
					Main.getInstance().getDatabaseManager().setNickName(p.getUniqueId(), args[0]);
					sender.sendMessage(Translate.translateConfigText(p, "commands.nick.success_me"));
				}else {
					sender.sendMessage("§c/nick <name> (<player>)");
				}
			}else if(args.length==2){
				if(Main.getInstance().getDatabaseManager().exist(args[1])) {
					if(args[0]=="delete") {
						if(Main.getInstance().getDatabaseManager().getNickName(Main.getInstance().getDatabaseManager().getUUID(args[1]))==null) {
							sender.sendMessage(Translate.translateConfigText(args[1], args[0], "commands.nick.delete_error_other"));
							return false;
						}
						Main.getInstance().getDatabaseManager().setNickName(Main.getInstance().getDatabaseManager().getUUID(args[1]), null);
						sender.sendMessage(Translate.translateConfigText(args[1], args[0], "commands.nick.delete_other"));
						return false;
					}
					Main.getInstance().getDatabaseManager().setNickName(Main.getInstance().getDatabaseManager().getUUID(args[1]), args[0]);
					sender.sendMessage(Translate.translateConfigText(args[1], args[0], "commands.nick.success_other"));
				}
			}else {
				sender.sendMessage("§c/nick <name> (<player>)");
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> r = new ArrayList<String>();
		if(args.length==1) {
			r.add("delete");
		}
		return r;
	}

}
