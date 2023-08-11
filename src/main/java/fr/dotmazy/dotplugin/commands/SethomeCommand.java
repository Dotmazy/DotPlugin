package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import java.lang.Object;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SethomeCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public SethomeCommand(DotPlugin dotPlugin) {
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
        if (sender instanceof Player && !(PlayerApi.hasPerms((Player) sender,"dotplugin.spawn.set","dotplugin.spawn.*"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Please specify a name");
            return true;
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();

        PlayerApi.setHome(player,args[0],loc);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>();
    }
}
