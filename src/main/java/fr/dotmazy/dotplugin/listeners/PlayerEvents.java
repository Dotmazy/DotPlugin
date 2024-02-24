package fr.dotmazy.dotplugin.listeners;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.gui.CraftingTableGui;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.util.*;
import fr.dotmazy.dotplugin.util.raw.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class PlayerEvents implements Listener {

    private final DotPlugin dotPlugin;

    public PlayerEvents(DotPlugin dotPlugin){
        this.dotPlugin = dotPlugin;
    }

    public static String calculateSHA256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    @EventHandler
    public void onResourcePackStatusChange(PlayerResourcePackStatusEvent event){
        Player player = event.getPlayer();
        PlayerResourcePackStatusEvent.Status status = event.getStatus();
        switch (status){
            case DECLINED:
                player.kickPlayer("Resource pack is obligatory, pls enable it.");
                break;
            case FAILED_DOWNLOAD:
                player.kickPlayer("An error occurred when downloading resource pack.\nPls contact our staff.");
                break;
        }
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String prefix = Api.Player.getPrefix(player);
        String suffix = Api.Player.getSuffix(player);
        if(DotPlugin.chatInputPlayers.containsKey(player)) {
            event.setCancelled(true);
            ChatInputFunction function = DotPlugin.chatInputPlayers.get(player);
            DotPlugin.chatInputPlayers.remove(player.getPlayer());
            function.apply(message);
        }else if(DotPlugin.adminChatPlayers.contains(player)){
            event.setCancelled(true);
            for (Player pl : Bukkit.getOnlinePlayers()){
                if (pl.hasPermission("dotplugin.adminchat")){
                    pl.sendMessage("(AdminChat) "+(prefix==null?"":prefix)+player.getName()+(suffix==null?"":suffix)+": "+event.getMessage());
                }
            }
        }else{
            if (DotPlugin.mutePlayers.contains(player)){
                event.setCancelled(true);
                player.sendMessage("You can't send message because you are muted !");
            }else if (player.hasPermission("dotplugin.chatColor")){
                event.setMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
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

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        player.setResourcePack(ResourcePackUtil.resourcePackURL);

        /*if(Database.executeStringQuery("SELECT playerUuid FROM link WHERE playerUuid='"+player.getName()+"'")!=null){
            Database.executeUpdate("UPDATE link SET playerUuid='"+player.getUniqueId()+"' WHERE playerUuid='"+player.getName()+"'");
            notLogin(player,0);
        }else{
            if(Database.executeStringQuery("SELECT memberId FROM link WHERE playerUuid='"+player.getUniqueId()+"'")==null)
                notLink(player,0);
            else notLogin(player,0);
        }*/

        Api.Player.setOnline(player, true);
        if(Api.Player.exist(player)){
            player.sendMessage("Welcome to the server "+player.getServer().getName());
        }
        Api.Player.create(player);
        Api.Player.updatePerms(player);
        if(Api.Player.isFreeze(player)) DotPlugin.freezePlayers.add(player);
        if(Api.Player.isMute(player)) DotPlugin.vanishedPlayers.add(player);

        for (Player pl : DotPlugin.vanishedPlayers)
            event.getPlayer().hidePlayer(pl);
    }

    private void notLink(Player player, int i) {
        player.sendMessage("Please link your account on our discord: https://discord.gg/SGMU8WSDQz");
        Bukkit.getScheduler().runTaskLater(DotPlugin.getInstance(),()->{
            if(i<10) {
                if(Database.executeStringQuery("SELECT playerUuid FROM link WHERE playerUuid='"+player.getName()+"'")==null)
                    notLink(player, i+1);
                else {
                    player.sendMessage(ChatColor.GREEN + "You are successfully link");
                    Database.executeUpdate("UPDATE link SET playerUuid='"+player.getUniqueId()+"' WHERE playerUuid='"+player.getName()+"'");
                    notLogin(player,0);
                }
            }else player.kickPlayer(ChatColor.RED+"You are standing too long without link");
        },100);
    }

    private void notLogin(Player player, int i){
        DotPlugin.loginPlayers.add(player);
        player.sendMessage("Please login or register");
        Bukkit.getScheduler().runTaskLater(DotPlugin.getInstance(),()->{
            if(i<10) {
                if (DotPlugin.loginPlayers.contains(player)){ notLogin(player, i + 1);
                }
            }else player.kickPlayer(ChatColor.RED+"You are standing too long without login");
        },100);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        DotPlugin.vanishedPlayers.remove(player);
        Api.Player.setOnline(player, false);
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

        if(action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.CRAFTING_TABLE){
            event.setCancelled(true);
            player.openInventory(new CraftingTableGui().getInventory());
        }

        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("u00A7bDisplay wand");
        meta.setLore(Arrays.asList("u00A78This is a wand to make or manage display entities.",""));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 1, false);
        item.setItemMeta(meta);
        if (player.getInventory().getItemInMainHand() == item){
            //DisplayGui.openInventory(player);
        }
    }

    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        //if(PlayerApi.isInModerationMode(player) && entity instanceof Player){
            ItemStack item = player.getInventory().getItemInMainHand();
            assert item.getType() != Material.AIR;
            if (item.getItemMeta().getDisplayName().equals("u00A7cFreeze")){
                if (Api.Player.isFreeze((Player)entity)){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/freeze "+entity.getName());
                    player.sendMessage("You have freeze "+entity.getName());
                }else{
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/unfreeze "+entity.getName());
                    player.sendMessage("You have unfreeze "+entity.getName());
                }
            }else if (item.getItemMeta().getDisplayName().equals("u00A7cMute")){
                if (Api.Player.isMute((Player)entity)){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/mute "+entity.getName());
                    player.sendMessage("You have mute "+entity.getName());
                }else{
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"/unmute "+entity.getName());
                    player.sendMessage("You have unmute "+entity.getName());
                }
            }else if (item.getItemMeta().getDisplayName().equals("u00A7cKnockback")){
                entity.setVelocity(new Vector(1,1,0));
            }
        //}
    }

}
