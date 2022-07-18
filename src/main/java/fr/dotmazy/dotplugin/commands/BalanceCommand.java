package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.listeners.Tablist;
import fr.dotmazy.dotplugin.utils.Economy;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class BalanceCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(Permissions.hasPermission(((Player)sender).getName(), "dotplugin.balance")) {
				return set(args,sender);
			}else {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
			}
		}else {
			return set(args,sender);
		}
		return false;
	}
	
	private boolean set(String[] args, CommandSender sender) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				player.sendMessage("§aMoney: §6"+Economy.getFormatedBalance(player.getName())+"$");
			}
		}
		if(args.length == 2) {
			if(sender.hasPermission("dotplugin.balance.admin")) {
				if(args[0].equals("get")) {
					String targetName = args[1];
					if(!Main.getInstance().getDatabaseManager().exist(targetName)) {
						sender.sendMessage(Translate.translateConfigText(null, "commands.not_connected"));
					}
					sender.sendMessage("§6"+targetName+" §aa §6"+Economy.getFormatedBalance(targetName)+"$ §adans sa balance.");
				}
			}else {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
			}
		}else if(args.length == 3) {
			if(sender.hasPermission("dotplugin.balance.admin")) {
				String targetName = args[1];
				if(!Main.getInstance().getDatabaseManager().exist(targetName)) {
					sender.sendMessage(Translate.translateConfigText(null, "commands.not_connected"));
				}
				double amount = 0;
				try {
					amount = Double.valueOf(args[2]);
				}catch(Exception e) {
					sender.sendMessage("NUMBER(DOUBLE)");
					return false;
				}
				if(args[0].equalsIgnoreCase("set")) {
					if(Economy.setBalance(targetName, amount)) {
						sender.sendMessage("§aVous avez défini l'argent de §6"+targetName+" §aà §6"+Economy.formatm(amount)+"$");
					}else {
						sender.sendMessage("§cUne erreur est survenue !");
					}
				}
				if(args[0].equalsIgnoreCase("add")) {
					if(Economy.addBalance(targetName, amount)) {
						sender.sendMessage("§aVous avez ajouter §6"+Economy.formatm(amount)+"$ §aà §6"+targetName+"§a, il a maintenant §6"+Economy.getFormatedBalance(targetName)+"$");
					}else {
						sender.sendMessage("§cUne erreur est survenue !");
					}
				}
				if(args[0].equalsIgnoreCase("remove")) {
					if(Economy.removeBalance(targetName, amount)) {
						sender.sendMessage("§aVous avez enlever §6"+Economy.formatm(amount)+"$ §aà §6"+targetName+"§a, il a maintenant §6"+Economy.getFormatedBalance(targetName)+"$");
					}else {
						sender.sendMessage("§cUne erreur est survenue !");
					}
				}
				Tablist.onCoinChange();
			}else {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
			}
		}else if(args.length!=0){
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_cant_execute_in_console"));
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> l = new ArrayList<>();
		if(args.length==1) {
			if("set".startsWith(args[0])) l.add("set");
			if("get".startsWith(args[0])) l.add("get");
			if("add".startsWith(args[0])) l.add("add");
		}
		if(args.length==2&&(args[0].equalsIgnoreCase("set")||args[0].equalsIgnoreCase("get")||args[0].equalsIgnoreCase("add"))) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getName().startsWith(args[1])) {
					l.add(p.getName());
				}
			}
		}
		return l;
	}

}
