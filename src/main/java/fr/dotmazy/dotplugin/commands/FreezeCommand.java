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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreezeCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public FreezeCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String,Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.freeze.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (!(sender.hasPermission("dotplugin.freeze"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if(args.length < 1){
            sender.sendMessage(TextApi.getTranslateConfig("commands.freeze.noArgsMessage",options));
            return true;
        }

        if(Bukkit.getPlayer(args[0])==null){
            sender.sendMessage(TextApi.getTranslateConfig("commands.freeze.invalidPlayerMessage",options));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        assert player != null;
        if(PlayerApi.freezePlayer(player))
            sender.sendMessage(TextApi.getTranslateConfig("commands.freeze.successfullyFreezeMessage",options));
        else
            sender.sendMessage(TextApi.getTranslateConfig("commands.freeze.errorWhenFreezeMessage",options));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<String>();
        if(args.length == 1){
            for (Player player : Bukkit.getServer().getOnlinePlayers()){
                if(player.getName().startsWith(args[0])) list.add(player.getName());
            }
        }
        return list;
    }
}
