package fr.dotmazy.dotplugin.listeners;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockEvents implements Listener {

    private final DotPlugin dotPlugin;

    public BlockEvents(DotPlugin dotPlugin){
        this.dotPlugin = dotPlugin;
    }

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (DotPlugin.freezePlayers.contains(player) || DotPlugin.modPlayers.get(player)!=null) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (DotPlugin.freezePlayers.contains(player) || DotPlugin.modPlayers.get(player)!=null) event.setCancelled(true);
    }

}