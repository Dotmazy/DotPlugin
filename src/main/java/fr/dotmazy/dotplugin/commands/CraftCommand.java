package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.TextApi;
import java.lang.Object;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CraftCommand implements CommandExecutor {

    private final DotPlugin dotPlugin;

    public CraftCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = new HashMap<>(Map.of(
                "player", sender instanceof Player ? (Player) sender : null,
                "world", sender instanceof Player ? ((Player) sender).getWorld() : null
        ));
        if (!dotPlugin.getConfig().getBoolean("commands.craft.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if(!(sender instanceof Player player)){
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        if(!player.hasPermission("dotplugin.commands.craft")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }
        ((Player)sender).openWorkbench(new Location(Bukkit.getWorlds().get(0),0,0,0), true);
        return true;
    }

}
