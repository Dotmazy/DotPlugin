package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.PlayerApi;
import fr.dotmazy.dotplugin.api.TextApi;
import fr.dotmazy.dotplugin.gui.MusicGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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
        Map<String,Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.home.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        if (!(PlayerApi.hasPerm((Player) sender,"dotplugin.music"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (args.length > 0){
            try{
                Player player = (Player) sender;
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"stopsound "+sender.getName()+" record");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute at "+sender.getName()+" run playsound minecraft:music.game."+args[0]+" record "+sender.getName()+" ~ ~ ~");
            }catch(Exception e){
                e.printStackTrace();
                sender.sendMessage("Invalid music !");
            }
            return true;
        }
        MusicGui.musicGui();
        MusicGui.musicGui2();
        MusicGui.openInventory((Player)sender);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) return makeTabList(args[0],
                "ne-rage-quitte-pas",
                "jai-la-dalle",
                "ce-soir",
                "court-metrage-musicale",
                "dans-la-nuit",
                "herobrine",
                "jaimerais-trop-quil-creve",
                "jai-sombre",
                "jarrive-pas-a-pecho",
                "jean-kevin",
                "je-construis-ma-maison",
                "je-toublierais-jamais",
                "la-pire-des-teams",
                "le-temp-dun-reve",
                "ma-pelle",
                "mon-diams-ou-tes",
                "noel-pour-toi-cest-mort",
                "pas-lniveau",
                "pgm",
                "plus-tard",
                "yen-a-marre-dhalloween",
                "tu-as-explose",
                "tout-casser",
                "toi-et-moi",
                "sur-ma-map",
                "sur-blood-symphony",
                "save-me",
                "roots-noob",
                "redstone",
                "popopop-bloc-par-bloc",
                "enderman",
                "zombie"
        );
        return null;
    }

    public static List<String> makeTabList(String arg, String... list){
        List<String> result = new ArrayList<>();
        for (String s : list){
            if(s.startsWith(arg)) result.add(s);
        }
        return result;
    }
}
