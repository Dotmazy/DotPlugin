package fr.dotmazy.dotplugin.old.api;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.math.BigDecimal;

import java.util.Objects;

public class CommandApi {

    public static boolean setSpawn(Location location){
        try {
            String worldName = Objects.requireNonNull(location.getWorld()).getName();
            double xPos = location.getX();
            double yPos = location.getY();
            double zPos = location.getZ();
            float yaw = location.getYaw();
            float pitch = location.getPitch();
            String path = "worlds."+location.getWorld().getUID()+".spawn";
            DotPlugin.files.get("world").get().set(path+".x",xPos);
            DotPlugin.files.get("world").get().set(path+".y",yPos);
            DotPlugin.files.get("world").get().set(path+".z",zPos);
            DotPlugin.files.get("world").get().set(path+".yaw",yaw);
            DotPlugin.files.get("world").get().set(path+".pitch",pitch);
            return true;
        }catch (Exception e){ return false; }
    }

    public static Location getSpawn(World world){
        String path = "worlds."+world.getUID()+".spawn";
        double xPos = DotPlugin.files.get("world").get().getDouble(path+".x");
        double yPos = DotPlugin.files.get("world").get().getDouble(path+".y");
        double zPos = DotPlugin.files.get("world").get().getDouble(path+".z");
        float yaw = BigDecimal.valueOf(DotPlugin.files.get("world").get().getDouble(path + ".yaw")).floatValue();
        float pitch = BigDecimal.valueOf(DotPlugin.files.get("world").get().getDouble(path + ".pitch")).floatValue();
        return new Location(world,xPos,yPos,zPos,yaw,pitch);
    }

    public static boolean setLobby(Location location){
        String worldName = Objects.requireNonNull(location.getWorld()).getName();
        int xPos = location.getBlockX();
        int yPos = location.getBlockY();
        int zPos = location.getBlockZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        World world = location.getWorld();
        DotPlugin.files.get("world").get().set("lobby.x",xPos);
        DotPlugin.files.get("world").get().set("lobby.y",yPos);
        DotPlugin.files.get("world").get().set("lobby.z",zPos);
        DotPlugin.files.get("world").get().set("lobby.yaw",yaw);
        DotPlugin.files.get("world").get().set("lobby.pitch",pitch);
        DotPlugin.files.get("world").get().set("lobby.world",world.getUID());
        return true;
    }

    public static Location getLobby(){
        World world = Bukkit.getWorld(Objects.requireNonNull(DotPlugin.files.get("world").get().getString("lobby.world")));
        if (world != null) {
            double xPos = DotPlugin.files.get("world").get().getDouble("lobby.x");
            double yPos = DotPlugin.files.get("world").get().getDouble("lobby.y");
            double zPos = DotPlugin.files.get("world").get().getDouble("lobby.z");
            float yaw = (float) DotPlugin.files.get("world").get().get("lobby.yaw");
            float pitch = (float) DotPlugin.files.get("world").get().get("lobby.pitch");
            return new Location(world, xPos, yPos, zPos, yaw, pitch);
        }else{
            return null;
        }
    }

    public static boolean setWarp(String name, Location location){
        String worldName = Objects.requireNonNull(location.getWorld()).getName();
        int xPos = location.getBlockX();
        int yPos = location.getBlockY();
        int zPos = location.getBlockZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        World world = location.getWorld();
        String path = "warps."+name;
        DotPlugin.files.get("world").get().set(path+".x",xPos);
        DotPlugin.files.get("world").get().set(path+".y",yPos);
        DotPlugin.files.get("world").get().set(path+".z",zPos);
        DotPlugin.files.get("world").get().set(path+".yaw",yaw);
        DotPlugin.files.get("world").get().set(path+".pitch",pitch);
        DotPlugin.files.get("world").get().set(path+".world",world.getUID());
        return true;
    }

    public static Location getWarp(String name){
        World world = Bukkit.getWorld(Objects.requireNonNull(DotPlugin.files.get("world").get().getString("warps."+name+".world")));
        if (world!=null){
            String path = "warps."+name;
            double xPos = DotPlugin.files.get("world").get().getDouble(path+".x");
            double yPos = DotPlugin.files.get("world").get().getDouble(path+".y");
            double zPos = DotPlugin.files.get("world").get().getDouble(path+".z");
            float yaw = (float) DotPlugin.files.get("world").get().get(path+".yaw");
            float pitch = (float) DotPlugin.files.get("world").get().get(path+".pitch");
            return new Location(world,xPos,yPos,zPos,yaw,pitch);
        }else {
            return null;
        }
    }

}
