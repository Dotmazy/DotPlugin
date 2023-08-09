package fr.dotmazy.dotplugin.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MusicGui {
	
	public static Inventory inv = Bukkit.createInventory(null, 54, "Music");
	
	public static Inventory inv2 = Bukkit.createInventory(null, 54, "Music");
	
	public static void musicGui() {
        inv = Bukkit.createInventory(null, 45, "§5Musics: ");
        initializeItems();
    }
	
	public static void musicGui2() {
        inv2 = Bukkit.createInventory(null, 45, "§5Musics: ");
        initializeItems2();
    }
	
	public static void openInventory(final Player player) {
        player.openInventory(inv);
    }
    
    public static void openInventory2(final Player player) {
        player.openInventory(inv2);
    }
	
	public static void initializeItems() {
    	inv.setItem(0, createGuiItem(Material.STRUCTURE_VOID, "§4STOP MUSIC", ""));
        inv.setItem(10, createGuiItem(Material.BARRIER, "§bNe rage quitte pas", ""));
        inv.setItem(11, createGuiItem(Material.CUT_SANDSTONE, "§bJai la dalle", ""));
        inv.setItem(12, createGuiItem(Material.LIGHT_BLUE_TERRACOTTA, "§bCe soir", ""));
        inv.setItem(13, createGuiItem(Material.JUKEBOX, "§bCourt metrage musicale", ""));
        inv.setItem(14, createGuiItem(Material.BLUE_TERRACOTTA, "§bDans la nuit", ""));
        inv.setItem(15, createGuiItem(Material.PLAYER_HEAD, "§bHerobrine", ""));
        inv.setItem(16, createGuiItem(Material.CREEPER_HEAD, "§bJ'aimerais trop quil cr§ve", ""));
        inv.setItem(19, createGuiItem(Material.BLACK_CONCRETE, "§bJ'ai sombr§", ""));
        inv.setItem(20, createGuiItem(Material.SKELETON_SKULL, "§bJarrive pas a pecho", ""));
        inv.setItem(21, createGuiItem(Material.ZOMBIE_HEAD, "§bJean kevin", ""));
        inv.setItem(22, createGuiItem(Material.OAK_PLANKS, "§bJe construis ma maison", ""));
        inv.setItem(23, createGuiItem(Material.TROPICAL_FISH_BUCKET, "§bJe t'oublirais jamais", ""));
        inv.setItem(24, createGuiItem(Material.COARSE_DIRT, "§bLa pire des team", ""));
        inv.setItem(25, createGuiItem(Material.WHITE_CONCRETE, "§bLe temp d'un r§ve", ""));
        inv.setItem(28, createGuiItem(Material.GOLDEN_SHOVEL, "§bMa pelle", ""));
        inv.setItem(29, createGuiItem(Material.CREEPER_SPAWN_EGG, "§bMari§ a un creeper", ""));
        inv.setItem(30, createGuiItem(Material.DIAMOND, "§bMon diams ou tes", ""));
        inv.setItem(31, createGuiItem(Material.RED_CARPET, "§bNoel pour toi c'est mort", ""));
        inv.setItem(32, createGuiItem(Material.DRAGON_EGG, "§bPas l'niveau", ""));
        inv.setItem(33, createGuiItem(Material.ENDER_PEARL, "§bPgm", ""));
        inv.setItem(34, createGuiItem(Material.CLOCK, "§bPlus tard", ""));
        
        //inv.setItem(39, createGuiItem(Material.PAPER, "§fBACK", ""));
        inv.setItem(41, createGuiItem(Material.MAP, "§fNEXT", ""));
    }
    
    public static void initializeItems2() {
    	inv.setItem(0, createGuiItem(Material.STRUCTURE_VOID, "§4STOP MUSIC", ""));
    	inv2.setItem(10, createGuiItem(Material.PUMPKIN, "§4Yen a marre d'halloween", ""));
        inv2.setItem(11, createGuiItem(Material.CREEPER_HEAD, "§bTu as explos§", ""));
        inv2.setItem(12, createGuiItem(Material.TNT, "§bTout casser", ""));
        inv2.setItem(13, createGuiItem(Material.MUSIC_DISC_11, "§bToi et moi", ""));
        inv2.setItem(14, createGuiItem(Material.FILLED_MAP, "§bSur ma map", ""));
        inv2.setItem(15, createGuiItem(Material.PLAYER_HEAD, "§bSur blood symphony", ""));
        inv2.setItem(16, createGuiItem(Material.VILLAGER_SPAWN_EGG, "§bSave me", ""));
        inv2.setItem(19, createGuiItem(Material.COARSE_DIRT, "§bRoots noob", ""));
        inv2.setItem(20, createGuiItem(Material.REDSTONE, "§bRedstone", ""));
        inv2.setItem(21, createGuiItem(Material.STONE, "§bPopopop bloc par bloc", ""));
        inv2.setItem(22, createGuiItem(Material.ENDERMAN_SPAWN_EGG, "§bEnderman", ""));
        inv2.setItem(23, createGuiItem(Material.ZOMBIE_SPAWN_EGG, "§bZombie", ""));
        /*inv2.setItem(24, createGuiItem(Material.COARSE_DIRT, "§bLa pire des team", ""));
        inv2.setItem(25, createGuiItem(Material.WHITE_CONCRETE, "§bLe temp d'un raive", ""));
        inv2.setItem(28, createGuiItem(Material.GOLDEN_SHOVEL, "§bMa pelle", ""));
        inv2.setItem(29, createGuiItem(Material.CREEPER_SPAWN_EGG, "§bMari§ a un creeper", ""));
        inv2.setItem(30, createGuiItem(Material.DIAMOND, "§bMon diams ou tes", ""));
        inv2.setItem(31, createGuiItem(Material.RED_CARPET, "§bNoel pour toi c'est mort", ""));
        inv2.setItem(32, createGuiItem(Material.DRAGON_EGG, "§bPas l'niveau", ""));
        inv2.setItem(33, createGuiItem(Material.ENDER_PEARL, "§bPgm", ""));
        inv2.setItem(34, createGuiItem(Material.CLOCK, "§bPlus tard", ""));*/
        
        inv2.setItem(39, createGuiItem(Material.PAPER, "§fBACK", ""));
        //inv2.setItem(41, createGuiItem(Material.MAP, "§fNEXT", ""));
    }
    
    protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
