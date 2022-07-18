package fr.dotmazy.dotplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length==2) {
				Player p = (Player) sender;
				int v=0;
				try {
					v=Integer.valueOf(args[1]);
				}catch(Exception e) {
					sender.sendMessage("§cLa valeur 'speed' doit être un nombre !");
					return false;
				}
				if(args[0].equalsIgnoreCase("walk")) {
					p.setWalkSpeed(v);
				}else if(args[0].equalsIgnoreCase("fly")) {
					p.setFlySpeed(v);
				}else {
					sender.sendMessage("§cLe type est incorect !");
				}
			}else {
				sender.sendMessage("§c/speed <type> <speed>");
			}
		}
		return false;
	}

}
