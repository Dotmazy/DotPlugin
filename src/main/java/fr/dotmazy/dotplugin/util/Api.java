package fr.dotmazy.dotplugin.util;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.raw.RawBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.sql.ResultSet;
import java.util.*;

public class Api {

    public static class Text {

        private static String transformText(String text, Map<String,Object> options){
            Map<String,String> opts = new HashMap<>();
            for(String arg : options.keySet())
                if(options.get(arg) instanceof org.bukkit.entity.Player player){
                    opts.put("%"+arg+"_name%",player.getName());
                    opts.put("%"+arg+"_uuid%",player.getUniqueId().toString());
                    opts.put("%"+arg+"_locale%",player.getLocale());
                    opts.put("%"+arg+"_list_footer%",player.getPlayerListFooter());
                    opts.put("%"+arg+"_list_header%",player.getPlayerListHeader());
                    opts.put("%"+arg+"_list_name%",player.getPlayerListName());
                    opts.put("%"+arg+"_time%",player.getPlayerTime()+"");
                    opts.put("%"+arg+"_time_offset%",player.getPlayerTimeOffset()+"");
                    opts.put("%"+arg+"_time_weather%",player.getPlayerWeather().toString());
                    opts.put("%"+arg+"_custom_name%",player.getCustomName());
                    opts.put("%"+arg+"_display_name%",player.getDisplayName());
                    opts.put("%"+arg+"_allow_flight%",player.getAllowFlight()+"");
                    opts.put("%"+arg+"_block_location%",blockLocation(player.getLocation()));
                    opts.put("%"+arg+"_location%",simpleLocation(player.getLocation()));
                    opts.put("%"+arg+"_advanced_location%",advancedLocation(player.getLocation()));
                    opts.put("%"+arg+"_location_x%",player.getLocation().getX()+"");
                    opts.put("%"+arg+"_location_y%",player.getLocation().getY()+"");
                    opts.put("%"+arg+"_location_z%",player.getLocation().getZ()+"");
                    opts.put("%"+arg+"_location_yaw%",player.getLocation().getYaw()+"");
                    opts.put("%"+arg+"_location_pitch%",player.getLocation().getPitch()+"");
                    opts.put("%"+arg+"_block_location_x%",player.getLocation().getBlockX()+"");
                    opts.put("%"+arg+"_block_location_y%",player.getLocation().getBlockY()+"");
                    opts.put("%"+arg+"_block_location_z%",player.getLocation().getBlockZ()+"");
                    opts.put("%"+arg+"_fly_speed%",player.getFlySpeed()+"");
                    opts.put("%"+arg+"_xp%",player.getExp()+"");
                    opts.put("%"+arg+"_gamemode%",player.getGameMode().toString());
                    opts.put("%"+arg+"_health%",player.getHealth()+"");
                    opts.put("%"+arg+"_level%",player.getLevel()+"");
                    opts.put("%"+arg+"_rank%",Api.Player.getRank(player));
                    opts.put("%"+arg+"_prefix%",Api.Player.getPrefix(player));
                    opts.put("%"+arg+"_suffix%",Api.Player.getSuffix(player));
                    opts.put("%"+arg+"_walk_speed%",player.getWalkSpeed()+"");
                    opts.put("%"+arg+"_compass_target_block_location%",blockLocation(player.getCompassTarget()));
                    opts.put("%"+arg+"_compass_target_block_location_x%",player.getCompassTarget());
                    opts.put("%"+arg+"_compass_target_block_location_y%",player.getCompassTarget());
                    opts.put("%"+arg+"_compass_target_block_location_z%",player.getCompassTarget());
                    opts.put("%"+arg+"_compass_target_location%",simpleLocation(player.getCompassTarget()));
                    opts.put("%"+arg+"_compass_target_location_x%",player.getCompassTarget().getX()+"");
                    opts.put("%"+arg+"_compass_target_location_y%",player.getCompassTarget().getY()+"");
                    opts.put("%"+arg+"_compass_target_location_z%",player.getCompassTarget().getZ()+"");
                    opts.put("%"+arg+"_compass_target_location_yaw%",player.getCompassTarget().getYaw()+"");
                    opts.put("%"+arg+"_compass_target_location_pitch%",player.getCompassTarget().getPitch()+"");
                    opts.put("%"+arg+"_compass_target_advanced_location%",advancedLocation(player.getCompassTarget()));
                }else opts.put("%"+arg+"%",options.get(arg)+"");


            opts.put("%n%","");

            String toReturn = text;
            for(String arg : opts.keySet())
                text = toReturn.replace(arg,opts.get(arg));

            return ChatColor.translateAlternateColorCodes('&',text);
        }

