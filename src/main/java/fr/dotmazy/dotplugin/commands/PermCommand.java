package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class PermCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==2) {
			if(Permissions.getPowerOfPermission(args[0])==-1) {
				sender.sendMessage(Translate.translateConfigText(args[0], args[1], 0, "commands.permissions.unknow_permissions"));
				return false;
			}
			int power = 0;
			try {
				power = Integer.valueOf(args[1]);
			}catch(Exception e) {
				sender.sendMessage(Translate.translateConfigText(args[0], args[1], 0, "commands.permissions.unknow_power"));
				return false;
			}
			Permissions.setPower(args[0], power);
			sender.sendMessage(Translate.translateConfigText(args[0], power+"", 0, "commands.permissions.success"));
		}else {
			helpMsg(sender);
		}
		return false;
	}
	
	private void helpMsg(CommandSender sender) {
		sender.sendMessage("§c/permissions <permission_name> <power>");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> l = new ArrayList<>();
		if(args.length==1) {
			for(String p : Main.permissions) {
				if(p.startsWith(args[0])) l.add(p);
			}
		}
		return l;
	}

}
