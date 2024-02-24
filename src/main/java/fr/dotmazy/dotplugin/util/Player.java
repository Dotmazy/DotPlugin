package fr.dotmazy.dotplugin.util;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.music.Music;
import fr.dotmazy.dotplugin.util.music.PlayedMusic;
import fr.dotmazy.dotplugin.util.raw.RawBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.SoundCategory;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.*;

public class Player {
    private final org.bukkit.entity.Player player;

    public Player(org.bukkit.entity.Player player){
        this.player = player;
    }

    public static Player valueOf(org.bukkit.entity.Player player){
        return new Player(player);
    }

    public static List<Player> valueOf(List<org.bukkit.entity.Player> players){
        ArrayList<Player> playersResult = new ArrayList<>();
        for (org.bukkit.entity.Player player : players)
            playersResult.add(Player.valueOf(player));
        return playersResult;
    }

    public static Player fromName(String name){
        org.bukkit.entity.Player p = Bukkit.getPlayer(name);
        if(p!=null) return new Player(p);
        else return null;
    }

    public void sendRawMessage(RawBuilder raw){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw "+getName()+" "+raw.toString());
    }

    public void sendMessage(String msg){
        player.sendMessage(msg);
    }

    public void sendTitle(){

    }

    public org.bukkit.entity.Player get(){
        return player;
    }

    public UUID getUUID(){
        return player.getUniqueId();
    }

    public String getName(){
        return player.getName();
    }

    public void create(){
        Database.executeUpdate("INSERT INTO players(uuid,name) VALUES ('"+getUUID()+"','"+getName()+"') ");
    }

    public void setNickName(String name){
        Database.executeUpdate("UPDATE players SET nickName='"+name+"' WHERE uuid='"+getUUID()+"'");
    }

    public void warn(String reason, org.bukkit.entity.Player warner){
        Database.executeUpdate("INSERT INTO warns(playerUuid,warnerUuid,reason) VALUES ('"+getUUID()+"','"+warner.getUniqueId()+"','"+reason+"')");
    }

    public void setRank(String rank){
        setRank(Rank.fromName(rank));
    }

    public void setRank(Rank rank){
        Database.executeUpdate("UPDATE players SET rank='"+rank.getId()+"' WHERE uuid='"+getUUID()+"'");
    }

    public void setHome(String name, Location loc){
        if(hasHome(name)) {
            Database.executeUpdate("UPDATE homes SET x='"+loc.getX()+"',y='"+loc.getY()+"',z='"+loc.getZ()+"',yaw='"+loc.getYaw()+"',pitch='"+loc.getPitch()+"' WHERE playerUuid='"+getUUID()+"' AND name='"+name+"'");
        }else{
            Database.executeUpdate("INSERT INTO homes(playerUuid,name,x,y,z,yaw,pitch) VALUES ('"+getUUID()+"','"+name+"','"+loc.getX()+"','"+loc.getY()+"','"+loc.getZ()+"','"+loc.getYaw()+"','"+loc.getPitch()+"')");
        }
    }

    public void setHome(String name){
        setHome(name,player.getLocation());
    }

    public void setVanish(boolean vanish){
        if(vanish) DotPlugin.vanishedPlayers.add(player);
        else DotPlugin.vanishedPlayers.remove(player);
    }

    public void toggleVanish(){
        setVanish(!isVanish());
    }

    public void setModerationMode(boolean mod){
        if(mod) DotPlugin.modPlayers.remove(player);
        else DotPlugin.modPlayers.put(player,player.getInventory().getContents());
    }

    public void toggleModerationMode(){
        setModerationMode(!isInModerationMode());
    }

    public void setMute(boolean mute){
        if(mute){
            DotPlugin.mutePlayers.add(player);
            Database.executeUpdate("UPDATE players SET mute=1 WHERE uuid='"+getUUID()+"'");
        }else{
            DotPlugin.mutePlayers.remove(player);
            Database.executeUpdate("UPDATE players SET mute=0 WHERE uuid='"+getUUID()+"'");
        }
    }

    public void toggleMute(boolean mute){
        setMute(!isMute());
    }

    public void setFreeze(boolean freeze){
        if(freeze){
            DotPlugin.freezePlayers.add(player);
            Database.executeUpdate("UPDATE players SET freeze=1 WHERE uuid='"+getUUID()+"'");
        }else{
            DotPlugin.freezePlayers.remove(player);
            Database.executeUpdate("UPDATE players SET freeze=0 WHERE uuid='"+getUUID()+"'");
        }
    }

