package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.AbstractMultiGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ExampleMultiGui extends AbstractMultiGui {

    protected void init() {
        addGui(new ExampleGui1());
        addGui(new ExampleGui2());
    }

    private class ExampleGui1 extends AbstractGui {
        protected void init() {
            setItem(new GuiItem(Material.STICKY_PISTON).onLeftClick((item,player,number, slot)->{
                player.get().openInventory(guis.get(1).getInventory());
            }, true).setDisplayName("\u00A7aNext"),26);
        }

        protected String getName() {
            return "Example Multi Gui 1";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        public boolean isDefaultCanceled() {
            return true;
        }
    }

    private class ExampleGui2 extends AbstractGui {
        protected void init() {
            setItem(new GuiItem(Material.STICKY_PISTON).onLeftClick((item,player,number, slot)->{
                player.get().openInventory(guis.get(0).getInventory());
            }, true).setDisplayName("\u00A7aPrevious"),18);
        }

        protected String getName() {
            return "Example Multi Gui 2";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        public boolean isDefaultCanceled() {
            return true;
        }
    }

}
