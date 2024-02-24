package fr.dotmazy.dotplugin.util.music;

import fr.dotmazy.dotplugin.util.Database;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Music {
    public static Map<Player,Float> volume = new HashMap<>();

    private static List<Music> musics;

    private final String music;
    private final Material type;
    private final Double time;
    private final String name;
    private final String youtubeVideo;

    private Music(String music, Material type, double time, String name, String youtubeVideo){
        if(musics == null) musics = new ArrayList<>();
        this.music = music;
        this.type = type;
        this.time = time;
        this.name = name;
        this.youtubeVideo = youtubeVideo;
        musics.add(this);
    }

    public static void init(){
        ResultSet result = Database.executeQuery("SELECT * FROM musics");

        while(true){
            try{
                if (!result.next()) break;
                String name = result.getString("name");
                String music = result.getString("music");
                Material icon = Material.matchMaterial(result.getString("icon"));
                double time = result.getDouble("time");
                String ytVideo = result.getString("youtubeVideo");
                new Music(music,icon,time,name,ytVideo);
            }catch(Exception e) {}
        }
    }

    public static Music fromMusic(String music){
        for(Music m : musics){
            if(m.getMusic().equals(music)) return m;
        }
        return null;
    }

    public String getMusic() {
        return music;
    }

    public Material getType() {
        return type;
    }

    public Double getTime() {
        return time;
    }

    public String getFormattedTime(){
        int seconds = (int)(time*1);

        int minutes = seconds/60;
        int secondsR = seconds%60;

        return minutes+":"+(String.valueOf(secondsR).length()==1?"0":"")+secondsR;
    }

    public String getName() {
        return name;
    }

    public String getYoutubeVideo() {
        return youtubeVideo;
    }

    public static List<Music> getMusics(){
        return musics;
    }

    public static List<Music> getMusicOnPage(int page){
        int to = (page+1)*28;
        if(to>musics.size()) to = musics.size();
        return musics.subList(page*28,to);
    }

    public static int getNumberOfPages(){
        return (int)Math.ceil(musics.size()/36D);
    }

    public static List<String> getStringMusics(){
        List<String> ms = new ArrayList<>();
        for(Music m : musics)
            ms.add(m.getMusic());
        return ms;
    }

    /*public static Music getRandomMusic(Player player){
        List<Music> musics = fr.dotmazy.dotplugin.util.Player.fromPlayer(player).getAutoMusics();
        return musics.get((int)Math.round(Math.random()*(musics.size()-1)));
    }*/

    public static Music get(String music){
        for(Music m : musics)
            if(Objects.equals(m.getMusic(),music)) return m;
        return null;
    }

}
