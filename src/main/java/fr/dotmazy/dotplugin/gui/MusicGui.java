package fr.dotmazy.dotplugin.gui;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.Api;
import fr.dotmazy.dotplugin.util.music.Music;
import fr.dotmazy.dotplugin.old.api.PlayerApi;
import fr.dotmazy.dotplugin.util.gui.AbstractGui;
import fr.dotmazy.dotplugin.util.gui.AbstractMultiGui;
import fr.dotmazy.dotplugin.util.gui.GuiItem;
import fr.dotmazy.dotplugin.util.gui.GuiType;
import fr.dotmazy.dotplugin.util.music.PlayedMusic;
import fr.dotmazy.dotplugin.util.raw.Action;
import fr.dotmazy.dotplugin.util.raw.ClickEvent;
import fr.dotmazy.dotplugin.util.raw.Component;
import fr.dotmazy.dotplugin.util.raw.RawBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class MusicGui extends AbstractMultiGui {

    protected void init() {
        int i;
        for(i=0;i<Music.getNumberOfPages();i++) {
            MusicGui1.slot=10;
            addGui(new MusicGui1(i));
        }
        addGui(new AutoMusicGui());
    }

    private class MusicGui1 extends AbstractGui {
        public static int slot = 10;

        public MusicGui1(int page){
            init(Music.getMusicOnPage(page), page);
        }

        protected void init() {}

        protected void init(List<Music> musics, int page) {
            makeFrame(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"), 1);

            setItem(new GuiItem(Material.STRUCTURE_VOID).onLeftClick((item, player, number) ->
                    Music.stopMusic(player), true).setDisplayName("\u00A7cStop Music"), 0);
            setItem(new GuiItem(Material.MUSIC_DISC_MELLOHI).onLeftClick((item, player, number) ->
                    player.openInventory(guis.get(Music.getNumberOfPages()).getInventory()), true)
                    .setDisplayName("\u00A7fMusic Played"), 8);

            for(Music music : musics)
                addMusic(music);

            if(page>0)
                setItem(new GuiItem(Material.MAP).onLeftClick((item, player, number) ->
                                player.openInventory(guis.get(page-1).getInventory()), true)
                        .setDisplayName("\u00A7fPrevious"), 45);

            if(Music.getNumberOfPages()-1 > page)
                setItem(new GuiItem(Material.MAP).onLeftClick((item, player, number) ->
                                player.openInventory(guis.get(page+1).getInventory()), true)
                        .setDisplayName("\u00A7fNext"), 53);
        }

        private void addMusic(Music music){
            setItem(new GuiItem(music.getType()).onLeftClick((item, player, number) -> {
                Music.stopMusic(player);
                player.playSound(player.getLocation(),"music.game."+music.getMusic(),100,1);
                DotPlugin.playMusics.put(player,new PlayedMusic(music));
            }, true).onRightClick((item, player, number) -> {
                Api.Player.sendRawMessage(player, new RawBuilder()
                        .addComponent(new Component("Here is the url of the music "+music.getName()+": "))
                        .addComponent(new Component("https://www.youtube.com/watch?v="+music.getYoutubeVideo()).setColor("#63a5ee").setUnderlined(true).setEvent(new ClickEvent(Action.OPEN_URL,"https://www.youtube.com/watch?v="+music.getYoutubeVideo()))
                        ));
            }, true).setLore("\u00A77Left click: \u00A78Launch music","\u00A77Right Click: \u00A78Get url").setDisplayName("\u00A7b"+music.getName()), slot);
            slot++;
            if(slot%9==8) slot+=2;
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
    }

    private class AutoMusicGui extends AbstractGui{
        private static boolean noMusic;

        protected void init() {
            fill(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"));

            setItem(new GuiItem(Material.MAP).setDisplayName("\u00A7fBack").onLeftClick((item, player, number) ->
                    player.openInventory(guis.get(0).getInventory()), true), 0);

            setItem(new GuiItem(Material.MUSIC_DISC_FAR).setDisplayName("\u00A7fToggle Auto Music").onLeftClick((item, player, number) ->
                    Music.toggleAutoMusic(player), true), 4);

            setItem(new GuiItem(Material.STRUCTURE_VOID).setDisplayName("\u00A7cStop Music").onLeftClick((item, player, number) ->{
                    Music.stopAll(player);
            }, true), 8);

        }

        protected void onTick(Player player) {
            if(!DotPlugin.playMusics.containsKey(player) || DotPlugin.playMusics.get(player)==null){
                if(!noMusic) {
                    noMusic=true;
                    for (int i=28;i<35;i++)
                        setItemOnTick(new GuiItem(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("\u00A78No music play"), i);
                    setItemOnTick(new GuiItem(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("\u00A7"), 13);
                }
            }else{
                noMusic = false;
                PlayedMusic playedMusic = DotPlugin.playMusics.get(player);
                Music music = playedMusic.getMusic();
                for(int i=28;i<35;i++)
                    setItemOnTick(new GuiItem(playedMusic.isActive(i-27)?Material.LIME_STAINED_GLASS_PANE:Material.BLACK_STAINED_GLASS_PANE).setDisplayName("\u00A7f"+music.getName())
                            .setLore("\u00A78Time: "+playedMusic.getFormattedTime()+"/"+music.getFormattedTime(),
                                    "\u00A78("+playedMusic.getPercentage()+")"), i);

                setItemOnTick(new GuiItem(music.getType()).setDisplayName("\u00A7f"+music.getName()), 13);
            }
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

}
