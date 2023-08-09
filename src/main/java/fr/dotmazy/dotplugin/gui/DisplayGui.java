package fr.dotmazy.dotplugin.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DisplayGui extends Gui {

    public static Inventory blockinv;
    public static Inventory iteminv;
    public static Inventory textinv;
    public static void initializeItems() {
        createGuiFrame(Material.BLACK_STAINED_GLASS_PANE);
        inv.setItem(20, createGuiItem(Material.GRASS_BLOCK, "Block"));
        inv.setItem(22, createGuiItem(Material.DIAMOND, "Item"));
        inv.setItem(24, createGuiItem(Material.COMMAND_BLOCK, "Text"));
    }

    public static void initializeBlockItems() {
        createGuiFrame(Material.BLACK_STAINED_GLASS_PANE);
        //inv.setItem(20, createGuiItem(Material.));
    }

    public static void initializeItemItems() {

    }

    public static void initializeTextItems() {

    }

    public static void openInventory(final Player player) {
        inv = Bukkit.createInventory(null, 45, "§6Display: ");
        initializeItems();
        player.openInventory(inv);
    }

    public static void openBlockInventory(final Player player) {
        blockinv = Bukkit.createInventory(null, 45, "§6§lBlock §r§6Display: ");
        initializeBlockItems();
        player.openInventory(blockinv);
    }

    public static void openItemInventory(final Player player) {
        iteminv = Bukkit.createInventory(null, 45, "§6§lItem §r§6Display: ");
        initializeItemItems();
        player.openInventory(iteminv);
    }

    public static void openTextInventory(final Player player) {
        textinv = Bukkit.createInventory(null, 45, "§6§lText §r§6Display: ");
        initializeTextItems();
        player.openInventory(textinv);
    }

}
