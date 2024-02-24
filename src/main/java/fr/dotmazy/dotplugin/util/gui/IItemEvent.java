package fr.dotmazy.dotplugin.util.gui;

import fr.dotmazy.dotplugin.util.Player;
import org.bukkit.inventory.ItemStack;

public interface IItemEvent {
    void run(ItemStack item, Player player, int number, int slot);
}
