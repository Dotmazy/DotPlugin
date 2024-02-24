package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.Database;
import fr.dotmazy.dotplugin.util.Player;
import fr.dotmazy.dotplugin.util.music.Music;
import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.AbstractMultiGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import fr.dotmazy.dotplugin.util.music.PlayedMusic;
import fr.dotmazy.dotplugin.util.raw.ClickAction;
import fr.dotmazy.dotplugin.util.raw.ClickEvent;
import fr.dotmazy.dotplugin.util.raw.Component;
import fr.dotmazy.dotplugin.util.raw.RawBuilder;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicGui extends AbstractMultiGui {
    private static final Map<org.bukkit.entity.Player,String> names = new HashMap<>();
    private static final Map<org.bukkit.entity.Player,Material> icons = new HashMap<>();

    public MusicGui(org.bukkit.entity.Player player){
        super();
        init(Player.valueOf(player));
    }

    protected void init(Player player) {
        addGui(new MusicGuiMain(0,player));
        for(int i=1;i<Music.getNumberOfPages();i++) {
            addGui(new MusicGuiMain(i,player));
        }
        addGui(new PlayedMusicGui());
        addGui(new PlaylistsGui());
        //addGui(new PlaylistGui());
    }

    protected void init() {}

    private class MusicGuiMain extends AbstractGui {
        public int slot = 10;
        public final int page;

        public MusicGuiMain(int page, Player player){
            this.page = page;
            //init(Music.getMusicOnPage(page), page, player);
        }

        protected void init() {}

        protected void init(List<Music> musics, int page, Player player) {
            makeFrame(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"), 1);

            if(names.containsKey(player.get()) && icons.containsKey(player.get()))
                initPlaylist(musics,page,player);
            else
                initNormal(musics,page,player);

            for(Music music : musics){
                if(names.containsKey(player.get()) && icons.containsKey(player.get()))
                    addPlaylistMusic(music, player);
                else
                    addMusic(music, player);
            }

            if(page>0)
                setItem(new GuiItem(Material.MAP).onLeftClick((item, player2, number, slot) ->
                                player2.get().openInventory(guis.get(page-1).getInventory()), true)
                        .setDisplayName("\u00A7fPrevious"), 45);

            if(Music.getNumberOfPages()-1 > page)
                setItem(new GuiItem(Material.MAP).onLeftClick((item, player2, number, slot) ->
                                player2.get().openInventory(guis.get(page+1).getInventory()), true)
                        .setDisplayName("\u00A7fNext"), 53);
        }

        private void initNormal(List<Music> musics, int page, Player player){
            setItem(new GuiItem(Material.STRUCTURE_VOID).onLeftClick((item, player2, number, slot) ->
                    player2.stopAllMusic(), true).setDisplayName("\u00A7cStop Music"), 0);
            setItem(new GuiItem(Material.MUSIC_DISC_MELLOHI).onLeftClick((item, player2, number, slot) ->
                            player2.get().openInventory(guis.get(Music.getNumberOfPages()+1).getInventory()), true)
                    .setDisplayName("\u00A7fPlaylists"), 4);
            setItem(new GuiItem(Material.MUSIC_DISC_MELLOHI).onLeftClick((item, player2, number, slot) ->
                            player2.get().openInventory(guis.get(Music.getNumberOfPages()).getInventory()), true)
                    .setDisplayName("\u00A7fMusic Played"), 8);
        }

        private void initPlaylist(List<Music> musics, int page, Player player){
            setItem(new GuiItem(Material.MAP).onLeftClick((item, player2, number, slot) ->{
                player2.get().openInventory(guis.get(Music.getNumberOfPages()+1).getInventory());
                names.remove(player.get());
                icons.remove(player.get());
            }, true).setDisplayName("\u00A7fBack"), 0);
            setItem(new GuiItem(icons.get(player.get())).onLeftClick((item, player2, number, slot) ->
                            player2.get().openInventory(guis.get(Music.getNumberOfPages()+1).getInventory()), true)
                    .setDisplayName("\u00A7f"+names.get(player.get())), 4);
            setItem(new GuiItem(Material.STRUCTURE_VOID).onLeftClick((item, player2, number, slot) ->
                            player2.stopAllMusic(), true)
                    .setDisplayName("\u00A7cStop Music"), 8);
        }

        private void addMusic(Music music, Player player){
            setItem(new GuiItem(music.getType()).onLeftClick((item, player2, number, slot) -> {
                player2.startMusic(music);
            }, true).onRightClick((item, player2, number, slot) -> {
                player2.sendRawMessage(new RawBuilder()
                        .addComponent(new Component("Here is the url of the music "+music.getName()+": "))
                        .addComponent(new Component("https://www.youtube.com/watch?v="+music.getYoutubeVideo()).setColor("#63a5ee").setUnderlined(true).setClickEvent(new ClickEvent(ClickAction.OPEN_URL,"https://www.youtube.com/watch?v="+music.getYoutubeVideo()))
                        ));
            }, true)/*.onMiddleClick((item, player2, number, slot) -> {
                updateMusic(music,slot,player);
                player2.toggleAutoMusic(music);
            }, true)*/.setLore(
                    "\u00A78"+music.getFormattedTime(),
                    "\u00A77Left click: \u00A78Launch music",
                    //"\u00A77Middle Click: \u00A78Add/Remove from auto music ("+(player.hasOnAutoMusic(music)?"added":"removed")+")",
                    "\u00A77Right Click: \u00A78Get url").setDisplayName("\u00A7b"+music.getName()), slot);
            slot++;
            if(slot%9==8) slot+=2;
        }

        private void addPlaylistMusic(Music music, Player player){
            setPlaylistMusic(slot++,music,player);
            if(slot%9==8) slot+=2;
        }

        private void setPlaylistMusic(int slot, Music music, Player player){
            try{
                int playlistId = Database.executeIntegerQuery("SELECT id FROM playlists WHERE name='"+names.get(player.get())+"' AND ownerUuid='"+player.getUUID()+"'");
                setItem(new GuiItem(music.getType()).onLeftClick((item, player2, number, slot2) -> {
                    ResultSet result = Database.executeQuery("SELECT * FROM playlistMusics WHERE musicId='"+Database.executeIntegerQuery("SELECT id FROM musics WHERE music='"+music.getMusic()+"'")+"' AND playlistId='"+playlistId+"'");
                    try{
                        if(result.next())
                            Database.executeUpdate("DELETE FROM playlistMusics WHERE playlistId='"+playlistId+"'");
                        else
                            Database.executeUpdate("INSERT INTO playlistMusics(playlistId,musicId) VALUES ('"+playlistId+"','"+Database.executeIntegerQuery("SELECT id FROM musics WHERE music='"+music.getMusic()+"'")+"')");
                        setPlaylistMusic(slot,music,player);
                    }catch(SQLException e){}
                }, true).onRightClick((item, player2, number, slot2) -> {
                    player2.startMusic(music);
                }, true).setLore(
                        "\u00A77Left click: \u00A78Add / Remove music",
                        "\u00A77Right Click: \u00A78Start Music").setDisplayName("\u00A7"+(Database.executeQuery("SELECT * FROM playlistMusics WHERE musicId='"+Database.executeIntegerQuery("SELECT id FROM musics WHERE music='"+music.getMusic()+"'")+"' AND playlistId='"+playlistId+"'").next()?"b":"c")+music.getName()), slot);
            }catch(Exception e){}
        }

        protected String getName() {
            return "Music";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        protected int getSize() {
            return 54;
        }

        public boolean isDefaultCanceled() {
            return true;
        }

        protected void onOpen(Player player) {
            init(Music.getMusicOnPage(page), page, player);
            slot=10;
        }
    }

    private class PlayedMusicGui extends AbstractGui{
        private static boolean noMusic;
        private boolean hasLoadVolume;

        protected void init() {
            fill(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"));

            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7fBack").onLeftClick((item, player, number, slot) ->
                    player.get().openInventory(guis.get(0).getInventory()), true), 0);

            setItem(new GuiItem(Material.STRUCTURE_VOID).setDisplayName("\u00A7cStop Music").onLeftClick((item, player, number, slot) ->{
                player.stopAllMusic();
            }, true), 8);

            /*setItem(new GuiItem(Material.MUSIC_DISC_FAR).setDisplayName("\u00A7fToggle Auto Music").onLeftClick((item, player, number, slot) -> {
                    try{
                        player.toggleAutoMusic();
                    }catch (IndexOutOfBoundsException e){
                        player.sendMessage("\u00A76[AutoMusic]: \u00A77No music added");
                    }
            }, true), 4);*/

        }

        private void setVolumeUpItem(Player player){
            setItem(new GuiItem(Material.LIME_WOOL).setDisplayName("\u00A7fVolume up").onLeftClick((item, player2, number, slot) -> {
                if(player2.getVolume()>=100)
                    player2.sendMessage("\u00A76[Music]: \u00A77Volume already at maximum");
                else
                    player2.volumeUp(1);
                setVolumeUpItem(player2);
                setVolumeDownItem(player2);
            },true).onShiftLeftClick((item, player2, number, slot) -> {
                if(player2.getVolume()+10>100)
                    player2.sendMessage("\u00A76[Music]: \u00A77Volume already at maximum");
                else
                    player2.volumeUp(10);
                setVolumeUpItem(player2);
                setVolumeDownItem(player2);
            },true).setLore("\u00A77Volume: \u00A78"+player.getVolume(),"\u00A77Left Click: \u00A78+1","\u00A77Shift Left Click: \u00A78+10"), 5);
        }

        private void setVolumeDownItem(Player player){
            setItem(new GuiItem(Material.RED_WOOL).setDisplayName("\u00A7fVolume down").onLeftClick((item, player2, number, slot) -> {
                if(player2.getVolume()<=0)
                    player2.sendMessage("\u00A76[Music]: \u00A77Volume already at minimum");
                else
                    player2.volumeDown(1);
                setVolumeUpItem(player2);
                setVolumeDownItem(player2);
            },true).onShiftLeftClick((item, player2, number, slot) -> {
                if(player2.getVolume()-10<0)
                    player2.sendMessage("\u00A76[Music]: \u00A77Volume already at minimum");
                else
                    player2.volumeDown(10);
                setVolumeUpItem(player2);
                setVolumeDownItem(player2);
            },true).setLore("\u00A77Volume: \u00A78"+player.getVolume(),"\u00A77Left Click: \u00A78-1","\u00A77Shift Left Click: \u00A78-10"), 3);
        }

        protected void onTick(Player player) {
            if(!hasLoadVolume){
                try{
                    setVolumeUpItem(player);
                    setVolumeDownItem(player);
                }catch(Exception e){
                    e.printStackTrace();
                }
                hasLoadVolume=true;
            }
            if(!DotPlugin.playMusics.containsKey(player.get()) || DotPlugin.playMusics.get(player.get())==null){
                if(!noMusic) {
                    noMusic=true;
                    for (int i=28;i<35;i++)
                        setItem(new GuiItem(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("\u00A78No music play"), i);
                    setItem(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"), 13);
                    /*setItemOnTick(new GuiItem(Material.MUSIC_DISC_FAR).setDisplayName("\u00A7fToggle Auto Music")
                            .setLore("\u00A78Auto music disabled").onLeftClick((item, p, number) ->
                            player.toggleAutoMusic(), true), 4);*/
                }
            }else{
                noMusic = false;
                PlayedMusic playedMusic = DotPlugin.playMusics.get(player.get());
                Music music = playedMusic.getMusic();
                for(int i=28;i<35;i++){
                    setItem(new GuiItem(playedMusic.isActive(i-27)?Material.LIME_STAINED_GLASS_PANE:Material.BLACK_STAINED_GLASS_PANE).setDisplayName("\u00A7f"+music.getName())
                            .setLore("\u00A78Time: "+playedMusic.getFormattedTime()+"/"+music.getFormattedTime(),
                                    "\u00A78("+playedMusic.getPercentage()+")"), i);
                }

                setItem(new GuiItem(music.getType()).setDisplayName("\u00A7f"+music.getName()), 13);
            }
        }

        protected void onClose(Player player) {
            noMusic = false;
        }

        protected String getName() {
            return "Music Played";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        protected int getSize() {
            return 54;
        }

        public boolean isDefaultCanceled() {
            return true;
        }
    }

    private class PlaylistsGui extends AbstractGui{
        private int slot;
        private boolean hasLoad;

        protected void init() {
            fill(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"));

            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7fBack").onLeftClick((item, player, number, slot) ->
                    player.get().openInventory(guis.get(0).getInventory()), true), 0);

            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7fCreate playlist").onLeftClick((item, player, number, slot) -> {
                player.get().closeInventory();
                player.sendMessage("\u00A76[Music]: \u00A77Playlist name:");
                DotPlugin.chatInputPlayers.put(player.get(), (name) -> {
                    sendIcon(player, name);
                    player.sendMessage("\u00A76[Music]: \u00A77Set playlist name: '"+name+"'");
                });
            }, true), 4);
        }

        private void setModeItem(int mode){
            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7fChange mode").onLeftClick((item, player, number, slot) -> {
                setModeItem(player.switchPlaylistMode());
            }, true).setLore("\u00A78Current mode: "+(mode==0?"Next":"Random")), 8);
        }

        protected void sendIcon(Player player, String name){
            DotPlugin.chatInputPlayers.put(player.get(), (icon) -> {
                Material iconMaterial = Material.matchMaterial(icon);
                if(iconMaterial==null){
                    player.sendMessage("\u00A76[Music]: \u00A77Unknown icon: '"+icon+"'");
                    sendIcon(player,name);
                    return;
                }
                if(names.containsKey(player))
                    names.replace(player.get(),name);
                else
                    names.put(player.get(),name);
                if(icons.containsKey(player))
                    icons.replace(player.get(),iconMaterial);
                else
                    icons.put(player.get(),iconMaterial);
                Database.executeUpdate("INSERT INTO playlists(name,icon,ownerUuid) VALUES ('"+name+"','"+icon+"','"+player.getUUID()+"')");
                player.sendMessage("\u00A76[Music]: \u00A77Playlist created: '"+name+"' with icon: '"+iconMaterial.name()+"'");
                player.get().openInventory(guis.get(0).getInventory());
            });
        }

        protected void onTick(Player player) {
            if(!hasLoad){
                setModeItem(player.getPlaylistMode());
                slot=9;
                makeSquare(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"),0,1,9,4);
                ResultSet result = Database.executeQuery("SELECT * FROM playlists WHERE ownerUuid='"+player.getUUID()+"'");
                try{
                    while(result.next()){
                        String name = result.getString("name");
                        Material icon = Material.matchMaterial(result.getString("icon"));
                        showPlaylist(name,icon);
                    }
                }catch (Exception e){}
                hasLoad = true;
            }
        }

        protected void showPlaylist(String name, Material icon){
            setItem(new GuiItem(icon).setDisplayName("\u00A7f"+name).onMiddleClick((item, player, number, slot) ->{
                String playlistId = Database.executeStringQuery("SELECT id FROM playlists WHERE name='"+name+"' AND ownerUuid='"+player.getUUID()+"'");
                Database.executeUpdate("DELETE FROM playlists WHERE name='"+name+"' AND ownerUuid='"+player.getUUID()+"'");
                Database.executeUpdate("DELETE FROM playlistMusics WHERE playlistId='"+playlistId+"'");
                hasLoad=false;
                player.get().openInventory(guis.get(Music.getNumberOfPages()+1).getInventory());
            }, true).onRightClick((item, player, number, slot) ->{
                if(names.containsKey(player))
                    names.replace(player.get(),name);
                else
                    names.put(player.get(),name);
                if(icons.containsKey(player))
                    icons.replace(player.get(),icon);
                else
                    icons.put(player.get(),icon);
                player.get().openInventory(guis.get(0).getInventory());
            }, true).onLeftClick((item, player, number, slot) ->{
                player.startPlaylist(name);
            }, true).setLore(
                    "\u00A77Left click: \u00A78Start this playlist",
                    "\u00A77Middle click: \u00A78Remove this playlist",
                    "\u00A77Right click: \u00A78Modify this playlist"
            ), slot++);
        }

        protected String getName() {
            return "Playlists";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        protected int getSize() {
            return 54;
        }

        public boolean isDefaultCanceled() {
            return true;
        }

        protected void onClose(Player player) {
            hasLoad = false;
        }
    }

    /*private class PlaylistGui extends AbstractGui{
        protected void init() {
            fill(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"));

            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7fBack").onLeftClick((item, player, number, slot) ->
                    player.get().openInventory(guis.get(Music.getNumberOfPages()+1).getInventory()), true), 0);

            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7fChange mode").onLeftClick((item, player, number, slot) ->{
                player.switchPlaylistMode();
                /*setItemOnTick(new GuiItem(Material.MAP).setDisplayName("\u00A7fChange mode").onLeftClick((item, player, number, slot) ->{
                    player.changePlaylistMode();
                    setItemOnTick();
                }, true).setLore("\u00A78Current mode: "), 8);
            }, true).setLore("\u00A78Current mode: "), 8);
        }

        protected void onTick(Player player) {
            if(names.containsKey(player.get()) && icons.containsKey(player.get())){
                setItem(new GuiItem(icons.get(player.get())).setDisplayName(names.get(player.get())),4);
                names.remove(player.get());
                icons.remove(player.get());
            }
        }

        protected String getName() {
            return "Playlists";
        }

        protected GuiType getType() {
            return GuiType.CHEST;
        }

        protected int getSize() {
            return 54;
        }

        public boolean isDefaultCanceled() {
            return true;
        }

        protected void onClose(Player player) {

        }
    }*/

}
