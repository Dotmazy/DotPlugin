package fr.dotmazy.dotplugin.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.core.Report;
import fr.dotmazy.dotplugin.utils.ItemBuilder;
import fr.dotmazy.dotplugin.utils.Translate;

public class ReportViewCommand implements CommandExecutor {
	
	public static Inventory inv3;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender.hasPermission("dotplugin.reportview")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Translate.translateConfigText(null, "commands.error_cant_execute_in_console"));
				return false;
			}
			
			Player player = (Player) sender;
			
			inv3 = Bukkit.createInventory(null, 6*9, Translate.translateConfigText(player, "config.report.report_view_name"));
			
			try {Connection c = Main.getInstance().getDatabaseManager().getDbConnection().getConnection();
				PreparedStatement s = c.prepareStatement("SELECT * FROM reports");;
				if(args.length==1) s = c.prepareStatement("SELECT * FROM reports WHERE uuid = '"+Bukkit.getPlayer(args[0]).getUniqueId().toString()+"'");
				ResultSet result = s.executeQuery();
				List<Report> reports = new ArrayList<>();
				while(result.next()) {
					reports.add(new Report(result.getInt("#"), result.getString("name"), result.getString("raison"), result.getString("auteur"), result.getString("date"), result.getString("uuid")));
				}
				
				int i=10;
				for(Report report : reports) {
					inv3.setItem(i,getReportItem(report));
					i++;
				}
			}catch(Exception er) {
				er.printStackTrace();
			}
			
			player.openInventory(inv3);
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}
	
	private ItemStack getReportItem(Report r) {
		Material m=Material.BARRIER;
		String name=r.getName();
		ArrayList<String> lore=new ArrayList<>();
		switch(r.getReason()) {
			case "SpamBow":
				m=Material.BOW;
				break;
			case "ForceField":
				m=Material.IRON_SWORD;
				break;
			default: break;
		}

		lore.add("Auteur: "+r.getAuteur());
		lore.add("Reason: "+r.getReason());
		lore.add("Date: "+r.getDate().toString());
		
		return new ItemBuilder(m).setName(name).setLore(lore.get(0),lore.get(1),lore.get(2),r.getAuteur()).toItemStack();
	}

}
