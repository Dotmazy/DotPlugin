package fr.dotmazy.dotplugin.util.gui;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.listeners.InventoryEvents;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**@author Dotmazy */
abstract public class AbstractGui {

    private final Inventory inv;
    private Map<Integer, GuiItem> items;
    private boolean isInit = false;

    public AbstractGui(){
        if(getType()!=GuiType.CHEST){
            if(getName()!=null) inv = Bukkit.createInventory(null, getType().getType(), getName());
            else inv = Bukkit.createInventory(null, getType().getType());
        }else{
            if(getSize()==0 || getSize()%9!=0 || getSize()>54) throw new IllegalArgumentException("The size of the gui must be a multiple of 9 between 0 and 54 (got "+getSize()+")");
            if(getName()!=null) inv = Bukkit.createInventory(null, getSize(), getName());
            else inv = Bukkit.createInventory(null, getSize());
        }
        items = new HashMap<>();
        init();
    };

    /**
     * Initialization of the gui.
     * */
    abstract protected void init();

    /**
     * @re
     * */
    protected int getTickDelay(){ return 1; };

    /**
     * @return The name of the gui.
     * */
    abstract protected String getName();

    /**
     * @return The type of the gui.
     * */
    abstract protected GuiType getType();

    /**
     * @return The size of the gui. (only if the type is a chest)
     * @apiNote The size of the gui must be a multiple of 9.
     * By default, it's 27.
     * */
    protected int getSize(){
        return 27;
    }

    /**
     * @return If the click event is canceled by default when gui open.
     * */
    abstract public boolean isDefaultCanceled();

    /**
     * @return The inventory.
     * */
    public Inventory getInventory(){
        if(!isInit){
            isInit = true;
            for (int slot : items.keySet()) inv.setItem(slot, items.get(slot).getItem());
            if(isDefaultCanceled()) InventoryEvents.itemClickEvents.add(new ItemEvent(inv));
            for (int slot : items.keySet()) items.get(slot).create(inv, isDefaultCanceled());
            InventoryEvents.openEvents.add(new InventoryEvent(this.inv, this::onOpen));
            InventoryEvents.closeEvents.add(new InventoryEvent(this.inv, this::onClose));
            InventoryEvents.openEvents.add(new InventoryEvent(this.inv, this::onOpenForce));
            InventoryEvents.closeEvents.add(new InventoryEvent(this.inv, this::onCloseForce));
            DotPlugin.tickEvents.put(this, new TickEvent(this.inv, this::onTick));
        }
        return this.inv;
    }

    private void onOpenForce(Player player){
        DotPlugin.tickEvents.replace(this, DotPlugin.tickEvents.get(this).setActive(player, true));
    }

    private void onCloseForce(Player player){
        DotPlugin.tickEvents.replace(this, DotPlugin.tickEvents.get(this).setActive(player, false));
    }

    /**This function is call when open the inventory */
    protected void onOpen(Player player){}

    /**This function is call when close the inventory */
    protected void onClose(Player player){}

    /**This function is call every tick */
    protected void onTick(Player player){};

    /**
     * @param item The item to put.
     * @param slot The slot where put the item. (between 0 and size of the gui.
     * <p></p>
     * Put an item on a slot.
     * */
    protected void setItem(GuiItem item, int slot){
        if(slot<inv.getSize() && slot>=0){
            items.put(slot, item);
            item.setSlot(slot);
        }else throw new IllegalArgumentException("Slot number out of range (slot>size or slot<0).");
    }

    /**
     * @param item The item to put.
     * @param slot The slot where put the item. (between 0 and size of the gui)
     * <p></p>
     * Put an item on a slot.
     * */
    protected void setItem(ItemStack item, int slot){
        setItem(new GuiItem(item), slot);
    }

    /**
     * @param type The type of the item to put.
     * @param slot The slot where put the item. (between 0 and size of the gui)
     * <p></p>
     * Put an item on a slot.
     * */
    protected void setItem(Material type, int slot){
        setItem(new GuiItem(type), slot);
    }

    /**
     * @param item The item to put.
     * @param slot The slot where put the item. (between 0 and size of the gui.
     * <p></p>
     * Put an item on a slot. (Only use in the onTick function)
     * */
    protected void setItemOnTick(GuiItem item, int slot){
        if(slot<inv.getSize() && slot>=0){
            items.put(slot, item);
            item.setSlot(slot);
            inv.setItem(slot, item.getItem());
            item.create(inv, isDefaultCanceled());

        }else throw new IllegalArgumentException("Slot number out of range (slot>size or slot<0).");
    }

    /**
     * @param item The item to put.
     * @param slot The slot where put the item. (between 0 and size of the gui)
     * <p></p>
     * Put an item on a slot. (Only use in the onTick function)
     * */
    protected void setItemOnTick(ItemStack item, int slot){
        setItemOnTick(new GuiItem(item), slot);
    }

    /**
     * @param type The type of the item to put.
     * @param slot The slot where put the item. (between 0 and size of the gui)
     * <p></p>
     * Put an item on a slot. (Only use in the onTick function)
     * */
    protected void setItemOnTick(Material type, int slot){
        setItemOnTick(new GuiItem(type), slot);
    }

