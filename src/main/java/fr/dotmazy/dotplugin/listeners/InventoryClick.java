package fr.dotmazy.dotplugin.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.listeners.gui.MusicGui;
import fr.dotmazy.dotplugin.managers.PlayerManager;
import fr.dotmazy.dotplugin.mysql.DbConnection;
import fr.dotmazy.dotplugin.utils.Translate;

public class InventoryClick implements Listener {
	
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent event){
		event.setCancelled(PlayerManager.isInModerationMod((Player)event.getWhoClicked()));
		if(event.getCurrentItem()==null) return;
		if (event.getInventory() == MusicGui.inv) {
			event.setCancelled(true);
	        final ItemStack clickedItem = event.getCurrentItem();
	        final Player player = (Player) event.getWhoClicked();
	        if(clickedItem.getType() == Material.BARRIER) {
	        	player.performCommand("music ne-rage-quitte-pas");
	        }
	        if(clickedItem.getType() == Material.CUT_SANDSTONE) {
	        	player.performCommand("music jai-la-dalle");
	        }
	        if(clickedItem.getType() == Material.LIGHT_BLUE_TERRACOTTA) {
	        	player.performCommand("music ce-soir");
	        }
	        if(clickedItem.getType() == Material.JUKEBOX) {
	        	player.performCommand("music court-metrage-musicale");
	        }
	        if(clickedItem.getType() == Material.BLUE_TERRACOTTA) {
	        	player.performCommand("music dans-la-nuit");
	        }
	        if(clickedItem.getType() == Material.PLAYER_HEAD) {
	        	player.performCommand("music herobrine");
	        }
	        if(clickedItem.getType() == Material.CREEPER_HEAD) {
	        	player.performCommand("music jaimerais-trop-quil-creve");
	        }
	        if(clickedItem.getType() == Material.BLACK_CONCRETE) {
	        	player.performCommand("music jai-sombre");
	        }
	        if(clickedItem.getType() == Material.SKELETON_SKULL) {
	        	player.performCommand("music jarrive-pas-a-pecho");
	        }
	        if(clickedItem.getType() == Material.ZOMBIE_HEAD) {
	        	player.performCommand("music jean-kevin");
	        }
	        if(clickedItem.getType() == Material.OAK_PLANKS) {
	        	player.performCommand("music je-construis-ma-maison");
	        }
	        if(clickedItem.getType() == Material.TROPICAL_FISH_BUCKET) {
	        	player.performCommand("music je-toublierais-jamais");
	        }
	        if(clickedItem.getType() == Material.COARSE_DIRT) {
	        	player.performCommand("music la-pire-des-teams");
	        }
	        if(clickedItem.getType() == Material.WHITE_CONCRETE) {
	        	player.performCommand("music le-temp-dun-reve");
	        }
	        if(clickedItem.getType() == Material.STRUCTURE_VOID) {
	        	player.performCommand("stopsound "+player.getName());
	        }
	        if(clickedItem.getType() == Material.GOLDEN_SHOVEL) {
	        	player.performCommand("music ma-pelle");
	        }
	        if(clickedItem.getType() == Material.CREEPER_SPAWN_EGG) {
	        	player.performCommand("music marie-a-un-creeper");
	        }
	        if(clickedItem.getType() == Material.DIAMOND) {
	        	player.performCommand("music mon-diams-ou-tes");
	        }
	        if(clickedItem.getType() == Material.RED_CARPET) {
	        	player.performCommand("music noel-pour-toi-cest-mort");
	        }
	        if(clickedItem.getType() == Material.DRAGON_EGG) {
	        	player.performCommand("music pas-lniveau");
	        }
	        if(clickedItem.getType() == Material.ENDER_PEARL) {
	        	player.performCommand("music pgm");
	        }
	        if(clickedItem.getType() == Material.CLOCK) {
	        	player.performCommand("music plus-tard");
	        }
			if(clickedItem.getType() == Material.MAP) {
				MusicGui.musicGui2();
				MusicGui.openInventory2(player);
	        }
		}else if (event.getInventory() == MusicGui.inv2) {
			event.setCancelled(true);
			
	        final ItemStack clickedItem = event.getCurrentItem();
	
	        // verify current item is not null
	        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.RED_STAINED_GLASS_PANE) return;
	
	        final Player player = (Player) event.getWhoClicked();
	
	        if(clickedItem.getType() == Material.STRUCTURE_VOID) {
	        	player.performCommand("stopsound "+player.getName());
	        }
	        if(clickedItem.getType() == Material.PUMPKIN) {
	        	player.performCommand("music yen-a-marre-dhalloween");
	        }
	        if(clickedItem.getType() == Material.CREEPER_HEAD) {
	        	player.performCommand("music tu-as-explose");
	        }
	        if(clickedItem.getType() == Material.TNT) {
	        	player.performCommand("music tout-casser");
	        }
	        if(clickedItem.getType() == Material.MUSIC_DISC_11) {
	        	player.performCommand("music toi-et-moi");
	        }
	        if(clickedItem.getType() == Material.FILLED_MAP) {
	        	player.performCommand("music sur-ma-map");
	        }
	        if(clickedItem.getType() == Material.PLAYER_HEAD) {
	        	player.performCommand("music sur-blood-symphony");
	        }
	        if(clickedItem.getType() == Material.VILLAGER_SPAWN_EGG) {
	        	player.performCommand("music save-me");
	        }
	        if(clickedItem.getType() == Material.COARSE_DIRT) {
	        	player.performCommand("music roots-noob");
	        }
	        if(clickedItem.getType() == Material.REDSTONE) {
	        	player.performCommand("music redstone");
	        }
	        if(clickedItem.getType() == Material.STONE) {
	        	player.performCommand("music popopop-bloc-par-bloc");
	        }
	        if(clickedItem.getType() == Material.ENDERMAN_SPAWN_EGG) {
	        	player.performCommand("music enderman");
	        }
	        if(clickedItem.getType() == Material.ZOMBIE_SPAWN_EGG) {
	        	player.performCommand("music zombie");
	        }
	        if(clickedItem.getType() == Material.PAPER) {
				MusicGui.musicGui();
				MusicGui.openInventory(player);
	        }
	        return;
		}
		if(event.getView().getTitle().equalsIgnoreCase("Reports")) {
			event.setCancelled(true);
			
			return;
		}
		if(event.getView().getTitle().startsWith("§bReport: ")) {
			int cooldownTime = 1; 
            if(cooldowns.containsKey(event.getWhoClicked().getName())) {
                long secondsLeft = ((cooldowns.get(event.getWhoClicked().getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                if(secondsLeft>0) {
                	return;
                }
            }
            cooldowns.put(event.getWhoClicked().getName(), System.currentTimeMillis());
			Player player = (Player) event.getWhoClicked();
			
			FileConfiguration conf = Main.getInstance().getConfig();
			for(String s : conf.getStringList("config.report.report_list")) {
				try {
					ItemStack it = event.getCurrentItem();
					if(it.getItemMeta().getDisplayName().equals(Translate.translateConfigText(player, "config.report.reports."+s+".name"))&&it.getType()==Material.valueOf(conf.getString("config.report.reports."+s+".material").toUpperCase())) {
						event.setCancelled(true);
						player.closeInventory();
						sendToMods(event.getCurrentItem().getItemMeta().getDisplayName(), event.getView().getTitle().substring(12), Bukkit.getPlayer(event.getView().getTitle().substring(12)).getUniqueId().toString(), player.getName());
						player.sendMessage("§aVous avez bien signalé ce joueur !");
					}
				}catch(Exception e){
					player.sendMessage("§cUne erreur est survenu !");
				}
			}
		}
	}
	
	private void sendToMods(String reason, String targetName, String uuid, String playerName) {
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(players.hasPermission("mod.receive")) {
				players.sendMessage("§bLe joueur " + targetName + " §ba été signalé pour : " + reason + " §bpar " + playerName);
				try {
					final DbConnection dbConnection = Main.getInstance().getDatabaseManager().getDbConnection();
					final Connection connection = dbConnection.getConnection();
					final PreparedStatement pS = connection.prepareStatement("INSERT INTO reports(name,uuid,date,auteur,raison) VALUE (?, ?, ?, ?, ?)");
					pS.setString(1, targetName);
					pS.setString(2, uuid);
					pS.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					pS.setString(4, playerName);
					pS.setString(5, reason.replace("§c", ""));
					pS.executeUpdate();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
}
