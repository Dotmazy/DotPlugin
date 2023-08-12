package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import java.lang.Object;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ModCommand implements CommandExecutor {

    private final DotPlugin dotPlugin;

    public ModCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        //if (!dotPlugin.getConfig().getBoolean("commands.mod.enable")){
            //sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            //return true;
        //}
        if (sender instanceof Player && !(sender.hasPermission("dotplugin.commands.mod"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        Player player = (Player) sender;
        if (DotPlugin.modPlayers.get(player) == null){
            DotPlugin.modPlayers.put(player,player.getInventory().getContents());
            Inventory inv = player.getInventory();
            inv.clear();
            inv.setItem(0,makeItem(Material.ICE, "u00A7cFreeze", "Freeze player"));
            inv.setItem(1,makeItem(Material.BARRIER, "u00A7cMute", "Mute player"));
            inv.setItem(2,makeItem(Material.GOLDEN_AXE, "u00A7cKnockback", "Knockback player"));
            PlayerApi.vanishPlayer(player);
            player.sendMessage("You are now in moderating mode.");
        } else {
            player.getInventory().setContents(DotPlugin.modPlayers.get(player));
            DotPlugin.modPlayers.remove(player);
            PlayerApi.unvanishPlayer(player);
            player.sendMessage("You are now in normal mode.");
        }
        return true;
    }
    
    private static ItemStack makeItem(Material material, String displayName, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        //if(lore!=null) meta.setLore(List.from(lore));
        item.setItemMeta(meta);
        return item;
        
    }

}
