package fr.dotmazy.dotplugin.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class SetLobbyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.setlobby"))) {
			FileConfiguration conf = Main.getInstance().getConfig();
			if(sender instanceof Player) {
				Player player = (Player) sender;
				Location loc = player.getLocation();
				conf.set("donne.lobby.loc.x", loc.getX());
				conf.set("donne.lobby.loc.y", loc.getY());
				conf.set("donne.lobby.loc.z", loc.getZ());
				conf.set("donne.lobby.loc.yaw", loc.getYaw());
				conf.set("donne.lobby.loc.pitch", loc.getPitch());
				conf.set("donne.lobby.loc.world", loc.getWorld().getName());
				Main.getInstance().saveConfig();
				player.sendMessage(Translate.translateConfigText(loc, player, "commands.setlobby.success"));
			}else {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_cant_execute_in_console"));
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

}
