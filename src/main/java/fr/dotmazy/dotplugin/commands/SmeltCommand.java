package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.old.api.TextApi;
import java.lang.Object;

import fr.dotmazy.dotplugin.util.Api;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class SmeltCommand implements CommandExecutor {

    private final DotPlugin dotPlugin;

    public SmeltCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.home.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if(!(sender instanceof Player player)){
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        if (!(Api.Player.hasPerms(player,"dotplugin.smelt"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.AIR) sender.sendMessage("u00A7cThere are no item in your main hand !");

        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(item, item.getType());
        Bukkit.getServer().addRecipe(furnaceRecipe);

        ItemStack smeltedItem = furnaceRecipe.getResult().clone();
        player.getInventory().setItemInMainHand(smeltedItem);

        sender.sendMessage("Successfully smelt the item in main hand.");

        return true;
    }

}
