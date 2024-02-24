package fr.dotmazy.dotplugin.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.util.*;

public class Rank {
    private final int id;
    private final String name;
    private String prefix;
    private String suffix;
    private List<OfflinePlayer> players;
    private List<String> perms;

    private Rank(int id, String name, String prefix, String suffix, List<OfflinePlayer> players, List<String> perms){
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.players = players;
        this.perms = perms;
    }

    private Rank(int id, String name, String prefix, String suffix){
        this(id,name,prefix,suffix,null,null);
    }

    public static Rank fromName(String name){
        try {
            ResultSet result = Database.executeQuery("SELECT * FROM ranks WHERE name='" + name + "'");
            Rank rank = null;
            if (result.next())
                rank = new Rank(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
            else
                return null;
            ResultSet playersResult = Database.executeQuery("SELECT uuid FROM players WHERE rank='"+rank.getId()+"'");
            List<OfflinePlayer> players = new ArrayList<>();
            while (playersResult.next())
                players.add(Bukkit.getOfflinePlayer(UUID.fromString(result.getString(1))));

            ResultSet permsResult = Database.executeQuery("SELECT name FROM perms WHERE rankId='"+rank.getId()+"'");
            List<String> perms = new ArrayList<>();
            while (permsResult.next())
                perms.add(result.getString(1));

            return new Rank(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), players, perms);
        }catch(Exception e){
            return null;
        }
    }

    public void setPrefix(String prefix) {
        Database.executeUpdate("UPDATE ranks SET prefix='"+prefix+"' WHERE id='"+id+"'");
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        Database.executeUpdate("UPDATE ranks SET suffix='"+suffix+"' WHERE id='"+id+"'");
        this.suffix = suffix;
    }

    public void addPerms(String... perms){
        for(String perm : perms)
            Database.executeUpdate("INSERT INTO perms(rankId,name) VALUES ('"+id+"','"+perm+"')");
        this.perms.addAll(Arrays.stream(perms).toList());
    }

    public void removePerms(String... perms){
        for(String perm : perms)
            Database.executeUpdate("DELETE FROM perms WHERE rankId='"+id+"' AND name='"+perm+"'");
        this.perms.removeAll(Arrays.stream(perms).toList());
    }

    public void clearPerms(){
        Database.executeUpdate("DELETE FROM perms WHERE rankId='"+id+"'");
        perms.clear();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public List<OfflinePlayer> getPlayers() {
        return players;
    }

    public List<String> getPerms() {
        return perms;
    }

    public boolean hasPerm(String perm){
        List<String> path = List.of(perm.split("\\."));
        if(getPerms().contains(perm)) return true;
        String pa = "";
        for(String p : path) {
            pa += p;
            if (getPerms().contains(pa+".*")) return true;
        }
        return false;
    }

    public boolean hasPerms(String... perms){
        for(String perm : perms) if(!hasPerm(perm)) return false;
        return true;
    }

    public boolean hasOneOfPerms(String... perms){
        for(String perm : perms)
            if(hasPerm(perm)) return true;
        return false;
    }

    public Rank update(){
        Rank rank = fromName(name);
        if(rank==null) return null;
        this.prefix = rank.getPrefix();
        this.suffix = rank.getSuffix();
        this.players = rank.getPlayers();
        this.perms = rank.getPerms();
        return this;
    }
}
