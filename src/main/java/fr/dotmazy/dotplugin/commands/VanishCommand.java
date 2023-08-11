package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import java.lang.Object;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class VanishCommand implements CommandExecutor {

    private DotPlugin dotPlugin;

    public VanishCommand(DotPlugin dotPlugin) {
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
        if (!(sender instanceof Player player)){
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        if (!(PlayerApi.hasPerms((Player) sender,"dotplugin.*","dotplugin.vanish"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (PlayerApi.vanishPlayer(player)) {
            sender.sendMessage((args.length>=1?args[0]:"") == null?"You":player.getName()+" are now vanished.");
        }else{
            PlayerApi.unvanishPlayer(player);
            sender.sendMessage((args.length>=1?args[0]:"") == null?"You":player.getName()+" are now unvanished.");
        }
        return true;
    }

}
