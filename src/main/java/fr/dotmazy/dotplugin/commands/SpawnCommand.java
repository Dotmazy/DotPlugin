package fr.dotmazy.dotplugin.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class SpawnCommand implements CommandExecutor {

	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.instanttp"))) {
    		if(sender instanceof Player) {
    			Player player = (Player) sender;
    			try {
    				Location spawn = new Location(
        					Bukkit.getWorld(Main.getInstance().getConfig().getString("donne.spawn."+player.getWorld().getName()+".loc.world")),
        					Main.getInstance().getConfig().getDouble("donne.spawn."+player.getWorld().getName()+".loc.x"),
        					Main.getInstance().getConfig().getDouble("donne.spawn."+player.getWorld().getName()+".loc.y"),
        					Main.getInstance().getConfig().getDouble("donne.spawn."+player.getWorld().getName()+".loc.z"),
        					(float) Main.getInstance().getConfig().getDouble("donne.spawn."+player.getWorld().getName()+".loc.yaw"),
        					(float) Main.getInstance().getConfig().getDouble("donne.spawn."+player.getWorld().getName()+".loc.pitch")
        					);
        			player.teleport(spawn);
        			player.sendMessage(Translate.translateConfigText(spawn, player, "commands.spawn.success"));
    			}catch(Exception e) {
    				player.sendMessage(Translate.translateConfigText(player, "commands.spawn.error"));
    				return true;
    			}
    		}else {
    			sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.error_cant_execute_in_console"));
    		}
    	}else if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.spawn"))){
    		int cooldownTime = 10; 
            if(cooldowns.containsKey(sender.getName())) {
                long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                if(secondsLeft>0) {
                    
                	sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.wait_message"));
                    return true;
                }
            }
            cooldowns.put(sender.getName(), System.currentTimeMillis());
            
            if(sender instanceof Player) {
    			final Player player = (Player) sender;
    			final Location spawn = new Location(
    					Bukkit.getWorld(Main.getInstance().getConfig().getString("commands.spawn."+player.getWorld().getName()+".loc.world")),
    					Main.getInstance().getConfig().getDouble("commands.spawn."+player.getWorld().getName()+".loc.x"),
    					Main.getInstance().getConfig().getDouble("commands.spawn."+player.getWorld().getName()+".loc.y"),
    					Main.getInstance().getConfig().getDouble("commands.spawn."+player.getWorld().getName()+".loc.z"),
    					(float) Main.getInstance().getConfig().getDouble("commands.spawn."+player.getWorld().getName()+".loc.yaw"),
    					(float) Main.getInstance().getConfig().getDouble("commands.spawn."+player.getWorld().getName()+".loc.pitch")
    					);
    			player.sendMessage(Translate.translateConfigText(player, "commands.5_second_message"));
    			
    			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						player.teleport(spawn);
						player.sendMessage(Translate.translateConfigText(player, "commands.spawn.success"));
						
					}
				}, (20 * 5));
            }else {
    			sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.error_cant_execute_in_console"));
    		}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
        return true;
	}

}
