package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.TimeUnit;
import fr.dotmazy.dotplugin.utils.Translate;

public class BansCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("ban")) {
			if(sender instanceof Player&&!Permissions.hasPermission(((Player)sender).getName(), "dotplugin.ban")) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.error_not_permission"));
				return false;
			}
			
			if(args.length < 3) {
				helpMessage(sender);
				return false;
			}
			
			String targetName = args[0];
			
			if(!Main.getInstance().getDatabaseManager().exist(targetName)) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.not_connected"));
				return false;
			}
			
			UUID targetUUID = Main.getInstance().getDatabaseManager().getUUID(targetName);
			
			if(Main.getInstance().banManager.isBanned(targetUUID)) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.ban.already_banned"));
				return false;
			}
			
			String reason = "";
			for(int i = 2; i < args.length; i++) {
				reason += args[i] + " ";
			}
			
			if(args[1].equalsIgnoreCase("perm")) {
				Main.getInstance().banManager.ban(targetUUID, -1, reason);
				sender.sendMessage(Translate.translateConfigText(targetName, 0, "Permanent", reason, "commands.ban.success"));
				return false;
			}
			
			if(!args[1].contains(":")) {
				helpMessage(sender);
				return false;
			}
			
			int duration = 0;
			try {
				duration = Integer.parseInt(args[1].split(":")[0]);
			}catch(NumberFormatException e) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.ban.duration_not_number"));
				return false;
			}
			
			if(!TimeUnit.existFromShortcut(args[1].split(":")[1])) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.ban.unknow_time_unit"));
				for(TimeUnit units : TimeUnit.values()) {
					sender.sendMessage("§b"+units.getName()+"§f: §e"+units.getShortcut());
					return false;
				}
			}
			
			TimeUnit unit = TimeUnit.getFromShortcut(args[1].split(":")[1]);
			
			long banTime = unit.getToSecond() * duration;
			
			Main.getInstance().banManager.ban(targetUUID, banTime, reason);
			sender.sendMessage(Translate.translateConfigText(targetName, duration, unit.getName(), reason, "commands.ban.success"));
		}
		
		if(label.equalsIgnoreCase("unban")) {
			if(sender instanceof Player&&!Permissions.hasPermission(((Player)sender).getName(), "dotplugin.unban")) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
				return false; 
			}
			
			if(args.length != 1) {
				sender.sendMessage("§c/unban <joueur>");
				return false;
			}
			
			String targetName = args[0];
			
			if(!Main.getInstance().getDatabaseManager().exist(targetName)) {
				sender.sendMessage(Translate.translateConfigText(targetName, "commands.not_connected", null, null, 0));
			}
			
			UUID targetUUID = Main.getInstance().getDatabaseManager().getUUID(targetName);
			
			if(!Main.getInstance().banManager.isBanned(targetUUID)) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.unban.not_ban"));
				return false;
			}
			
			Main.getInstance().banManager.unban(targetUUID);
			sender.sendMessage(Translate.translateConfigText(null, "commands.unban.success"));
			return false;
		}
		
		return false;
	}
	
	public void helpMessage(CommandSender sender) {
		sender.sendMessage("§c/ban <joueur> perm <raison>");
		sender.sendMessage("§c/ban <joueur> <durée>:<unité> <raison>");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> r = new ArrayList<>();
		if(label.equalsIgnoreCase("unban")) {
			if(args.length==1) {
				for(String p : Main.getInstance().getDatabaseManager().getBans()) {
					if(p.startsWith(args[0])) r.add(p);
				}
			}
		}else {
			if(args.length==1) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getName().startsWith(args[0])) r.add(p.getName());
				}
			}
			if(label.equalsIgnoreCase("ban")) {
				if(args.length==2) {
					if("perm".startsWith(args[1])) r.add("perm");
				}
			}
		}
		return r;
	}

}
