package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.CommandApi;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import java.lang.Object;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbyCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public LobbyCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null,
                "wait_time", dotPlugin.getConfig().getInt("commands.lobby.waitTime")
        );
        if (!dotPlugin.getConfig().getBoolean("commands.lobby.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (sender instanceof Player && !(PlayerApi.hasPerms((Player) sender,"dotplugin.*","dotplugin.spawn.tp","dotplugin.spawn.*"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }

        Location lobby = CommandApi.getLobby();

        assert lobby != null;
        if (!player.hasPermission("dotplugin.lobby.noWait")){
            Location loc = player.getLocation();
            sender.sendMessage(TextApi.getTranslateConfig("commands.lobby.waitMessage",options));
            Bukkit.getScheduler().runTaskLater(dotPlugin,() -> {
                Location nloc = player.getLocation();
                if (loc.getX()==nloc.getX() && loc.getY()==nloc.getY() && loc.getZ()==nloc.getZ()) {
                    player.teleport(lobby);
                    player.sendMessage(TextApi.getTranslateConfig("commands.lobby.tpSuccessfully",options));
                } else {
                    player.sendMessage(TextApi.getTranslateConfig("commands.lobby.playerMoveMessage",options));
                }
            },dotPlugin.getConfig().getInt("commands.lobby.waitTime")*20L);
        }else {
            player.teleport(lobby);
            player.sendMessage(TextApi.getTranslateConfig("commands.lobby.tpSuccessfully",options));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>();
    }

}
