package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.old.api.TextApi;
import java.lang.Object;

import fr.dotmazy.dotplugin.util.Api;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public HomeCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = new HashMap<>(Map.of(
                "player", sender instanceof Player ? (Player) sender : null,
                "world", sender instanceof Player ? ((Player) sender).getWorld() : null,
                "wait_time", dotPlugin.getConfig().getInt("commands.home.waitTime")
        ));
        if (!dotPlugin.getConfig().getBoolean("commands.home.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (sender instanceof Player player && !(Api.Player.hasOneOfPerms(player,"dotplugin.*","dotplugin.spawn.tp","dotplugin.spawn.*"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }

        String home;
        if (args.length < 1)
            home = "home";
        else home = args[0];

        Location warp = PlayerApi.getHome(player, home);

        if (warp==null){
            sender.sendMessage(TextApi.getTranslateConfig("commands.home.unknownHome",options));
            return true;
        }
        options.put("home_name",home);
        if (!sender.hasPermission("dotplugin.home.noWait")){
            Location loc = player.getLocation();
            sender.sendMessage(TextApi.getTranslateConfig("commands.home.waitMessage",options));
            Bukkit.getScheduler().runTaskLater(dotPlugin,() -> {
                Location nloc = player.getLocation();
                if (loc.getX()==nloc.getX() && loc.getY()==nloc.getY() && loc.getZ()==nloc.getZ()) {
                    player.teleport(warp);
                    player.sendMessage(TextApi.getTranslateConfig("commands.home.tpSuccessfully",options));
                } else {
                    player.sendMessage(TextApi.getTranslateConfig("commands.home.playerMoveMessage",options));
                }
            },dotPlugin.getConfig().getInt("commands.home.waitTime")*20L);
        }else {
            player.teleport(warp);
            player.sendMessage(TextApi.getTranslateConfig("commands.home.tpSuccessfully",options));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>();
    }

}
