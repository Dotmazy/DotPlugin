package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.old.api.CommandApi;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.old.api.TextApi;
import fr.dotmazy.dotplugin.util.Api;
import fr.dotmazy.dotplugin.util.CommandUtil;
import java.lang.Object;
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

public class WarpCommand extends CommandUtil implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public WarpCommand(DotPlugin dotPlugin) {
        super(dotPlugin);
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = new HashMap<>(Map.of(
                "player", sender instanceof Player ? (Player) sender : null,
                "world", sender instanceof Player ? ((Player) sender).getWorld() : null,
                "wait_time", dotPlugin.getConfig().getInt("commands.warp.waitTime")
        ));
        if (!dotPlugin.getConfig().getBoolean("commands.warp.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (sender instanceof Player player && !(Api.Player.hasPerms(player,"dotplugin.*","dotplugin.spawn.tp","dotplugin.spawn.*"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.warp.noNameMessage",options));
            return true;
        }

        Location warp = CommandApi.getWarp(args[0]);

        if (warp==null){
            sender.sendMessage(TextApi.getTranslateConfig("commands.warp.unknownWarp",options));
            return true;
        }
        options.put("warp_name",args[0]);
        if (!Api.Player.hasPerms(player,"dotplugin.warp.noWait")){
            Location loc = player.getLocation();
            sender.sendMessage(TextApi.getTranslateConfig("commands.warp.waitMessage",options));
            Bukkit.getScheduler().runTaskLater(dotPlugin,() -> {
                Location nloc = player.getLocation();
                if (loc.getX()==nloc.getX() && loc.getY()==nloc.getY() && loc.getZ()==nloc.getZ()) {
                    player.teleport(warp);
                    player.sendMessage(TextApi.getTranslateConfig("commands.warp.tpSuccessfully",options));
                } else {
                    player.sendMessage(TextApi.getTranslateConfig("commands.warp.playerMoveMessage",options));
                }
            },dotPlugin.getConfig().getInt("warpCommandWaitTime")*20L);
        }else {
            player.teleport(warp);
            player.sendMessage(TextApi.getTranslateConfig("commands.warp.tpSuccessfully",options));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>();
    }

}
