package fr.dotmazy.dotplugin.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    boolean isPlayerOnly(){
        return false;
    }

    protected void onExecute(CommandSender sender, Command command, String label, String[] args){};
    protected void onExecute(Player player, Command command, String label, String[] args){};

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        onExecute(sender, command, label, args);
        if(sender instanceof Player player) onExecute(player, command, label, args);
        else if(isPlayerOnly()) sender.sendMessage();
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completer = new ArrayList<>();
        return completer;
    }
}
