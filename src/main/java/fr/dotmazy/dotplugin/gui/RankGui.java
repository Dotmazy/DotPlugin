package fr.dotmazy.dotplugin.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.dotmazy.dotplugin.api.RankApi;
import fr.dotmazy.dotplugin.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RankGui {

    public static Inventory inv = Bukkit.createInventory(null, 54, "Ranks");

    public static void openInventory(final Player player) {
        inv = Bukkit.createInventory(null, 45, "§eRanks 1/"+(RankApi.getRanks().size()/45+1)+": ");

        for (int i = 0; i < 45 && i < RankApi.getRanks().size(); i++) {
            Rank rank = RankApi.getRanks().get(i);
            String[] perms = rank.getPermissions().toArray(new String[0]);
            inv.setItem(i, createGuiItem(Material.STRUCTURE_VOID, rank.getName(), perms,
                    ChatColor.DARK_GRAY+"Prefix: "+ChatColor.translateAlternateColorCodes('&',rank.getPrefix()),
                    ChatColor.DARK_GRAY+"Suffix: "+(rank.getSuffix().equals("")?"Aucun":ChatColor.translateAlternateColorCodes('&',rank.getSuffix())),
                    ChatColor.DARK_GRAY+"Permissions: "));
        }

        player.openInventory(inv);
    }

    protected static ItemStack createGuiItem(final Material material, final String name, final String[] endlore, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lores = new ArrayList<>(Arrays.asList(lore));
        lores.addAll(Arrays.asList(endlore));
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }
}
