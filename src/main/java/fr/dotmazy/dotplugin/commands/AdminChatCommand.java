package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.TextApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;

import java.util.Map;

public class AdminChatCommand implements CommandExecutor {

    private final DotPlugin dotPlugin;

    public AdminChatCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String,Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.adminchat.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if(!(sender instanceof Player player)){
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        if(!player.hasPermission("dotplugin.commands.adminchat")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.adminchat",options));
            return true;
        }
        if (DotPlugin.adminChatPlayers.contains((Player)sender)) {
            DotPlugin.adminChatPlayers.add(player);
            sender.sendMessage(TextApi.getTranslateConfig("commands.adminchat.enableMessage",options));
        } else {
            DotPlugin.adminChatPlayers.remove(player);
            sender.sendMessage(TextApi.getTranslateConfig("commands.adminchat.disableMessage",options));
        }
        return true;
    }

}
