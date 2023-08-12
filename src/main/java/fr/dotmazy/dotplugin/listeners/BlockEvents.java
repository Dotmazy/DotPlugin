package fr.dotmazy.dotplugin.listeners;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvents implements Listener {

    private final DotPlugin dotPlugin;

    public BlockEvents(DotPlugin dotPlugin){
        this.dotPlugin = dotPlugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (DotPlugin.freezePlayers.contains(player) || DotPlugin.modPlayers.get(player)!=null) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (DotPlugin.freezePlayers.contains(player) || DotPlugin.modPlayers.get(player)!=null) event.setCancelled(true);
    }

}