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
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GamemodeCommand extends CommandUtil implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public GamemodeCommand(DotPlugin dotPlugin) {
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
        if (!(sender.hasPermission("dotplugin.gamemode.*"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(args.length >= 1) || !(args[0].equals("survival") || args[0].equals("creative") || args[0].equals("adventure") || args[0].equals("spectator") || args[0].equals("s") || args[0].equals("c") || args[0].equals("a") || args[0].equals("sp") || args[0].equals("0") || args[0].equals("1") || args[0].equals("2") || args[0].equals("3"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.gamemode.invalidGamemode",options)+"\n"+
                    getOption(args[0], "survival", "creative", "adventure", "spectator", "sp"));
            return true;
        }
        Player player = Bukkit.getPlayer(!(args.length >= 2)?"":args[1]);
        if ((!(sender instanceof Player) && player == null) || ((!(args.length >= 2)?"":args[1]) != null && player == null)){
            sender.sendMessage(TextApi.getTranslateConfig("commands.gamemode.invalidPlayer",options));
            return true;
        }
        if (player == null) player = (Player) sender;
        player.setGameMode(
                args[0].equals("survival") || args[0].equals("creative") || args[0].equals("adventure") || args[0].equals("spectator")?
                GameMode.valueOf(args[0]):
                args[0].equals("s")?GameMode.SURVIVAL:
                args[0].equals("c")?GameMode.CREATIVE:
                args[0].equals("a")?GameMode.ADVENTURE:
                args[0].equals("sp")?GameMode.SPECTATOR:
                GameMode.getByValue(Integer.parseInt(args[0]))
        );
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            if("survival".startsWith(args[0])) list.add("");
            if("creative".startsWith(args[0])) list.add("");
            if("adventure".startsWith(args[0])) list.add("");
            if("spectator".startsWith(args[0])) list.add("");
            if("s".startsWith(args[0])) list.add("");
            if("c".startsWith(args[0])) list.add("");
            if("a".startsWith(args[0])) list.add("");
            if("sp".startsWith(args[0])) list.add("");
            if("0".startsWith(args[0])) list.add("");
            if("1".startsWith(args[0])) list.add("");
            if("2".startsWith(args[0])) list.add("");
            if("3".startsWith(args[0])) list.add("");
        }
        return list;
    }
}
