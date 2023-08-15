package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.util.Api;
import fr.dotmazy.dotplugin.util.music.Music;
import fr.dotmazy.dotplugin.old.api.TextApi;

import java.lang.Object;

import fr.dotmazy.dotplugin.gui.MusicGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MusicCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public MusicCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.music.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        if (!(Api.Player.hasPerms(player,"dotplugin.music"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (args.length > 0){
            if(args[0].equals("auto")) Music.toggleAutoMusic(player);
            else if(args[0].equals("stop")) Music.stopAll(player);
            else if(Music.get(args[0]) != null) Music.startMusic(player, args[0]);
            else player.sendMessage("\u00A7cUnknown music: "+args[0]);
            return true;
        }
        Inventory inv = new MusicGui().getMainInventory();
        if(inv!=null) player.openInventory(inv);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length == 1){
            for(String m : Music.getStringMusics())
                if(m.startsWith(args[0])) result.add(m);
            if("auto".startsWith(args[0])) result.add("auto");
            if("stop".startsWith(args[0])) result.add("stop");
        }
        return result;
    }
}