        private static String blockLocation(Location loc){
            return loc.getBlockY()+" "+loc.getBlockY()+" "+loc.getBlockZ();
        }

        private static String simpleLocation(Location loc){
            return loc.getY()+" "+loc.getY()+" "+loc.getZ();
        }

        private static String advancedLocation(Location loc){
            return loc.getY()+" "+loc.getY()+" "+loc.getZ()+" "+loc.getYaw()+" "+loc.getPitch();
        }

    }

    public static class Player {

        public static void sendRawMessage(org.bukkit.entity.Player player, RawBuilder raw){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tellraw "+player.getName()+" "+raw.toString());
        }

        public static boolean exist(org.bukkit.entity.Player player){
            return Database.executeStringQuery("SELECT uuid FROM players WHERE uuid='"+player.getUniqueId()+"'")!=null;
        }

        public static void create(org.bukkit.entity.Player player){
            if(!exist(player))
                Database.executeUpdate("INSERT INTO players(uuid,name) VALUES ('"+player.getUniqueId()+"','"+player.getName()+"')");
        }

        public static void setRank(org.bukkit.entity.Player player, String rank){
            Database.executeUpdate("UPDATE players SET rank='"+rank+"' WHERE uuid='"+player.getUniqueId()+"'");
            updatePerms(player, rank);
        }

