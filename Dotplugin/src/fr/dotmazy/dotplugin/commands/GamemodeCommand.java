package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Translate;

public class GamemodeCommand implements CommandExecutor, TabCompleter {
	
	@SuppressWarnings("deprecation")
	private boolean detectGamemode(String gm, Player p, CommandSender sender) {
		try{
			p.setGameMode(GameMode.getByValue(Integer.parseInt(gm)));
			if(sender.getName()==p.getName()) {
				return true;
			}
		}catch(IllegalArgumentException e){
			try {
				p.setGameMode(GameMode.valueOf(gm.toUpperCase()));
				if(sender.getName()==p.getName()) {
					return true;
				}
			}catch(IllegalArgumentException er){}
		}
		return false;
	}
	
	private void changeGamemode(GameMode gamemode, CommandSender sender, String[] args) {
		if(args.length==0) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				p.setGameMode(gamemode);
				sender.sendMessage(Translate.translateConfigText(p, "commands.gamemode.memsg"));
			}
		}else {
			Player p = Bukkit.getPlayer(args[0]);
			p.setGameMode(gamemode);
			sender.sendMessage(Translate.translateConfigText(p, "commands.gamemode.othermsg"));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("dotplugin.gamemodes")) {
			if(label.equalsIgnoreCase("gamemode")||label.equalsIgnoreCase("gm")) {
				if(args.length==1) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(detectGamemode(args[0], p, sender)==false) {
							sender.sendMessage(Translate.translateConfigText(p, "commands.gamemode.unknow_gamemode"));
							return true;
						}
						sender.sendMessage(Translate.translateConfigText(p, "commands.gamemode.me"));
					}else {
						sender.sendMessage("§cVous n'ètes pas un joueur.");
					}
				}else if(args.length==2) {
					Player p = Bukkit.getPlayer(args[1]);
					if(detectGamemode(args[0], p, sender)==false) {
						sender.sendMessage(Translate.translateConfigText(p, "commands.gamemode.unknow_gamemode"));
						return true;
					}
					sender.sendMessage(Translate.translateConfigText(p, "commands.gamemode.other"));
				}else {
					sender.sendMessage("§cUtiliser la command: /gamemode <creative/survival/spectator/adventure> (<player>)");
				}
			}
			if(label.equalsIgnoreCase("gmc")) {
				changeGamemode(GameMode.CREATIVE, sender, args);
			}
			if(label.equalsIgnoreCase("gms")) {
				changeGamemode(GameMode.SURVIVAL, sender, args);
			}
			if(label.equalsIgnoreCase("gmsp")) {
				changeGamemode(GameMode.SPECTATOR, sender, args);
			}
			if(label.equalsIgnoreCase("gma")) {
				changeGamemode(GameMode.ADVENTURE, sender, args);
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
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
		
		if(args.length==1&&(label.equalsIgnoreCase("gmc")||label.equalsIgnoreCase("gma")||label.equalsIgnoreCase("gms")||label.equalsIgnoreCase("gmsp"))) {
			for(String str : arguments) {
				if(str.toLowerCase().startsWith(args[0].toLowerCase())) {
					resultat.add(str);
				}
			}
			return resultat;
		}
		if(args.length==1&&(label.equalsIgnoreCase("gm")||label.equalsIgnoreCase("gamemode"))) {
			get("survival",resultat,args[0]);
			get("creative",resultat,args[0]);
			get("adventure",resultat,args[0]);
			get("spectator",resultat,args[0]);
			get("0",resultat,args[0]);
			get("1",resultat,args[0]);
			get("2",resultat,args[0]);
			get("3",resultat,args[0]);
			return resultat;
		}
		if(args.length==2&&(cmd.getName().equalsIgnoreCase("gm")||cmd.getName().equalsIgnoreCase("gamemode"))) {
			for(String str : arguments) {
				if(str.toLowerCase().startsWith(args[1].toLowerCase())) {
					resultat.add(str);
				}
			}
			return resultat;
		}
		return new ArrayList<>();
	}
	
	private void get(String l, List<String> r, String a) {
		if(l.startsWith(a.toLowerCase())) {
			r.add(l);
		}
	}

}
