package fr.dotmazy.dotplugin.util.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IItemEvent {
    void run(ItemStack item, Player player, int number);
}
