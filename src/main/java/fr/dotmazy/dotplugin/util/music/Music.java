package fr.dotmazy.dotplugin.util.music;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Music {

    public static Music NE_RAGE_QUITE_PAS = new Music("ne-rage-quitte-pas", Material.BARRIER, 3.23, "Ne rage quitte pas", "95jBpUBX-xI");
    public static Music JAI_LA_DALLE = new Music("jai-la-dalle", Material.CUT_SANDSTONE, 3.24, "Jai la dalle", "ho_riOB9aTs");
    public static Music CE_SOIR = new Music("ce-soir", Material.LIGHT_BLUE_TERRACOTTA, 3.19, "Ce soir", "PvufrL9IzkU");
    public static Music COURT_METRAGE_MUSICAL = new Music("court-metrage-musicale", Material.JUKEBOX, 14.11, "Court metrage musicale", "ZFN8yqFXYfY");
    public static Music DANS_LA_NUIT = new Music("dans-la-nuit", Material.BLUE_TERRACOTTA, 3.31, "Dans la nuit", "91AVSv5jh4Q");
    public static Music HEROBRINE = new Music("herobrine", Material.PLAYER_HEAD, 3.26, "Herobrine", "2MbCiq-6Nus");
    public static Music JAIMERAIS_TROP_QUIL_CREVE = new Music("jaimerais-trop-quil-creve", Material.ZOMBIE_HEAD, 3.43, "J'aimerais trop quil cr\u00E8ve", "yM8UcnKbUvI");
    public static Music JAI_SOMBRE = new Music("jai-sombre", Material.BLACK_CONCRETE, 3.32, "J'ai sombr\u00E9", "0Pm0H8WkYG4");
    public static Music JARRIVE_PAS_A_PECHO = new Music("jarrive-pas-a-pecho", Material.SKELETON_SKULL, 3.04, "J'arrive pas a pecho", "cK_V_2xayes");
    public static Music JEAN_KEVIN = new Music("jean-kevin", Material.ZOMBIE_HEAD, 3.33, "Jean kevin", "6BUdjSpSaec");
    public static Music JE_CONSTRUIS_MA_MAISON = new Music("je-construis-ma-maison", Material.OAK_PLANKS, 4.18, "Je construis ma maison", "Dh-ymBzHPW8");
    public static Music JE_TOUBLIERAIS_JAMAIS = new Music("je-toublierais-jamais", Material.TROPICAL_FISH_BUCKET, 3.59, "Je t'oublirais jamais", "wv67EH4YQXM");
    public static Music LA_PIRE_DES_TEAMS = new Music("la-pire-des-teams", Material.COARSE_DIRT, 3.5, "La pire des team", "MP3_KqAtDww");
    public static Music LE_TEMP_DUN_REVE = new Music("le-temp-dun-reve", Material.WHITE_CONCRETE, 3.02, "Le temp d'un r\u00E8ve", "NsCqte2AnSU");
    public static Music MA_PELLE = new Music("ma-pelle", Material.GOLDEN_SHOVEL, 2.47, "Ma pelle", "gXA7IDxgzNI");
    public static Music MARIE_A_UN_CREEPER = new Music("marie-a-un-creeper", Material.CREEPER_SPAWN_EGG, 3.05, "Mari\u00E9 a un creeper", "F6k06hV2cQk");
    public static Music MON_DIAMS_OU_TES = new Music("mon-diams-ou-tes", Material.DIAMOND, 4.0, "Mon diams ou t'est", "0RChy3TyA90");
    public static Music NOEL_POUR_TOI_CEST_MORT = new Music("noel-pour-toi-cest-mort", Material.RED_CARPET, 2.60, "Noel pour toi c'est mort", "BFq5TqdrkfA");
    public static Music PAS_LNIVEAU = new Music("pas-lniveau", Material.DRAGON_EGG, 3.04, "Pas l'niveau", "esWqmcVbLws");
    public static Music PGM = new Music("pgm", Material.ENDER_PEARL, 3.53, "Pgm", "rVHDRVkxBpA");
    public static Music PLUS_TARD = new Music("plus-tard", Material.CLOCK, 3.27, "Plus tard", "xKw4t7qCQPs");
    public static Music YEN_A_MARRE_DHALLOWEEN = new Music("yen-a-marre-dhalloween", Material.PUMPKIN, 3.20, "Yen a marre d'halloween", "LS9nqntYzHA");
    public static Music TU_A_EXPLOSE = new Music("tu-as-explose", Material.CREEPER_HEAD, 3.00, "Tu as explos\u00E9", "G2TYIgE9nLo");
    public static Music TOUT_CASSER = new Music("tout-casser", Material.TNT, 2.47, "Tout casser", "XGe4zjV0au8");
    public static Music TOI_ET_MOI = new Music("toi-et-moi", Material.MUSIC_DISC_11, 4.13, "Toi et moi", "EMBmxTi9QZo");
    public static Music SUR_MA_MAP = new Music("sur-ma-map", Material.FILLED_MAP, 4.03, "Sur ma map", "Gvx6AjScTgQ");
    public static Music SUR_BLOOD_SYMPHONY = new Music("sur-blood-symphony", Material.PLAYER_HEAD, 3.34, "Sur blood symphony", "GgDZpp1QRdI");
    public static Music SAVE_ME = new Music("save-me", Material.VILLAGER_SPAWN_EGG, 3.46, "Save me", "mBZrQooyGJY");
    public static Music ROOTS_NOOB = new Music("roots-noob", Material.COARSE_DIRT, 2.56, "Roots noob", "hHfARqlU_hc");
    public static Music REDSTONE = new Music("redstone", Material.REDSTONE, 2.55, "Redstone", "8D_QD9ORklY");
    public static Music POPOPOP_BLOC_PAR_BLOC = new Music("popopop-bloc-par-bloc", Material.STONE, 2.26, "Popopop bloc par bloc", "_eNorj8c0MI");
    public static Music ENDERMAN = new Music("enderman", Material.ENDERMAN_SPAWN_EGG, 3.03, "Enderman", "EEmO_pwTitY");
    public static Music ZOMBIE = new Music("zombie", Material.ZOMBIE_SPAWN_EGG, 3.24, "Zombie", "Or9n9XV3EJ8");
    public static Music NON_TE_CACHE_PAS = new Music("non-te-cache-pas", Material.BARRIER, 3.35, "Non te cache pas", "gD-LHwlo5s8");
    public static Music TA_MAISON = new Music("ta-maison", Material.OAK_STAIRS, 3.03, "Ta maison", "l74--PKfiGc");
    public static Music PIOCHE_PIOCHE_PIOCHE = new Music("pioche-pioche-pioche", Material.DIAMOND_PICKAXE, 3.24, "Pioche Pioche Pioche", "QazeYaP0oAM");
    public static Music TON_DESTIN = new Music("ton-destin", Material.WHITE_CONCRETE, 3.5, "Ton Destin", "1INFNZ2buNQ");

    private static List<Music> musics;

    private final String music;
    private final Material type;
    private final Double time;
    private final String name;
    private final String youtubeVideo;

    private Music(String music, Material type, Double time, String name, String youtubeVideo){
        if(musics == null) musics =  new ArrayList<>();
        this.music = music;
        this.type = type;
        this.time = time*60;
        this.name = name;
        this.youtubeVideo = youtubeVideo;
        musics.add(this);
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

    public static Music getRandomMusic(Player player){
        return musics.get((int)Math.round(Math.random()*(musics.size()-1)));
    }

    public static void stopMusic(Player player){
        player.stopSound(SoundCategory.MASTER);
        DotPlugin.playMusics.remove(player);
        player.sendMessage("Stopped music");
    }

    public static void stopAll(Player player){
        player.stopSound(SoundCategory.MASTER);
        DotPlugin.playMusics.remove(player);
        DotPlugin.autoMusicPlayers.remove(player);
        player.sendMessage("Stopped music and auto music");
    }

    public static void startAutoMusic(Player player){
        autoMusic(player, true);
    }

    public static void startMusic(Player player, String music){
        player.stopSound(SoundCategory.MASTER);
        player.playSound(player.getLocation(), "music.game."+music, 100, 1);
        DotPlugin.playMusics.put(player,new PlayedMusic(get(music)));
        player.sendMessage("Start music "+music);
    }

    public static Music get(String music){
        for(Music m : musics)
            if(Objects.equals(m.getMusic(),music)) return m;
        return null;
    }

    public static void toggleAutoMusic(Player player){
        if(DotPlugin.autoMusicPlayers.containsKey(player)) stopAll(player);
        else autoMusic(player, true);
    }

    private static void autoMusic(Player player, boolean isFirst){
        if(!isFirst || !DotPlugin.autoMusicPlayers.containsKey(player)){
            Music chosenMusic = Music.getRandomMusic(player);
            player.sendMessage("\u00A76[Auto music]: \u00A77Start music "+chosenMusic.getName());
            player.stopSound(SoundCategory.MASTER);
            player.playSound(player.getLocation(), "music.game."+chosenMusic.getMusic(), 100, 1);
            if(DotPlugin.playMusics.containsKey(player)) DotPlugin.playMusics.replace(player,new PlayedMusic(chosenMusic));
            else DotPlugin.playMusics.put(player,new PlayedMusic(chosenMusic));
            UUID uuid = UUID.randomUUID();
            DotPlugin.autoMusicPlayers.put(player, uuid);
            Bukkit.getScheduler().runTaskLater(DotPlugin.getInstance(), ()->{
                if(DotPlugin.autoMusicPlayers.containsKey(player) && DotPlugin.autoMusicPlayers.get(player) == uuid)
                    autoMusic(player, false);
            },(long)(chosenMusic.getTime()*20));
        }
    }
}
