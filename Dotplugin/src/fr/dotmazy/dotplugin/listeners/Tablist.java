package fr.dotmazy.dotplugin.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.core.Coin;
import fr.dotmazy.dotplugin.utils.Translate;
import net.md_5.bungee.api.ChatColor;

public class Tablist {

	@SuppressWarnings("deprecation")
	public static void onTab() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				if(Main.getInstance().getConfig().getBoolean("config.allow_plugin_tablist")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						try {
							p.setPlayerListName(Translate.translateConfigText(p, "config.tablist.format"));
						}catch(Exception e) {
							p.setPlayerListName("[ERROR] " + p.getName());
						}
					}
				}
			}
		}, 0, 3);
	}
	
	public static void createBoard(Player player) {
		if(!Main.getInstance().getConfig().getBoolean("config.allow_plugin_scoreboard")) {
			return;
		}
    	ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("HubScoreboard-1","dummy",ChatColor.translateAlternateColorCodes('&', "&a&1<< &2&lDotServer &a&1>>"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        List<String> l = Main.getInstance().getConfig().getStringList("config.scoreboard");
        int i=l.size();
        for(String line : l) {
        	Score s = obj.getScore(Translate.translateText(player, line));
        	s.setScore(i);
        	i--;
        }
        player.setScoreboard(board);
	}
	
}
