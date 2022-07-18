package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class DotReloadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()||(sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.drl"))) {
			Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
			Bukkit.getServer().getPluginManager().enablePlugin(Main.getInstance());
			Main.getInstance().reloadConfig();
			sender.sendMessage("Le plugin DotPlugin vien de redémarer.");
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

}
