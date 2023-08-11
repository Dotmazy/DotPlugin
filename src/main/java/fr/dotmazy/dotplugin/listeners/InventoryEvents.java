package fr.dotmazy.dotplugin.listeners;

import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.util.gui.InventoryEvent;
import fr.dotmazy.dotplugin.util.gui.ItemEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryEvents implements Listener {
	public static List<ItemEvent> itemClickEvents = new ArrayList<>();
	public static List<InventoryEvent> openEvents = new ArrayList<>();
	public static List<InventoryEvent> closeEvents = new ArrayList<>();

	@EventHandler
	public static void onClick(InventoryClickEvent event){
		ItemStack clickedItem = event.getCurrentItem();
		if(clickedItem==null) return;
		Player player = (Player) event.getWhoClicked();
		int number = event.getHotbarButton();

		for (ItemEvent itemEvent : itemClickEvents) {
			if (event.getInventory() == itemEvent.getInventory() && !event.getSlotType().equals(InventoryType.SlotType.QUICKBAR))
				if(itemEvent.getItem() == null) event.setCancelled(itemEvent.isCanceled());
				else if (clickedItem.equals(itemEvent.getItem()) && event.getSlot() == itemEvent.getSlot() && event.getClick() == itemEvent.getType()) {
					event.setCancelled(itemEvent.isCanceled());
					if (itemEvent.getEvent() != null) itemEvent.getEvent().run(itemEvent.getItem(), player,number);
				}
		}

		if(!event.isCancelled()) event.setCancelled(PlayerApi.isInModerationMode((Player)event.getWhoClicked()));

		/*if(event.getInventory() == DisplayGui.inv) {
			event.setCancelled(true);
			if (clickedItem.getItemMeta().getDisplayName().equals("Block")){
				DisplayGui.openBlockInventory(player);
			} else if (clickedItem.getItemMeta().getDisplayName().equals("Item")){
				DisplayGui.openItemInventory(player);
			} else if (clickedItem.getItemMeta().getDisplayName().equals("Text")){
				DisplayGui.openTextInventory(player);
			}
		} else if(event.getInventory() == RankGui.inv){
			event.setCancelled(true);
		} else if (event.getInventory() == MusicGui.inv) {
			event.setCancelled(true);
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

	        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.RED_STAINED_GLASS_PANE) return;
	
	        if(clickedItem.getType() == Material.STRUCTURE_VOID) {
	        	player.performCommand("stopsound "+player.getName()+" record");
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
		}*/
	}

	/*@EventHandler
	public static void onDrag(InventoryDragEvent event){
		System.out.println(event.getType());
		System.out.println(event.getRawSlots());
		System.out.println(event.getNewItems());
		System.out.println(event.getOldCursor());
	}*/

	@EventHandler
	public static void onClose(InventoryCloseEvent event){
		for(InventoryEvent inventoryEvent : closeEvents){
			if(event.getInventory() == inventoryEvent.getInventory()){
				inventoryEvent.getEvent().run((Player)event.getPlayer());
			}
		}
	}

	@EventHandler
	public static void onOpen(InventoryOpenEvent event){
		for(InventoryEvent inventoryEvent : openEvents){
			if(event.getInventory() == inventoryEvent.getInventory()){
				inventoryEvent.getEvent().run((Player)event.getPlayer());
			}
		}
	}

	/*@EventHandler
	public static void onMove(InventoryMoveItemEvent event){
		System.out.println(event.getItem());
	}*/
	
}
