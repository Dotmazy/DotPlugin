package fr.dotmazy.dotplugin.util.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class TickEvent {
    private final Inventory inv;
    private final ITickEvent event;
    private Map<Player,Boolean> active = new HashMap<>();

    public TickEvent(Inventory inv, ITickEvent event){
        this.inv = inv;
        this.event = event;
    }

    public ITickEvent getEvent() {
        return event;
    }

    public Inventory getInventory() {
        return inv;
    }

    public TickEvent setActive(Player player, boolean active) {
        if(this.active.get(player) != null) this.active.replace(player,active);
        else this.active.put(player,active);
        return this;
    }

    public boolean isActive(Player player) {
        return active.containsKey(player) && active.get(player);
    }
}
