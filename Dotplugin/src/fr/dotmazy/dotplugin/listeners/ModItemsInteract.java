package fr.dotmazy.dotplugin.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.core.Report;
import fr.dotmazy.dotplugin.managers.PlayerManager;
import fr.dotmazy.dotplugin.utils.ItemBuilder;

public class ModItemsInteract implements Listener {

	@SuppressWarnings({ "deprecation", "static-access" })
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		if(!PlayerManager.isInModerationMod(player)) return;
		if(!(e.getRightClicked() instanceof Player)) return;
		Player target = (Player) e.getRightClicked();
		
		e.setCancelled(true);
		
		switch(player.getInventory().getItemInHand().getType()) {
			case PAPER:
				Inventory inv = Bukkit.createInventory(null, 5*9, target.getName() + " > Inventaire");
				
				for(int i = 0; i < 36; i++) {
					if(target.getInventory().getItem(i)!=null) {
						inv.setItem(i, target.getInventory().getItem(i));
					}
				}
				
				inv.setItem(36, target.getInventory().getHelmet());
				inv.setItem(37, target.getInventory().getChestplate());
				inv.setItem(38, target.getInventory().getLeggings());
				inv.setItem(39, target.getInventory().getBoots());
				
				player.openInventory(inv);
				break;
				
			case BOOK:
				Inventory inv2 = Bukkit.createInventory(null, 5*9, target.getName() + " > Reports");
				
				try {Connection c = Main.getInstance().getDatabaseManager().getDbConnection().getConnection();
					PreparedStatement s = c.prepareStatement("SELECT * FROM reports WHERE uuid = '"+target.getUniqueId()+"'");
					ResultSet result = s.executeQuery();
					List<Report> reports = new ArrayList<>();
					while(result.next()) {
						reports.add(new Report(result.getInt("id"),result.getString("name"), result.getString("raison"), result.getString("auteur"), result.getTime("date").toString(), result.getString("uuid")));
					}
					
					int i=0;
					for(Report report : reports) {
						inv2.setItem(i,getReportItem(report));
						i++;
					}
				}catch(Exception er) {
					er.printStackTrace();
				}
				
				player.openInventory(inv2);
				break;
				
			case PACKED_ICE:
				if(Main.getInstance().freeze.contains(target)) {
					Main.getInstance().freeze.remove(target);
				}else {
					Main.getInstance().freeze.add(target);
				}
				break;
			case BLAZE_ROD:
				target.damage(target.getHealth());
				break;
				
			default: break;
		}
		
	}
	
	private ItemStack getReportItem(Report r) {
		Material m=Material.BARRIER;
		String name=r.getReason();
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
		lore.add("Date: "+r.getDate());
		
		return new ItemBuilder(m).setName(name).setLore(lore.get(0),lore.get(1),lore.get(2)).toItemStack();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(!PlayerManager.isInModerationMod(player)) return;
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;
		
		switch(player.getInventory().getItemInHand().getType()) {
		
			case ARROW:
				List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
				list.remove(player);
				
				if(list.size() == 0) {
					player.sendMessage("§cIl n'y a aucun joueur sur lequel vous téléporter.");
					return;
				}
				
				Player target = list.get(new Random().nextInt(list.size()));
				player.teleport(target.getLocation());
				player.sendMessage("§aVous avez été téléporté à §e" + target.getName());
				
				break;
				
			case BLAZE_POWDER:
				PlayerManager mod = PlayerManager.getFromPlayer(player);
				mod.setVanished(!mod.isVanished());
				if(mod.isVanished()) {
					player.sendMessage("§aVous êtes à présent invisible !");
				}else {
					player.sendMessage("§bVous êtes à présent visible !");
				}
				break;
				
			default: break;
		}
	
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(PlayerManager.isInModerationMod(players)) {
				PlayerManager pm = PlayerManager.getFromPlayer(players);
				if(pm.isVanished()) {
					player.hidePlayer(player);
				}
			}
		}
	}
	
}
