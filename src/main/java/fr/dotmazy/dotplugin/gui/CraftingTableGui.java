package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.util.Player;
import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftingTableGui extends AbstractGui {
    protected void init() {}

    protected void onTick(Player player) {
        ItemStack[] craftItems = new ItemStack[]{
                player.get().getOpenInventory().getItem(1),
                player.get().getOpenInventory().getItem(2),
                player.get().getOpenInventory().getItem(3),
                player.get().getOpenInventory().getItem(4),
                player.get().getOpenInventory().getItem(5),
                player.get().getOpenInventory().getItem(6),
                player.get().getOpenInventory().getItem(7),
                player.get().getOpenInventory().getItem(8),
                player.get().getOpenInventory().getItem(9),
        };
        Recipe recipe = Bukkit.getCraftingRecipe(craftItems,player.get().getWorld());
        if(recipe!=null) setItem(new GuiItem(recipe.getResult())
                .onLeftClick((item, player1, number, slot) -> clearCraft(), false)
                .onRightClick((item, player1, number, slot) -> clearCraft(), false)
                .onShiftLeftClick((item, player1, number, slot) -> clearCraft(), false)
                .onShiftRightClick((item, player1, number, slot) -> clearCraft(), false)
                .onNumberClick((item, player1, number, slot) -> clearCraft(), false)
                , 0);
        else setItem(Material.AIR, 0);
    }

    private void clearCraft(){
        setItem(Material.AIR, 1);
        setItem(Material.AIR, 2);
        setItem(Material.AIR, 3);
        setItem(Material.AIR, 4);
        setItem(Material.AIR, 5);
        setItem(Material.AIR, 6);
        setItem(Material.AIR, 7);
        setItem(Material.AIR, 8);
        setItem(Material.AIR, 9);
    }

    protected String getName() {
        return "Custom Crafting Table";
    }

    protected GuiType getType() {
        return GuiType.CRAFTING_TABLE;
    }

    public boolean isDefaultCanceled() {
        return false;
    }
}
