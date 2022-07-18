package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.listeners.gui.MusicGui;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class MusicCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.music"))) {
			if(args.length==1 && sender instanceof Player) {
				Player p = (Player)sender;
				Bukkit.dispatchCommand(p, "stopsound "+p.getName());
				Bukkit.dispatchCommand(p, "playsound minecraft:music.game."+args[0]+" ambient "+p.getName());
			}else if(args.length==0){
				MusicGui.musicGui();
				MusicGui.openInventory((Player)sender);
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

}
