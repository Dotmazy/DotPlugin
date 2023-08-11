package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import fr.dotmazy.dotplugin.util.CommandUtil;
import java.lang.Object;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class NickCommand extends CommandUtil {
    public NickCommand(DotPlugin dotPlugin) {super(dotPlugin);}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.nick.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (sender instanceof Player && !(PlayerApi.hasPerms((Player) sender,"dotplugin.nick"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }
        if (args.length < 1) {

            return true;
        }
        if(sender instanceof Player){}
        return true;
    }

}
