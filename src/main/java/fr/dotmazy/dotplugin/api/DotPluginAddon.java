package fr.dotmazy.dotplugin.api;

import fr.dotmazy.dotplugin.api.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class DotPluginAddon implements org.bukkit.event.Listener {
    private final List<Listener> listeners = new ArrayList<>();

    protected void addListener(Listener listener){
        listeners.add(listener);
    }

    public void onEnable() {
        //Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event){
        for(Listener listener : listeners) listener.onBlockPlace(BlockPlaceEvent.fromEvent(event));
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event){
        //for(Listener listener : listeners) listener.onBlockBreak(BlockPlaceEvent.fromEvent(event));
    }

    @EventHandler
    public void onBlockBurn(org.bukkit.event.block.BlockBurnEvent event){
        //for(Listener listener : listeners) listener.onBlockBreak(BlockPlaceEvent.fromEvent(event));
    }

}
