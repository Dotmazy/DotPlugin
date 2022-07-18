package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.listeners.Tablist;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.RankUnit;
import fr.dotmazy.dotplugin.utils.Translate;

public class RankCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.ranks"))) {
			if(args.length>0) {
				if(args[0].equalsIgnoreCase("join")) {
					try {
						String targetName = args[1];
						String name = args[2];
						if(!Main.getInstance().getDatabaseManager().exist(targetName)) {
							sender.sendMessage(Translate.translateConfigText(targetName, "commands.not_connected", name, null, 0));
							return false;
						}
						UUID targetUUID = Main.getInstance().getDatabaseManager().getUUID(targetName);
						if(!RankUnit.exist(name)) {
							sender.sendMessage(Translate.translateConfigText(targetName, "commands.ranks.join.unknow_rank", name, null, 0));
							return false;
						}
						if(Main.getInstance().getDatabaseManager().getRank(targetUUID).equals(name)) {
							sender.sendMessage(Translate.translateConfigText(targetName, "commands.ranks.join.already_join", name, null, 0));
							return false;
						}
						RankUnit.joinRank(targetUUID, name);
						sender.sendMessage(Translate.translateConfigText(targetName, "commands.ranks.join.success", name, null, 0));
						Tablist.onCoinChange();
						return false;
					}catch(Exception e) {
						helpMsg(sender);
						return false;
					}
				}else if(args[0].equalsIgnoreCase("create")){
					try {
						String name = args[1];
						int power;
						try {
							power = Integer.valueOf(args[2]);
						}catch(Exception e) {
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.create.unknow_power", name, null, 0));
							return false;
						}
						boolean def = false;
						if(args[3]!=null) {
							try {
								def = Boolean.valueOf(args[3]);
							}catch(Exception e){
								sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.create.unknow_default", name, null, 0));
								return false;
							}
						}
						String prefix = args[4];
						for(int i=5;i<args.length;i++) {
							prefix=" "+prefix+args[i];
						}
						if(RankUnit.exist(name)) {
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.create.already_exist", name, null, 0));
							return false;
						}
						RankUnit.createRank(name, power, prefix, def);
						sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.create.success", name, null, 0));
						return false;
					}catch(Exception e) {
						helpMsg(sender);
						return false;
					}
				}else if(args[0].equalsIgnoreCase("delete")) {
					try {
						String name = args[1];
						if(!RankUnit.exist(name)) {
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.delete.unknow_rank", name, null, 0));
							return false;
						}
						RankUnit.deleteRank(name);
						sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.delete.success", name, null, 0));
					}catch(Exception e) {
						helpMsg(sender);
						return false;
					}
				}else if(args[0].equalsIgnoreCase("prefix")) {
					try {
						String name = args[2];
						if(!RankUnit.exist(name)) {
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.prefix.unknow_rank", name, null, 0));
							return false;
						}
						if(args[1].equalsIgnoreCase("get")) {
							String prefix = RankUnit.getPrefix(name);
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.prefix.get_success", name, prefix, 0));
						}else if(args[1].equalsIgnoreCase("set")) {
							if(args[3]==null) {
								helpMsg(sender);
								return false;
							}
							String prefix = args[3];
							for(int i=4;i<args.length;i++) {
								prefix=" "+prefix+args[i];
							}
							RankUnit.setRankPrefix(prefix, name);
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.prefix.set_success", name, prefix, 0));
							Tablist.onCoinChange();
						}else {
							helpMsg(sender);
							return false;
						}
					}catch(Exception e) {
						helpMsg(sender);
						return false;
					}
				}else if(args[0].equalsIgnoreCase("power")){
					try {
						String name = args[2];
						if(!RankUnit.exist(name)) {
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.power.unknow_rank", name, null, 0));
							return false;
						}
						if(args[1].equalsIgnoreCase("get")) {
							int power = RankUnit.getPower(name);
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.power.get_success", name, null, power));
						}else if(args[1].equalsIgnoreCase("set")) {
							if(args[3]==null) {
								helpMsg(sender);
								return false;
							}
							int power = 0;
							try {
								power = Integer.valueOf(args[3]);
							}catch(Exception e) {
								sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.power.unknow_number", name, args[3], 0));
								return false;
							}
							RankUnit.setRankPower(power, name);
							sender.sendMessage(Translate.translateConfigText(null, "commands.ranks.power.set_success", name, null, power));
						}else {
							helpMsg(sender);
							return false;
						}
					}catch(Exception e) {
						helpMsg(sender);
						return false;
					}
				}else if(args[0].equalsIgnoreCase("list")){
					for(String n : RankUnit.getRank()) {
						sender.sendMessage(n+" : "+ChatColor.translateAlternateColorCodes('&', RankUnit.getRanks().get(n))+"§r : "+RankUnit.getPower(n));
					}
				}else {
					helpMsg(sender);
					return false;
				}
			}else {
				helpMsg(sender);
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}
	
	private void helpMsg(CommandSender sender) {
		sender.sendMessage("§c/ranks create <name> <power> <true/false> <prefix>");
		sender.sendMessage("§c/ranks delete <name>");
		sender.sendMessage("§c/ranks join <player_name> <name>");
		sender.sendMessage("§c/ranks prefix <set/get> <name> (<prefix>)");
		sender.sendMessage("§c/ranks power <set/get> <name> (<power>)");
		sender.sendMessage("§c/ranks list");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> r = new ArrayList<>();
		if(args.length==1) {
			if("create".startsWith(args[0])) r.add("create");
			if("delete".startsWith(args[0])) r.add("delete");
			if("join".startsWith(args[0])) r.add("join");
			if("prefix".startsWith(args[0])) r.add("prefix");
			if("prefix".startsWith(args[0])) r.add("prefix");
			if("power".startsWith(args[0])) r.add("power");
			if("list".startsWith(args[0])) r.add("list");
		}else if(args.length==2) {
			switch(args[0]) {
				case "delete":
					for(String s : RankUnit.getRank()) {
						if(s.startsWith(args[1])) r.add(s);
					}
					break;
				case "join":
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getName().startsWith(args[1])) r.add(p.getName());
					}
					break;
				case "prefix":
					if("set".startsWith(args[1])) r.add("set");
					if("get".startsWith(args[1])) r.add("get");
					break;
				case "power":
					if("set".startsWith(args[1])) r.add("set");
					if("get".startsWith(args[1])) r.add("get");
					break;
			
				default: break;
			}
		}else if(args.length==3) {
			switch(args[0]) {
				case "join":
					for(String s : RankUnit.getRank()) {
						if(s.startsWith(args[2])) r.add(s);
					}
					break;
				case "prefix":
					for(String s : RankUnit.getRank()) {
						if(s.startsWith(args[2])) r.add(s);
					}
					break;
				case "power":
					for(String s : RankUnit.getRank()) {
						if(s.startsWith(args[2])) r.add(s);
					}
					break;
			
				default: break;
			}
		}else if(args.length==5&&args[0]=="create") {
			if("true".startsWith(args[4])) r.add("true");
			if("false".startsWith(args[4])) r.add("false");
		}
		return r;
	}

}
