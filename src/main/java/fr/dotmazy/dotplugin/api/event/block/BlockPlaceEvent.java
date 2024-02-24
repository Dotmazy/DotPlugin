package fr.dotmazy.dotplugin.api.event.block;

public class BlockPlaceEvent {
    public static BlockPlaceEvent fromEvent(org.bukkit.event.block.BlockPlaceEvent event) {
        return new BlockPlaceEvent();
    }
}
