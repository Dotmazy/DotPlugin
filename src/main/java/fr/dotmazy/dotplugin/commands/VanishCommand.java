package fr.dotmazy.dotplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.managers.PlayerManager;
import fr.dotmazy.dotplugin.utils.Translate;

public class VanishCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player&&args.length==0) {
			Player p = (Player) sender;
			if(Main.vanished.contains(p)) {
				Main.removeVanished(p);
			}else {
				Main.addVanished(p);
			}
			return false;
		}
		if(args.length==1) {
			Player target;
			String targetName = args[0];
			if(Main.getInstance().getDatabaseManager().exist(targetName)&&Main.getInstance().getDatabaseManager().isConnected(Main.getInstance().getDatabaseManager().getUUID(targetName))) {
				target = Bukkit.getPlayer(targetName);
			}else {
				sender.sendMessage(Translate.translateConfigText(null, "commands.not_connected"));
				return false;
			}
			if(Main.vanished.contains(target)) {
				Main.removeVanished(target);
			}else {
				Main.addVanished(target);
			}
		}else if(sender instanceof Player){
			sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.error_cant_execute_in_console"));
		}else {
			sender.sendMessage("§c/vanish (<player_name>)");
		}
		return false;
	}

}