        public static void setFreeze(org.bukkit.entity.Player player, boolean freeze){
            Database.executeUpdate("UPDATE players SET freeze='"+(freeze?1:0)+"' WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static void setMute(org.bukkit.entity.Player player, boolean mute){
            Database.executeUpdate("UPDATE players SET mute='"+(mute?1:0)+"' WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static void setVanish(org.bukkit.entity.Player player, boolean vanish){
            Database.executeUpdate("UPDATE players SET vanish='"+(vanish?1:0)+"' WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static void setOnline(org.bukkit.entity.Player player, boolean online){
            Database.executeUpdate("UPDATE players SET online='"+(online?1:0)+"' WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static String getRank(org.bukkit.entity.Player player){
            return Database.executeStringQuery("SELECT rank FROM players WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static boolean isFreeze(org.bukkit.entity.Player player){
            return Database.executeBooleanQuery("SELECT freeze FROM players WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static boolean isMute(org.bukkit.entity.Player player){
            return Database.executeBooleanQuery("SELECT mute FROM players WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static boolean isVanish(org.bukkit.entity.Player player){
            return Database.executeBooleanQuery("SELECT vanish FROM players WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static boolean isOnline(org.bukkit.entity.Player player){
            return Database.executeBooleanQuery("SELECT online FROM players WHERE uuid='"+player.getUniqueId()+"'");
        }

        public static boolean isInModerationMode(org.bukkit.entity.Player player){
            return DotPlugin.modPlayers.containsKey(player);
        }

        public static List<String> getPerms(org.bukkit.entity.Player player){
            return Rank.getPerms(getRank(player));
        }

        public static boolean hasPerm(org.bukkit.entity.Player player, String perm){
            return player.hasPermission(perm);
        }

        public static boolean hasPerms(org.bukkit.entity.Player player, String... perms){
            for(String perm : perms)
                if(!player.hasPermission(perm)) return false;
            return true;
        }

        public static boolean hasOneOfPerms(org.bukkit.entity.Player player, String... perms){
            for(String perm : perms)
                if(player.hasPermission(perm) || player.isOp()) return true;
            return false;
        }

        public static void updatePerms(org.bukkit.entity.Player player, String rank){
            for(PermissionAttachmentInfo attachment : player.getEffectivePermissions())
                try{player.removeAttachment(attachment.getAttachment());}catch(Exception ignored){}
            for(String perm : Rank.getPerms(rank))
                player.addAttachment(DotPlugin.getInstance(), perm, true);
        }

        public static void updatePerms(org.bukkit.entity.Player player){
            updatePerms(player,getRank(player));
        }

        public static String getPrefix(org.bukkit.entity.Player player){
            return Rank.getPrefix(getRank(player));
        }

        public static String getSuffix(org.bukkit.entity.Player player){
            return Rank.getSuffix(getRank(player));
        }

        public static void warn(org.bukkit.entity.Player player, String info){
            Database.executeUpdate("INSERT INTO warns(playerUuid,info) VALUES ('"+player.getUniqueId()+"','"+info+"')");
        }

        public static List<String> getWarns(org.bukkit.entity.Player player){
            List<String> warns = new ArrayList<>();
            try {
                ResultSet result = Database.executeQuery("SELECT info FROM warns WHERE playerUuid='"+player.getUniqueId()+"'");
                while(result.next()){
                    warns.add(result.getString(1));
                }
            }catch(Exception ignore){}
            return warns;
        }

        public static void setHome(org.bukkit.entity.Player player, Location loc, String name){
            Database.executeUpdate("INSERT INTO homes(playerUuid, name, x, y, z, yaw, pitch) VALUES ('"+player.getUniqueId()+"','"+name+"','"+loc.getX()+"','"+loc.getY()+"','"+loc.getZ()+"','"+loc.getYaw()+"','"+loc.getPitch()+"')");
        }

        public static void setHome(org.bukkit.entity.Player player, String name){
            setHome(player, player.getLocation(), name);
        }

    }

    public static class Rank {

        public static void create(String name, String prefix, String suffix){
            Database.executeUpdate("INSERT INTO ranks(name,prefix,suffix) VALUES ('"+name+"','"+prefix+"','"+suffix+"')");
        }

        public static void create(String name){
            create(name, "", "");
        }

        public static void setPrefix(String rank, String prefix){
            Database.executeUpdate("UPDATE ranks SET prefix='"+prefix+"' WHERE name='"+rank+"'");
        }

        public static void setSuffix(String rank, String suffix){
            Database.executeUpdate("UPDATE ranks SET suffix='"+suffix+"' WHERE name='"+rank+"'");
        }

        public static String getPrefix(String rank){
            return Database.executeStringQuery("SELECT prefix FROM ranks WHERE name='"+rank+"'");
        }

        public static String getSuffix(String rank){
            return Database.executeStringQuery("SELECT suffix FROM ranks WHERE name='"+rank+"'");
        }

        private static int getId(String rank){
            return Database.executeIntegerQuery("SELECT id FROM ranks WHERE name="+rank);
        }

        public static void addPerms(String rank, String... perms){
            for(String perm : perms){
                if(!hasPerm(rank,perm))
                    Database.executeUpdate("INSERT INTO perms(rankId, name) VALUES ('"+getId(rank)+"','"+perm+"')");
            }
        }

        public static boolean hasPerm(String rank, String perm){
            return Database.executeStringQuery("SELECT name FROM perms WHERE rankId='"+getId(rank)+"' AND name='"+perm+"'")!=null;
        }

        public static List<String> getPerms(String rank){
            try {
                ResultSet result = Database.executeQuery("SELECT name FROM perms WHERE rankId='"+rank+"'");
                List<String> perms = new ArrayList<>();
                while(result.next()){
                    perms.add(result.getString(1));
                }
            }catch(Exception ignore){}
            return null;
        }

    }

}
