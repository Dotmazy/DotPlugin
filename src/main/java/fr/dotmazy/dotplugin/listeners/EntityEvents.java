package fr.dotmazy.dotplugin.listeners;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class EntityEvents implements Listener {

    private final DotPlugin discordPlugin;

    public EntityEvents(DotPlugin discordPlugin){
        this.discordPlugin = discordPlugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if(damager instanceof Player){
            Player player = (Player) damager;
            if (DotPlugin.modPlayers.get(player)!=null && entity instanceof Player) {
                event.setCancelled(true);
                ItemStack item = player.getInventory().getItemInMainHand();
                assert item.getType() != Material.AIR;
                if (item.getItemMeta().getDisplayName().equals("u00A7cFreeze")){
                    if (PlayerApi.isFreeze((Player)entity)){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/freeze "+entity.getName());
                        player.sendMessage("You have freeze "+entity.getName());
                    }else{
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/unfreeze "+entity.getName());
                        player.sendMessage("You have unfreeze "+entity.getName());
                    }
                }else if (item.getItemMeta().getDisplayName().equals("u00A7cMute")){
                    if (PlayerApi.isMute((Player)entity)){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/mute "+entity.getName());
                        player.sendMessage("You have mute "+entity.getName());
                    }else{
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/unmute "+entity.getName());
                        player.sendMessage("You have unmute "+entity.getName());
                    }
                }else if (item.getItemMeta().getDisplayName().equals("u00A7cKnockback")){
                    event.setCancelled(false);
                }
            }
            if (DotPlugin.freezePlayers.contains(player) || DotPlugin.modPlayers.get(player)!=null) event.setCancelled(true);
        }
    }

}