package fr.dotmazy.dotplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.ItemBuilder;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class ReportCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.report"))) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.adminchat.error_cant_execute_in_console"));
				return false;
			}
			
			Player player = (Player) sender;
				
			if(args.length != 1) {
				player.sendMessage("§cVeuillez saisir le pseudo d'un joueur !");
				return false;
			}
			
			String targetName = args[0];
			
			if(Bukkit.getPlayer(targetName) == null) {
				player.sendMessage("Ce joueur n'est pas connecté ou n'existe pas !");
				return false;
			}
			
			Player target = Bukkit.getPlayer(targetName);
			
			Inventory inv = Bukkit.createInventory(null, 18, Translate.translateConfigText(targetName, "config.report.report_gui_format", null, null, 0));
			
			FileConfiguration conf = Main.getInstance().getConfig();
			int i=0;
			for(String s : conf.getStringList("config.report.report_list")) {
				try {
					inv.setItem(i, new ItemBuilder(Material.valueOf(conf.getString("config.report.reports."+s+".material").toUpperCase())).setName(Translate.translateConfigText(player, "config.report.reports."+s+".name")).toItemStack());
				}catch(Exception e){
					inv.setItem(i, new ItemBuilder(Material.BARRIER).setName("?ERROR?").toItemStack());
				}
				i++;
			}
		
			player.openInventory(inv);
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> r = new ArrayList<>();
		if(args.length==1) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getName().startsWith(args[0])) r.add(p.getName());
			}
		}
		return r;
	}

}
