package fr.dotmazy.dotplugin.api;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerApi {

    /**
     * Define a rank for a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * PlayerApi.joinRank(Bukkit.getServer().getPlayer("example"),
     *                    RankApi.getRank("example"));
     * }
     * </pre>
     *
     * @param player Player to define the rank
     * @param rank Rank to define
     */
    public static void joinRank(Player player, Rank rank){
        DotPlugin.files.get("players").get().set(player.getUniqueId()+".rank",rank.getName());
    }

    /**
     * Remove the rank of the player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * PlayerApi.leaveRank(Bukkit.getServer().getPlayer("example"),
     *                     RankApi.getRank("example"));
     * }
     * </pre>
     *
     * @param player Player to remove the rank
     */
    public static void leaveRank(Player player){
        DotPlugin.files.get("players").get().set(player.getUniqueId()+".rank",null);
    }

    /**
     * Warn a player
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.warnPlayer(Bukkit.getServer().getPlayer("example"),"example")){
     *     System.out.println("Successfully warn player");
     * }else{
     *     System.out.println("An error occurred when warn player");
     * }
     * }
     * </pre>
     *
     * @param player Player to warn
     * @return Warn successfully ?
     */
    public static boolean warnPlayer(Player player, String reason){

        return true;
    }

    /**
     * Get warns of a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * System.out.println(PlayerApi.getWarnsOfPlayer(Bukkit.getServer().getPlayer("example")));
     * }
     * </pre>
     *
     * @param player Player to get the warns
     * @return Warns of the player
     */
    public static HashMap<String,String> getWarnsOfPlayer(Player player){
        HashMap<String,String> map = new HashMap<String,String>();

        return map;
    }

    /**
     * Freeze a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.freezePlayer(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Successfully freeze player");
     * }else{
     *     System.out.println("Player already freeze");
     * }
     * }
     * </pre>
     *
     * @param player Player to freeze
     * @return Freeze successfully ? (Player already freeze)
     */
    public static boolean freezePlayer(Player player){
        if (isFreeze(player)) return false;
        DotPlugin.freezePlayers.add(player);
        DotPlugin.files.get("players").get().set(player.getUniqueId()+".freeze",true);
        return true;
    }

    /**
     * Unfreeze a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.unfreezePlayer(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Successfully unfreeze player");
     * }else{
     *     System.out.println("Player not freeze");
     * }
     * }
     * </pre>
     *
     * @param player Player to unfreeze
     * @return Unfreeze successfully ? (Player not freeze)
     */
    public static boolean unfreezePlayer(Player player){
        if (!isFreeze(player)) return false;
        DotPlugin.freezePlayers.remove(player);
        DotPlugin.files.get("players").get().set(player.getUniqueId()+".freeze",false);
        return true;
    }

    /**
     * Get if a player is freeze.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.isFreeze(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Player is freeze");
     * }else{
     *     System.out.println("Player is not freeze");
     * }
     * }
     * </pre>
     *
     * @param player Player to verify
     * @return Player is freeze?
     */
    public static boolean isFreeze(Player player){
        return DotPlugin.freezePlayers.contains(player);
    }

    /**
     * Mute a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.mutePlayer(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Successfully mute player");
     * }else{
     *     System.out.println("Player already mute");
     * }
     * }
     * </pre>
     *
     * @param player Player to mute
     * @return Mute successfully ? (Player already mute)
     */
    public static boolean mutePlayer(Player player){
        if (isMute(player)) return false;
        DotPlugin.mutePlayers.add(player);
        DotPlugin.files.get("players").get().set(player.getUniqueId()+".mute",true);
        return true;
    }

    /**
     * Unmute a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.unmutePlayer(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Successfully unmute player");
     * }else{
     *     System.out.println("Player not mute");
     * }
     * }
     * </pre>
     *
     * @param player Player to unmute
     * @return Unmute successfully ? (Player not mute)
     */
    public static boolean unmutePlayer(Player player){
        if (!isMute(player)) return false;
        DotPlugin.mutePlayers.remove(player);
        DotPlugin.files.get("players").get().set(player.getUniqueId()+".mute",false);
        return true;
    }

    /**
     * Get if the player is mute.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.isMute(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Player is mute");
     * }else{
     *     System.out.println("Player is not mute");
     * }
     * }
     * </pre>
     *
     * @param player Player to verify
     * @return Player is mute?
     */
    public static boolean isMute(Player player){
        return DotPlugin.mutePlayers.contains(player);
    }

    /**
     * Set home of a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * PlayerApi.setHome(Bukkit.getServer().getPlayer("example"),
     *                   "example",
     *                   new Location(Bukkit.getWorld("example"),0,0,0));
     * }
     * </pre>
     *
     * @param player Player to set the home
     */
    public static void setHome(Player player, String name, Location location){
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

    /**
     * Get a home of a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * System.out.println(PlayerApi.getHome(Bukkit.getServer().getPlayer("example"), "example"));
     * }
     * </pre>
     *
     * @param player Player to get the home
     * @return Location of the home of the player
     */
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

    /**
     * Get homes of a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * System.out.println(PlayerApi.getHomes(Bukkit.getServer().getPlayer("example")));
     * }
     * </pre>
     *
     * @param player Player to get the homes
     * @return Homes of the player
     */
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

    /**
     * Get rank of a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * System.out.println(PlayerApi.getRankOfPlayer(Bukkit.getServer().getPlayer("example")));
     * }
     * </pre>
     *
     * @param player Player to get the rank
     * @return Rank of the player
     */
    public static Rank getRankOfPlayer(Player player){
        try{
            System.out.println(DotPlugin.files.get("players"));
            System.out.println(DotPlugin.files.get("ranks"));
            String rankName = DotPlugin.files.get("players").get().getString(player.getUniqueId()+".rank");
            String prefix = DotPlugin.files.get("ranks").get().getString(rankName+".prefix");
            String suffix = DotPlugin.files.get("ranks").get().getString(rankName+".suffix");
            List<String> permissions = DotPlugin.files.get("ranks").get().getStringList(rankName+".permissions");
            return new Rank(rankName,prefix,suffix,permissions);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * Get if a player has a list of permissions.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.hasPermsAll(Bukkit.getServer().getPlayer("example"),"example","example2")){
     *     System.out.println("Player have this permissions");
     * }else{
     *     System.out.println("Player don't have this permissions");
     * }
     * }
     * </pre>
     *
     * @param player Player to verify
     * @param perms Permissions to verify
     * @return Player has permissions?
     */
    public static boolean hasPermsAll(Player player, String... perms){
        boolean result = true;
        for (String pe : perms){
            if (!player.hasPermission(pe)) result = false;
        }
        return result || player.isOp() || player.hasPermission("*");
    }

    /**
     * Vanish a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.vanishPlayer(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Vanished this player successfully");
     * }else{
     *     System.out.println("Player is already vanished");
     * }
     * }
     * </pre>
     *
     * @param player Player to vanish
     * @return Vanished successfully ? (Player already vanished)
     */
    public static boolean vanishPlayer(Player player){
        if (!isVanished(player)){
            DotPlugin.vanishedPlayers.add(player);
            return true;
        }
        return false;
    }

    /**
     * Unvanish a player.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.unvanishPlayer(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("Unvanished this player successfully");
     * }else{
     *     System.out.println("Player is not vanished");
     * }
     * }
     * </pre>
     *
     * @param player Player to unvanish
     * @return Unvanished successfully ? (Player not vanished)
     */
    public static boolean unvanishPlayer(Player player){
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.showPlayer(player);
        }
        if (isVanished(player)){
            DotPlugin.vanishedPlayers.remove(player);
            return true;
        }
        return false;
    }

    /**
     * Get if a player is vanished.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.isVanish(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("This player is vanished");
     * }else{
     *     System.out.println("This player is not vanished");
     * }
     * }
     * </pre>
     *
     * @param player Player to verify
     * @return Player is vanished?
     */
    public static boolean isVanished(Player player){
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.hidePlayer(player);
        }
        return DotPlugin.vanishedPlayers.contains(player);
    }

    /**
     * Get if a player is in moderation mode.
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(PlayerApi.isInModerationMode(Bukkit.getServer().getPlayer("example"))){
     *     System.out.println("This player is in moderation mode");
     * }else{
     *     System.out.println("This player is not in moderation mode");
     * }
     * }
     * </pre>
     *
     * @param player Player to verify
     * @return Player is in moderation mode?
     */
    public static boolean isInModerationMode(Player player){
        return DotPlugin.modPlayers.get(player) != null;
    }

    public static boolean hasPerms(Player player, String... perms) {
        for(String perm : perms){
            if(player.hasPermission(perm)){
                return true;
            }
        }
        return false;
    }
}