    /**
     * @param item The item to make the frame.
     * @param borderSize The size of the border of the frame.
     * @apiNote In certain case it's equivalent to a fill.
     * <p></p>
     * Make a frame on the gui.
     * */
    protected void makeFrame(GuiItem item, int borderSize){
        switch (getType()) {
            case CHEST -> {
                switch (getSize()) {
                    case 9, 18 -> fill(item);
                    case 27 -> {
                        if (borderSize == 1) {
                            for (int i = 0; i < 10; i++) setItem(item, i);
                            for (int i = 17; i < 27; i++) setItem(item, i);
                        } else fill(item);
                    }
                    case 36 -> {
                        if (borderSize == 1) {
                            for (int i = 0; i < 10; i++) setItem(item, i);
                            for (int i = 17; i < 19; i++) setItem(item, i);
                            for (int i = 26; i < 36; i++) setItem(item, i);
                        } else fill(item);
                    }
                    case 45 -> {
                        if (borderSize == 1) {
                            for (int i = 0; i < 10; i++) setItem(item, i);
                            for (int i = 17; i < 19; i++) setItem(item, i);
                            for (int i = 26; i < 28; i++) setItem(item, i);
                            for (int i = 35; i < 45; i++) setItem(item, i);
                        } else if (borderSize == 2) {
                            for (int i = 0; i < 20; i++) setItem(item, i);
                            for (int i = 25; i < 45; i++) setItem(item, i);
                        } else fill(item);
                    }
                    case 54 -> {
                        if (borderSize == 1) {
                            for (int i = 0; i < 10; i++) setItem(item, i);
                            for (int i = 17; i < 19; i++) setItem(item, i);
                            for (int i = 26; i < 28; i++) setItem(item, i);
                            for (int i = 35; i < 37; i++) setItem(item, i);
                            for (int i = 44; i < 54; i++) setItem(item, i);
                        } else if (borderSize == 2) {
                            for (int i = 0; i < 20; i++) setItem(item, i);
                            for (int i = 25; i < 29; i++) setItem(item, i);
                            for (int i = 34; i < 54; i++) setItem(item, i);
                        } else fill(item);
                    }
                }
            }
            case CRAFTING_TABLE -> {
                if (borderSize == 1) {
                    for (int i = 1; i < 6; i++) setItem(item, i);
                    for (int i = 7; i < inv.getSize(); i++) setItem(item, i);
                } else fill(item);
            }
            case PLAYER_INVENTORY -> {
                if (borderSize == 1) {
                    for (int i = 0; i < 10; i++) setItem(item, i);
                    for (int i = 17; i < 19; i++) setItem(item, i);
                    for (int i = 26; i < 36; i++) setItem(item, i);
                } else fill(item);
            }
            case ENDER_CHEST, SHULKER_BOX, BARREL -> {
                if (borderSize == 1) {
                    for (int i = 0; i < 10; i++) setItem(item, i);
                    for (int i = 17; i < 27; i++) setItem(item, i);
                } else fill(item);
            }
            case DISPENSER, DROPPER -> {
                if (borderSize == 1) {
                    for (int i = 0; i < 5; i++) setItem(item, i);
                    for (int i = 6; i < inv.getSize(); i++) setItem(item, i);
                } else fill(item);
            }
            default -> fill(item);
        }
    }

    /**
     * @param item The item to make the frame.
     * @param borderSize The size of the border of the frame.
     * @apiNote For a chest gui with size 9 and 18 it's equivalent to a fill.
     * <p></p>
     * Make a frame on the gui.
     * */
    protected void makeFrame(ItemStack item, int borderSize){
        makeFrame(new GuiItem(item), borderSize);
    }

    /**
     * @param type The type of the item to make the frame.
     * @param borderSize The size of the border of the frame.
     * @apiNote For a chest gui with size 9 and 18 it's equivalent to a fill.
     * <p></p>
     * Make a frame on the gui.
     * */
    protected void makeFrame(Material type, int borderSize){
        makeFrame(new GuiItem(type), borderSize);
    }

    /**
     * @param item The item to fill.
     * <p></p>
     * Fill the gui with an item.
     * */
    protected void fill(GuiItem item){
        for(int i=0;i<inv.getSize();i++) setItem(item, i);
    }

    /**
     * @param item The item to fill.
     * <p></p>
     * Fill the gui with an item.
     * */
    protected void fill(ItemStack item){
        fill(new GuiItem(item));
    }

    /**
     * @param type The type of the item to fill.
     * <p></p>
     * Fill the gui with an item.
     * */
    protected void fill(Material type){
        fill(new GuiItem(type));
    }

    /**
     * @param item The item to make the square.
     * <p></p>
     * Make a square.
     * */
    protected void makeSquare(GuiItem item, int posX, int posY, int sizeX, int sizeY){
        if(sizeX!=0 && sizeY!=0) {
            for(int y=0;y<sizeY;y++)
                for(int x=posX+(posY+y)*9;x<posX+(posY+y)*9+sizeX;x++)
                    if(x<getSize()) setItem(item, x);
        }else throw new IllegalArgumentException("Size x or y of a square can't be 0");
    }

    /**
     * @param item The item to make the square.
     * <p></p>
     * Make a square.
     * */
    protected void makeSquare(ItemStack item, int posX, int posY, int sizeX, int sizeY){
        makeSquare(new GuiItem(item), posX, posY, sizeX, sizeY);
    }

    /**
     * @param type The type of the item to make the square.
     * <p></p>
     * Make a square.
     * */
    protected void makeSquare(Material type, int posX, int posY, int sizeX, int sizeY){
        makeSquare(new GuiItem(type), posX, posY, sizeX, sizeY);
    }

}
