package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import fr.dotmazy.dotplugin.util.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class GmaCommand extends CommandUtil implements CommandExecutor {

    private final DotPlugin dotPlugin;

    public GmaCommand(DotPlugin dotPlugin) {
        super(dotPlugin);
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String,Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.gamemode.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (!(sender.hasPermission("dotplugin.gamemode.*") || sender.hasPermission("dotplugin.gamemode.adventure"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        Player player = Bukkit.getPlayer(!(args.length >= 1)?"":args[0]);
        if ((!(sender instanceof Player) && player == null) || ((!(args.length >= 1)?"":args[0]) != null && player == null)){
            sender.sendMessage(TextApi.getTranslateConfig("commands.gamemode.invalidPlayer",options));
            return true;
        }
        if (player == null) player = (Player) sender;
        player.setGameMode(GameMode.ADVENTURE);
        return true;
    }

}
