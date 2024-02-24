package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.AbstractMultiGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSelector extends AbstractMultiGui {
    public ItemSelector(AbstractMultiGui guis, int guiId){
        super(guis.getInventory(guiId));
    }

    public ItemSelector(AbstractGui gui){
        super(gui.getInventory());
    }

    protected void init() {
        System.out.println("startdd");
        Material[] items = Material.values();
        for(int page=0;page<items.length/27;page++){
            System.out.println(page);
            List<Material> pageItems = new ArrayList<>();
            for(int item=page*27;(item<items.length & item<page+27);item++) {
                pageItems.add(items[item]);
            }
            addGui(new Page(page,pageItems,(Inventory)options.get(0)));
        }
        System.out.println("startdds");
    }

    private class Page extends AbstractGui {
        public Page(int page, List<Material> items, Inventory backGui){
            super(page,items,backGui);
        }

        protected void init() {
            System.out.println("start");
            try{
                int page = (int)options.get(0);
                List<Material> items = (List<Material>)options.get(1);
                setItem(new GuiItem(Material.BARRIER).onLeftClick((item,player,number, slot)->{
                    player.get().openInventory((Inventory)options.get(2));
                }, true).setDisplayName("\u00A7aBack"),0);
                setItem(new GuiItem(Material.PAPER).onLeftClick((item,player,number, slot)->{
                    player.get().openInventory(guis.get(page+1).getInventory());
                }, true).setDisplayName("\u00A7aNext"),53);

                for(int i=0;i<items.size() & i<27;i++)
                    setItem(new GuiItem(items.get(i)).onLeftClick((item,player,number, slot)->{
                        player.get().openInventory(guis.get(1).getInventory());
                    }, true),i+9);

                setItem(new GuiItem(Material.PAPER).onLeftClick((item,player,number, slot)->{
                    player.get().openInventory(guis.get(page-1).getInventory());
                }, true).setDisplayName("\u00A7aPrevious"),46);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        protected String getName() {
            return "Item Selector";
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
