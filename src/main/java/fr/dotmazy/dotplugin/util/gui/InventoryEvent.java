package fr.dotmazy.dotplugin.util.gui;

import org.bukkit.inventory.Inventory;

public class InventoryEvent {
    private final Inventory inv;
    private final IInventoryEvent event;

    public InventoryEvent(Inventory inv, IInventoryEvent event){
        this.inv = inv;
        this.event = event;
    }

    public IInventoryEvent getEvent() {
        return event;
    }

    public Inventory getInventory() {
        return inv;
    }
}
