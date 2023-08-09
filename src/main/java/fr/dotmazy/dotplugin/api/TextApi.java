package fr.dotmazy.dotplugin.api;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextApi {

    private static DotPlugin dotPlugin;

    public static void init(DotPlugin dotPlugin) {
        TextApi.dotPlugin = dotPlugin;
    }

    public static String translateString(String text, Map<String, Object> options) {
        Player player = options.get("player") instanceof Player?(Player)options.get("player"):null;
        World world = options.get("world") instanceof World?(World)options.get("world"):null;
        String testArg = options.get("test_arg") instanceof String?(String)options.get("test_arg"):null;
        String waitTime = options.get("wait_time") instanceof Integer?options.get("wait_time").toString():null;
        String homeName = options.get("home_name") instanceof Integer?options.get("home_name").toString():null;
        String warpName = options.get("warp_name") instanceof Integer?options.get("warp_name").toString():null;

        return ChatColor.translateAlternateColorCodes('&',text
                .replace("%player_name%",player!=null?player.getName():"%player_name%")
                .replace("%player_location%",player!=null?player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ():"%player_location%")
                .replace("%player_block_location%",player!=null?player.getLocation().getBlockX()+" "+player.getLocation().getBlockY()+" "+player.getLocation().getBlockZ():"%player_block_location%")
                .replace("%player_display_name%",player!=null?player.getDisplayName():"%player_display_name%")
                .replace("%player_display_name%",player!=null?player.getDisplayName():"%player_display_name%")
                .replace("%player_walk_speed%",player!=null?player.getWalkSpeed()+"":"%player_walk_speed%")
                .replace("%player_local%",player!=null?player.getLocale()+"":"%player_local%")
                .replace("%player_ping%",player!=null?player.getPing()+"":"%player_ping%")
                .replace("%player_fly_speed%",player!=null?player.getFlySpeed()+"":"%player_fly_speed%")
                .replace("%player_health%",player!=null?player.getHealth()+"":"%player_health%")
                .replace("%player_world%",player!=null?player.getWorld()+"":"%player_world%")
                .replace("%player_bed_location%",player!=null?player.getBedLocation().toString():"%player_bed_location%")
                .replace("%player_entity_id%",player!=null?player.getEntityId()+"":"%player_entity_id%")
                .replace("%player_game_mode%",player!=null?player.getGameMode().toString():"%player_game_mode%")
                .replace("%player_item_in_main_hand%",player!=null?player.getInventory().getItemInMainHand().toString():"%player_item_in_main_hand%")
                .replace("%player_biome%",player!=null?player.getLocation().getBlock().getBiome()+"":"%player_biome%")
                .replace("%player_health%",player!=null?player.getHealth()+"":"%player_health%")
                .replace("%player_gamemode%",player!=null?player.getGameMode().toString().toLowerCase()+"":"%player_gamemode%")
                .replace("%world_name%",world!=null?world.getName():"%world_name%")
                .replace("%world_time%",world!=null?world.getTime()+"":"%world_time%")
                .replace("%world_spawn_location%",world!=null?world.getSpawnLocation().toString():"%world_spawn_location%")
                .replace("%world_seed%",world!=null?world.getSeed()+"":"%world_seed%")
                .replace("%world_uid%",world!=null?world.getUID().toString():"%world_uid%")
                .replace("%test_argument%",testArg!=null?testArg:"%test_argument%")
                .replace("%wait_time%",waitTime!=null?waitTime:"%wait_time%")
                .replace("%home_name%",homeName!=null?homeName:"%home_name%")
                .replace("%warp_name%",warpName!=null?warpName:"%warp_name%")
                .replace("%n%","\n"));
    }

    public static String getTranslateConfig(String path, Map<String,Object> options){
        try {
            return translateString(dotPlugin.getConfig().getString(path), options);
        }catch (Exception e){
            return "";
        }
    }

    public static List<String> getTranslateListConfig(String path, Map<String,Object> options){
        try {
            List<String> list = new ArrayList<>();
            int i=0;
            for (String text : dotPlugin.getConfig().getStringList(path)) {
                list.add(translateString(dotPlugin.getConfig().getStringList(path).get(i), options));
                i++;
            }
            return list;
        }catch (Exception e){
            return null;
        }
    }

}
