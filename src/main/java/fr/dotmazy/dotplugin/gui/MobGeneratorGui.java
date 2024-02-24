package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.AbstractMultiGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import org.bukkit.Material;

public class MobGeneratorGui extends AbstractMultiGui {

    protected void init() {
        addGui(new Main());
        addGui(new TradeEditor());
    }

    private class Main extends AbstractGui {
        protected void init() {
            setItem(new GuiItem(Material.EMERALD).onLeftClick((item,player,number, slot)->{
                player.get().openInventory(guis.get(1).getInventory());
            }, true).setDisplayName("\u00A7aTrades"),0);
        }

        protected String getName() {
            return "Mob Generator";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        protected int getSize() {
            return 54;
        }

        public boolean isDefaultCanceled() {
            return true;
        }
    }

    private class TradeEditor extends AbstractGui {
        protected void init() {
            setItem(new GuiItem(Material.STICKY_PISTON).onLeftClick((item,player,number, slot)->{
                player.get().openInventory(guis.get(0).getInventory());
            }, true).setDisplayName("\u00A7aBack"),0);
            setItem(new GuiItem(Material.STICKY_PISTON).onLeftClick((item,player,number, slot)->{
                player.get().openInventory(new ItemSelector(this).getMainInventory());
            }, true).setDisplayName("\u00A7aItem"),9);
        }

        protected String getName() {
            return "Trade Editor";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        protected int getSize() {
            return 54;
        }

        public boolean isDefaultCanceled() {
            return true;
        }
    }

    private class ItemEditor extends AbstractGui {
        protected void init() {
            setItem(new GuiItem(Material.STICKY_PISTON).onLeftClick((item,player,number, slot)->{
                player.get().openInventory(guis.get(0).getInventory());
            }, true).setDisplayName("\u00A7aBack"),18);
        }

        protected String getName() {
            return "Trade Editor";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        protected int getSize() {
            return 54;
        }

        public boolean isDefaultCanceled() {
            return true;
        }
    }

}