    public void toggleFreeze(){
        setFreeze(!isFreeze());
    }

    public boolean hasPerms(String... perms){
        return getRank().hasPerms(perms);
    }

    public boolean hasOneOfPerms(String... perms){
        return getRank().hasOneOfPerms(perms);
    }

    public String getNickName(){
        /*return Database.executeStringQuery("SELECT nickName FROM players WHERE uuid='"+getUUID()+"'")*/
        return null;
    }

    public boolean hasNickName(){
        return getNickName()!=null;
    }

    public MultiMap<OfflinePlayer,String> getWarns(){
        return new MultiMap<>(getWarnsPlayer(),getWarnsReason());
    }

    public Map<Integer,String> getWarnsReason(){
        Map<Integer,String> warnsReason = new HashMap<>();
        /*ResultSet result = Database.executeQuery("SELECT reason FROM warns WHERE playerUuid='"+getUUID()+"'")*/
        int i = 0;
        /*while(result.next()) {
            warnsReason.put(i,result.getString(1));
            i++;
        }*/
        return warnsReason;
    }

    public Map<Integer,OfflinePlayer> getWarnsPlayer(){
        Map<Integer,OfflinePlayer> warnsPlayer = new HashMap<>();
        /*ResultSet result = Database.executeQuery("SELECT warnerUuid FROM warns WHERE playerUuid='"+getUUID()+"'")*/
        int i = 0;
        /*while(result.next()) {
            warnsPlayer.put(i,Bukkit.getOfflinePlayer(UUID.fromString(result.getString(1))));
            i++;
        }*/
        return warnsPlayer;
    }

    public Rank getRank(){
        return Rank.fromName(Database.executeStringQuery("SELECT rank FROM players WHERE uuid='"+getUUID()+"'"));
    }

    public boolean hasHome(String name){
        return getHome(name)!=null;
    }

    public Location getHome(String name){
        try{
            ResultSet result = Database.executeQuery("SELECT x,y,z,yaw,pitch FROM homes WHERE playerUuid='"+getUUID()+"' AND name='"+name+"'");
            if(result.next()){
                return new Location(player.getWorld(), result.getDouble(1),result.getDouble(2),result.getDouble(3),result.getFloat(4),result.getFloat(5));
            }
        }catch(Exception ignore){}
        return null;
    }

    public boolean isVanish(){
        return DotPlugin.vanishedPlayers.contains(player);
    }

    public boolean isInModerationMode(){
        return DotPlugin.modPlayers.containsKey(player);
    }

    public boolean isMute(){
        /*return Database.executeBooleanQuery("SELECT mute FROM players WHERE uuid='"+getUUID()+"'")*/
        return false;
    }

    public boolean isFreeze(){
        /*return Database.executeBooleanQuery("SELECT freeze FROM players WHERE uuid='"+getUUID()+"'")*/
        return false;
    }

    public void startMusic(String music){
        player.stopSound(SoundCategory.MASTER);
        float volume = Music.volume!=null?(Music.volume.get(player)!=null?Music.volume.get(player):1):1;
        player.playSound(player.getLocation(), "music.game."+music, volume/100, 1);
        DotPlugin.playMusics.put(player,new PlayedMusic(Music.get(music)));
        player.sendMessage("\u00A76[Music]: \u00A77Start music "+music);
    }

    public void startMusic(Music music){
        player.stopSound(SoundCategory.MASTER);
        player.playSound(player.getLocation(), "music.game."+music.getMusic(), (float)getVolume()/100, 1);
        DotPlugin.playMusics.put(player,new PlayedMusic(music));
        player.sendMessage("\u00A76[Music]: \u00A77Start music "+music.getName());
    }

    public void stopMusic(){
        player.stopSound(SoundCategory.MASTER);
        DotPlugin.playMusics.remove(player);
        player.sendMessage("\u00A76[Music]: \u00A77Stopped music");
    }

    public void stopAllMusic(){
        player.stopSound(SoundCategory.MASTER);
        DotPlugin.playMusics.remove(player);
        DotPlugin.startedPlaylists.remove(player);
        player.sendMessage("\u00A76[Music]: \u00A77Stopped music and auto music");
    }

