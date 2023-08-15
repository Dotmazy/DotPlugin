package fr.dotmazy.dotplugin.old.api;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.Rank;
import java.util.Objects;

import fr.dotmazy.dotplugin.util.raw.RawBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerApi {

    /*public static void setHome(Player player, String name, Location location){
        World world = location.getWorld();
        double xPos = location.getX();
        double yPos = location.getY();
        double zPos = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        String path = player.getUniqueId()+".homes."+name;
        DotPlugin.files.get("players").get().set(path+".x",xPos);
        DotPlugin.files.get("players").get().set(path+".y",yPos);
        DotPlugin.files.get("players").get().set(path+".z",zPos);
        DotPlugin.files.get("players").get().set(path+".yaw",yaw);
        DotPlugin.files.get("players").get().set(path+".pitch",pitch);
        DotPlugin.files.get("players").get().set(path+".world",world);
    }

    public static Location getHome(Player player, String name){
        String path = player.getUniqueId()+".homes."+name;
        World world = Bukkit.getWorld(Objects.requireNonNull(DotPlugin.files.get("players").get().getString(path+".world")));
        if(world!=null){
            double xPos = DotPlugin.files.get("players").get().getDouble(path+".x");
            double yPos = DotPlugin.files.get("players").get().getDouble(path+".y");
            double zPos = DotPlugin.files.get("players").get().getDouble(path+".z");
            float yaw = (float) Objects.requireNonNull(DotPlugin.files.get("players").get().get(path+".yaw"));
            float pitch = (float) Objects.requireNonNull(DotPlugin.files.get("players").get().get(path+".pitch"));
            return new Location(world,xPos,yPos,zPos,yaw,pitch);
        }
        return null;
    }

    public static HashMap<String,Location> getHomes(Player player){
        HashMap<String,Location> map = new HashMap<String,Location>();
        for (String key : DotPlugin.files.get("players").get().getKeys(true)){
            if(key.startsWith(player.getUniqueId()+".homes.")){
                String homeName = key.split(".")[key.split(".").length];
                map.put(homeName,getHome(player,homeName));
            }
        }
        return map;
    }

    public static boolean isInModerationMode(Player player){
        return DotPlugin.modPlayers.get(player) != null;
    }*/

}
