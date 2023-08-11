package fr.dotmazy.dotplugin.util.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemEvent {
    private Inventory inv;
    private final ClickType type;
    private final ItemStack item;
    private int slot;
    private IItemEvent event;
    private boolean cancelEvent;

    public ItemEvent(Inventory inv){
        this.inv = inv;
        this.type = null;
        this.item = null;
        this.event = null;
        this.cancelEvent = true;
    }

    public ItemEvent(ClickType type, ItemStack item){
        this.inv = null;
        this.type = type;
        this.item = item;
        this.event = null;
        this.cancelEvent = false;
    }

    public Inventory getInventory(){
        return this.inv;
    }

    public void setInventory(Inventory inv){
        this.inv = inv;
    }

    public ClickType getType() {
        return this.type;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public IItemEvent getEvent() {
        return this.event;
    }

    public void setEvent(IItemEvent event, boolean cancelEvent){
        this.event = event;
        this.cancelEvent = cancelEvent;
    }

    public boolean isCanceled() {
        return this.cancelEvent;
    }

    public void setCanceled(boolean cancelEvent) {
        this.cancelEvent = cancelEvent;
    }

}
