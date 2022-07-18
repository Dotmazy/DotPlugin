package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(!sender.isOp()||!Permissions.hasPermission(p.getName(), "dotplugin.fly")) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
				return false;
			}
			if(args.length<1) {
				if(!p.getAllowFlight()) {
					p.sendMessage(Translate.translateConfigText(p, "commands.fly.active"));
					p.setAllowFlight(true);
				}else {
					sender.sendMessage(Translate.translateConfigText(p, "commands.fly.unactive"));
					p.setAllowFlight(false);
				}
			}else {
				Player player = Bukkit.getPlayer(args[0]);
				if(!p.getAllowFlight()) {
					sender.sendMessage(Translate.translateConfigText(player, "commands.fly.activeother"));
					player.setAllowFlight(true);
				}else {
					sender.sendMessage(Translate.translateConfigText(player, "commands.fly.unactiveother"));
					player.setAllowFlight(false);
				}
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_cant_execute_in_console"));
		}
		return false;
	}

}
