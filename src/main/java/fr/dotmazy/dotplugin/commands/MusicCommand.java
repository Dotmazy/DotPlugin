package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.util.Api;
import fr.dotmazy.dotplugin.util.ResourcePackUtil;
import fr.dotmazy.dotplugin.util.music.Music;
import fr.dotmazy.dotplugin.old.api.TextApi;

import java.lang.Object;

import fr.dotmazy.dotplugin.gui.MusicGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MusicCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public MusicCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
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

            if (args.length > 0) {
                if(args[0].equals("reloadpack")) {
                    player.sendMessage("\u00A7fReloading resource pack...");
                    ResourcePackUtil.zipResourcePack();
                    ResourcePackUtil.sendResourcePackToOnline();
                    player.sendMessage("\u00A7fReloaded resource pack");
                }else if(args[0].equals("remove")) {
                    if(ResourcePackUtil.removeMusic(args[1])){
                        ResourcePackUtil.zipResourcePack();
                        ResourcePackUtil.sendResourcePackToOnline();
                        player.sendMessage("\u00A7fRemoved music "+args[1]);
                    }else player.sendMessage("\u00A7cUnknown music "+args[1]);
                }else if(args[0].equals("upload")){
                    System.out.println("--qsfdf-0000");
                    String url = args[1];
                    double time = Double.valueOf(args[2]);
                    String icon = args[3];
                    //String youtubeVideo = args[0];
                    String name = args[4];
                    try{
                        Future<Boolean> future = ResourcePackUtil.addMusic(url,name,icon,time,null);
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    if(future.isDone()){
                                        timer.cancel();
                                        if(future.get()){
                                            ResourcePackUtil.zipResourcePack();
                                            player.sendMessage("\u00A7fAdded music "+name+" with url '"+url+"'");
                                            ResourcePackUtil.sendResourcePackToOnline();
                                        }
                                    }
                                }catch(Exception e){}
                            }
                        }, 0, 100);
                        System.out.println("--qsfdf-4444");
                    }catch(Throwable e){
                        player.sendMessage("\u00A7cUnknown url: "+url);
                    }
                }else if(args[0].equals("playlist")) {
                    new fr.dotmazy.dotplugin.util.Player(player).startPlaylist(args[1]);
                }else if(args[0].equals("stop")) fr.dotmazy.dotplugin.util.Player.valueOf(player).stopAllMusic();
                else if(Music.get(args[0]) != null) fr.dotmazy.dotplugin.util.Player.valueOf(player).startMusic(args[0]);
                else player.sendMessage("\u00A7cUnknown music: "+args[0]);
                return true;
            }
            Inventory inv = new MusicGui(player).getMainInventory();
            if(inv!=null) player.openInventory(inv);
        }catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length == 1){
            for(String m : Music.getStringMusics())
                if(m.startsWith(args[0])) result.add(m);
            if("playlist".startsWith(args[0])) result.add("playlist");
            if("stop".startsWith(args[0])) result.add("stop");
            if("upload".startsWith(args[0]) && sender.isOp()) result.add("upload");
            if("remove".startsWith(args[0]) && sender.isOp()) result.add("remove");
        }else if(args.length == 2){
            if(Objects.equals(args[0], "playlist")){

            }
        }
        return result;
    }
}
