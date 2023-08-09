package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MuteCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public MuteCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @Nonnull String[] args) {
        Map<String,Object> options = new java.util.HashMap<>(Map.of(
                "player", sender instanceof Player ? (Player) sender : null,
                "world", sender instanceof Player ? ((Player) sender).getWorld() : null
        ));
        if (!dotPlugin.getConfig().getBoolean("commands.mute.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (sender instanceof Player && !(PlayerApi.hasPerms((Player) sender,"dotplugin.mute"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if(args.length < 1){
            sender.sendMessage(TextApi.getTranslateConfig("commands.mute.noMuteArg",options));
            return true;
        }

        if(Bukkit.getPlayer(args[0])==null){
            sender.sendMessage(TextApi.getTranslateConfig("commands.mute.invalidPlayer",options));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        options.put("mute_player",player);

        assert player != null;
        if(PlayerApi.mutePlayer(player))
            sender.sendMessage(TextApi.getTranslateConfig("commands.mute.successfullyMute",options));
        else
            sender.sendMessage(TextApi.getTranslateConfig("commands.mute.alreadyMute",options));

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        List<String> list = new ArrayList<String>();
        if(args.length == 1){
            for (Player player : Bukkit.getServer().getOnlinePlayers()){
                if(player.getName().startsWith(args[0])) list.add(player.getName());
            }
        }
        return list;
    }
}
