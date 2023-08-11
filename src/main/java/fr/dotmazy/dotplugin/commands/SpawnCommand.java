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

public class SpawnCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public SpawnCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (sender instanceof Player && !(PlayerApi.hasPerms((Player) sender,"dotplugin.*","dotplugin.spawn.tp","dotplugin.spawn.*"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }

        Location spawn = CommandApi.getSpawn(player.getWorld());

        if (!PlayerApi.hasPerms(player,"dotplugin.spawn.noWait")){
            Location loc = player.getLocation();
            sender.sendMessage("Please wait "+dotPlugin.getConfig().getInt("spawnCommandWaitTime")+" sec without move.");
            Bukkit.getScheduler().runTaskLater(dotPlugin,() -> {
                Location nloc = player.getLocation();
                if (loc.getX()==nloc.getX() && loc.getY()==nloc.getY() && loc.getZ()==nloc.getZ()) {
                    player.teleport(spawn);
                    player.sendMessage("§You have been teleported to the spawn.");
                } else {
                    player.sendMessage("§cYou have move, teleport have been canceled.");
                }
            },dotPlugin.getConfig().getInt("spawnCommandWaitTime")*20L);
        }else {
            player.teleport(spawn);
            player.sendMessage("You have been teleported to the spawn.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>();
    }

}
