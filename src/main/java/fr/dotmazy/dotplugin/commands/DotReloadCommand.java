package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DotReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DotPlugin.reload();
        return false;
    }

}
