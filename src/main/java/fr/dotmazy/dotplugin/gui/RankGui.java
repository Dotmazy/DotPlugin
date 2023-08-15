package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.AbstractMultiGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import org.bukkit.Material;

public class RankGui extends AbstractMultiGui {

    protected void init() {
        addGui(new RankGuiMain());
        addGui(new RankGuiCreate());
    }

    private class RankGuiMain extends AbstractGui {
        protected void init() {
            makeFrame(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"), 1);

            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7eCreate Rank").onLeftClick((item, player, number) -> {
                player.openInventory(guis.get(1).getInventory());
            }, true),45);
        }

        protected String getName() {
            return "Ranks";
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

    private class RankGuiCreate extends AbstractGui {
        protected void init() {
            makeFrame(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"), 1);

            setItem(new GuiItem(Material.BARRIER).setDisplayName("\u00A7cCancel").onLeftClick((item, player, number) -> {
                player.openInventory(guis.get(0).getInventory());
            }, true),0);
            
        }

        protected String getName() {
            return "Create Rank";
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
