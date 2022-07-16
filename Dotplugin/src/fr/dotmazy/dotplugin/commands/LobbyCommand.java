package fr.dotmazy.dotplugin.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Translate;

public class LobbyCommand implements CommandExecutor {
	
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || sender.hasPermission("dotplugin.instanttp")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				try {
					Location spawn = new Location(
							Bukkit.getWorld(Main.getInstance().getConfig().getString("donne.lobby.loc.world")),
							Main.getInstance().getConfig().getDouble("donne.lobby.loc.x"),
							Main.getInstance().getConfig().getDouble("donne.lobby.loc.y"),
							Main.getInstance().getConfig().getDouble("donne.lobby.loc.z"),
							(float) Main.getInstance().getConfig().getDouble("donne.lobby.loc.yaw"),
							(float) Main.getInstance().getConfig().getDouble("donne.lobby.loc.pitch")
							);
					player.teleport(spawn);
					player.sendMessage(Translate.translateConfigText(spawn, player, "commands.lobby.success"));
				}catch(Exception e){
					sender.sendMessage(Translate.translateConfigText(player, "commands.lobby.error"));
					return true;
				}
			}else {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_cant_execute_in_console"));
			}
		}else if(sender.hasPermission("dotplugin.lobby")){
			int cooldownTime = 10; 
            if(cooldowns.containsKey(sender.getName())) {
                long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                if(secondsLeft>0) {
                    
                    sender.sendMessage("Tu ne peut pas executer cette commande avant "+ secondsLeft +" seconds!");
                    return true;
                }
            }
            cooldowns.put(sender.getName(), System.currentTimeMillis());
            
            if(sender instanceof Player) {
				Player player = (Player) sender;
				Location spawn = new Location(
						Bukkit.getWorld(Main.getInstance().getConfig().getString("commands.lobby.loc.world")),
						Main.getInstance().getConfig().getDouble("commands.lobby.loc.x"),
						Main.getInstance().getConfig().getDouble("commands.lobby.loc.y"),
						Main.getInstance().getConfig().getDouble("commands.lobby.loc.z"),
						(float) Main.getInstance().getConfig().getDouble("commands.lobby.loc.yaw"),
						(float) Main.getInstance().getConfig().getDouble("commands.lobby.loc.pitch")
						);
				
				player.sendMessage("§9Tu sera tp dans §c5 §9secondes!!");
    			
    			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						player.teleport(spawn);
						player.sendMessage(Translate.translateConfigText(spawn, player, "commands.lobby.success"));
						
					}
				}, (20 * 5));
				
			}else {
				sender.sendMessage("Cette command ne peut pas etre executer en console !");
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

}
