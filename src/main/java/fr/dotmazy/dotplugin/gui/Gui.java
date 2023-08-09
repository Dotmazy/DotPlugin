package fr.dotmazy.dotplugin.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Gui {

    public static Inventory inv;

    protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    protected static void fillInventory(ItemStack item) {
        for (int i=0;i<inv.getSize(); i++)
            inv.setItem(i,item);
    }

    protected static void createHorizontalLine(ItemStack item, int position, int size) {
        if (size<10 && size>0){
            for (int i=position; i<position+size; i++)
                inv.setItem(i, item);
        }
    }

    protected static void createVerticalLine(ItemStack item, int position, int size) {
        if (size<inv.getSize()/9+1 && size>0){
            for (int i=position; i<position+(size*9); i+=9)
                inv.setItem(i, item);
        }
    }

    protected static void createGuiFrame(ItemStack item) {
        if (inv.getSize()==9) {
            createHorizontalLine(item,0,9);
        } else if(inv.getSize()==18){
            createHorizontalLine(item, 0, 9);
            createHorizontalLine(item, 9, 9);
        } else if(inv.getSize()==27){
            createHorizontalLine(item, 0, 9);
            createHorizontalLine(item, 9, 9);
            createVerticalLine(item, 9, 1);
            createVerticalLine(item, 17, 1);
        } else if(inv.getSize()==36){
            createHorizontalLine(item, 0, 9);
            createHorizontalLine(item, 27, 9);
            createVerticalLine(item, 9, 2);
            createVerticalLine(item, 17, 2);
        } else if(inv.getSize()==45){
            createHorizontalLine(item, 0, 9);
            createHorizontalLine(item, 36, 9);
            createVerticalLine(item, 9, 3);
            createVerticalLine(item, 17, 3);
        } else if(inv.getSize()==54){
            createHorizontalLine(item, 0, 9);
            createHorizontalLine(item, 45, 9);
            createVerticalLine(item, 9, 4);
            createVerticalLine(item, 17, 4);
        }
    }

    protected static void createGuiFrame(Material material){
        createGuiFrame(createGuiItem(material,""));
    }

}