    public void setVolume(float volume){
        Database.executeUpdate("UPDATE players SET musicVolume='"+volume+"' WHERE uuid='"+player.getUniqueId()+"'");
    }

    public int getVolume(){
        return Database.executeIntegerQuery("SELECT musicVolume FROM players WHERE uuid='"+player.getUniqueId()+"'");
    }

    public void volumeUp(float volume){
        setVolume(getVolume()+volume);
    }

    public void volumeDown(float volume){
        setVolume(getVolume()-volume);
    }

    public void addPlaylist(String name, ItemStack icon){
        Database.executeUpdate("INSERT INTO playlists (name,icon,ownerUuid) VALUES ('"+name+"','"+formatItemStack(icon)+"','"+player.getUniqueId()+"')");
    }

    private String formatItemStack(ItemStack item){
        System.out.println(item.getType());
        return item.getType().toString();
    }

    /*public void addMusicToPlaylist(Music music, int id){
        if(hasOnAutoMusic(music)) removeFromAutoMusic(music);
        else addToAutoMusic(music);
    }*/

    private static final Map<org.bukkit.entity.Player,Integer> musics = new HashMap<>();

    public int getPlaylistMode(){
        return Database.executeIntegerQuery("SELECT playlistMode FROM players WHERE uuid='"+player.getUniqueId()+"'");
    }

    public void setPlaylistMode(int mode){
        if(mode==0) musics.put(player,0);
        else musics.remove(player);
        Database.executeUpdate("UPDATE players SET playlistMode="+mode+" WHERE uuid='"+player.getUniqueId()+"'");
    }

    public int switchPlaylistMode(){
        if(getPlaylistMode()<2)
            setPlaylistMode(getPlaylistMode()+1);
        else
            setPlaylistMode(0);
        return getPlaylistMode();
    }

    public void startPlaylist(String name){
        startPlaylist(name,true);
    }

    private void startPlaylist(String name, boolean isFirst){
        try{
            ResultSet result = Database.executeQuery("SELECT * FROM playlistMusics WHERE playlistId='"+Database.executeIntegerQuery("SELECT id FROM playlists WHERE name='"+name+"' AND ownerUuid='"+getUUID()+"'")+"'");
            List<Music> musics = new ArrayList<>();
            while(result.next())
                musics.add(Music.fromMusic(Database.executeStringQuery("SELECT music FROM musics WHERE id='"+result.getString("musicId")+"'")));
            if(isFirst) {
                player.sendMessage("\u00A76[Playlist]: \u00A77Started playlist " + name);
            }
            Player.musics.putIfAbsent(player, 0);
            int music = Player.musics.get(player);

            Music chosenMusic = chose(musics);

            player.stopSound(SoundCategory.MASTER);
            player.playSound(player.getLocation(), "music.game."+chosenMusic.getMusic(), (float)getVolume()/100, 1);
            if(DotPlugin.playMusics.containsKey(player))
                DotPlugin.playMusics.replace(player,new PlayedMusic(chosenMusic));
            else DotPlugin.playMusics.put(player,new PlayedMusic(chosenMusic));
            player.sendMessage("\u00A76[Playlist]: \u00A77Start music "+chosenMusic.getName());
            if(getPlaylistMode()==0) Player.musics.replace(player,music+1);

            if(DotPlugin.playMusics.containsKey(player)) DotPlugin.playMusics.replace(player,new PlayedMusic(chosenMusic));
            else DotPlugin.playMusics.put(player,new PlayedMusic(chosenMusic));
            UUID uuid = UUID.randomUUID();
            Playlist playlist = new Playlist(name,uuid);
            if(DotPlugin.startedPlaylists.containsKey(player))
                DotPlugin.startedPlaylists.replace(player, playlist);
            else DotPlugin.startedPlaylists.put(player, playlist);
            new Timer().schedule(new TimerTask() {
                public void run() {
                    if(DotPlugin.startedPlaylists.containsKey(player) && DotPlugin.startedPlaylists.get(player).getUuid()==uuid){
                        startPlaylist(name,false);
                    }
                }
            },(long)(chosenMusic.getTime()*1000));
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    private Music chose(List<Music> musics){
        int music = Player.musics.get(player);
        Music chosenMusic = musics.get(getPlaylistMode()==0?music:new Random().nextInt(musics.size()-1));
        if(chosenMusic==null && getPlaylistMode()==1) return chose(musics);
        return chosenMusic;
    }

}
