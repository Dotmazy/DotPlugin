package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.old.api.TextApi;
import java.lang.Object;

import fr.dotmazy.dotplugin.util.Api;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnmuteCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public UnmuteCommand(DotPlugin dotPlugin) {
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
        if (sender instanceof Player player && !(Api.Player.hasPerms(player,"dotplugin.*","dotplugin.unmute"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if(args.length < 1){
            sender.sendMessage("u00A7cPlease use this command: /unmute <player>");
            return true;
        }

        if(Bukkit.getPlayer(args[0])==null){
            sender.sendMessage("u00A7cInvalid player !");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        assert player != null;
        if(!Api.Player.isMute(player)) {
            Api.Player.setMute(player,true);
            sender.sendMessage("You have successfully unmute " + player.getName());
        }else {
            sender.sendMessage(player.getName()+" is not mute");
        }

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
