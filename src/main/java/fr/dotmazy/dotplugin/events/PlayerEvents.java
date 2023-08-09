package fr.dotmazy.dotplugin.events;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.gui.DisplayGui;
import fr.dotmazy.dotplugin.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PlayerEvents implements Listener {

    private final DotPlugin dotPlugin;

    public PlayerEvents(DotPlugin dotPlugin){
        this.dotPlugin = dotPlugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        player.addAttachment(dotPlugin, "", true);
        if(DotPlugin.perms.contains(player)){
            event.setCancelled(true);
            for (Player pl : Bukkit.getOnlinePlayers()){
                if (pl.hasPermission("dotplugin.adminchat")){
                    pl.sendMessage("(AdminChat) "+PlayerApi.getRankOfPlayer(player).getPrefix()+player.getName()+PlayerApi.getRankOfPlayer(player).getSuffix()+": ");
                }
            }
        }else{
            if (DotPlugin.mutePlayers.contains(player)){
                event.setCancelled(true);
                player.sendMessage("You can't send message because you are muted !");
            }else if (player.hasPermission("dotplugin.chatColor")){
                event.setMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            String prefix = PlayerApi.getRankOfPlayer(player).getPrefix();
            String suffix = PlayerApi.getRankOfPlayer(player).getSuffix();
            event.setFormat(ChatColor.translateAlternateColorCodes('&',
                    (prefix==null?"":prefix)+
                            player.getName()+
                            (suffix==null?"":suffix)+": "
            )+event.getMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Rank rank = PlayerApi.getRankOfPlayer(player);
        List<String> perms = rank.getPermissions();

        for (String perm : perms){
            player.addAttachment(dotPlugin,perm,true);
        }

        for (Player pl : DotPlugin.vanishedPlayers){
            event.getPlayer().hidePlayer(pl);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        DotPlugin.vanishedPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (DotPlugin.freezePlayers.contains(player)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBlockInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bDisplay wand");
        meta.setLore(Arrays.asList("§8This is a wand to make or manage display entities.",""));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 1, false);
        item.setItemMeta(meta);
        if (player.getInventory().getItemInMainHand() == item){
            DisplayGui.openInventory(player);
        }
    }

    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(PlayerApi.isInModerationMode(player) && entity instanceof Player){
            ItemStack item = player.getInventory().getItemInMainHand();
            assert item.getType() != Material.AIR;
            if (item.getItemMeta().getDisplayName().equals("§cFreeze")){
                if (PlayerApi.isFreeze((Player)entity)){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/freeze "+entity.getName());
                    player.sendMessage("You have freeze "+entity.getName());
                }else{
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/unfreeze "+entity.getName());
                    player.sendMessage("You have unfreeze "+entity.getName());
                }
            }else if (item.getItemMeta().getDisplayName().equals("§cMute")){
                if (PlayerApi.isMute((Player)entity)){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/mute "+entity.getName());
                    player.sendMessage("You have mute "+entity.getName());
                }else{
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/unmute "+entity.getName());
                    player.sendMessage("You have unmute "+entity.getName());
                }
            }else if (item.getItemMeta().getDisplayName().equals("§cKnockback")){
                entity.setVelocity(new Vector(1,1,0));
            }
        }
    }

}
