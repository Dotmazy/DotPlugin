package fr.dotmazy.dotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.dotmazy.dotplugin.Main;

public class Translate {
	
	public static String translateConfigText(String pName, int duration, String unit, String reason, String configText) {
		String translateText = Main.getInstance().getConfig().getString(configText);
		translateText = translateText.replace("&&", "§");
		translateText = translateText.replace("//e", "é");
		translateText = translateText.replace("//a", "à");
		translateText = translateText.replace("//E", "É");
		translateText = translateText.replace("%online_player%", Main.getInstance().getServer().getOnlinePlayers().size()+"");
		translateText = translateText.replace("%ban_reason%", reason);
		if(pName!=null) translateText = translateText.replace("%player_name%", pName);
		if(unit!="Permanent") {
			translateText = translateText.replace("%ban_duration%", duration+" "+unit);
		}else {
			translateText = translateText.replace("%ban_duration%", unit);
		}
		return translateText;
	}
	
	public static String translateConfigText(Player player, String configText) {
		try {
			return translateText(null, player, Main.getInstance().getConfig().getString(configText));
		}catch(Exception e) { return "Error";}
	}
	
	public static String translateConfigText(Location loc, Player player, String configText) {
		try {
			return translateText(loc, player, Main.getInstance().getConfig().getString(configText));
		}catch(Exception e) {return "Error";}
	}
	
	public static String translateConfigText(Player player, Event e, String configText) {
		try {
			return translateText(player, e, Main.getInstance().getConfig().getString(configText));
		}catch(Exception er) {return "Error";}
	}
	
	public static String translateConfigText(String pName, String configText, String rankName, String rankPrefix, int rankPower) {
		String translateText = Main.getInstance().getConfig().getString(configText);
		translateText = translateText.replace("&&", "§");
		translateText = translateText.replace("//e", "é");
		translateText = translateText.replace("//a", "à");
		translateText = translateText.replace("//E", "É");
		translateText = translateText.replace("%online_player%", Main.getInstance().getServer().getOnlinePlayers().size()+"");
		if(rankName!=null) translateText = translateText.replace("%rank_name%", rankName);
		translateText = translateText.replace("%rank_power%", String.valueOf(rankPower));
		if(pName!=null) translateText = translateText.replace("%player_name%", pName);
		if(rankPrefix!=null) translateText = translateText.replace("%rank_prefix%", ChatColor.translateAlternateColorCodes('&', rankPrefix));
		return translateText;
	}
	
	public static String translateText(Player player, String text) {
		return translateText(null, player, text);
	}
	
	public static String translateText(Player player, Event e, String text) {
		String translateText = text;
		translateText = translateText.replace("%event_message%", ChatColor.translateAlternateColorCodes('&', ((AsyncPlayerChatEvent)e).getMessage()));
		return translateText(null, player, translateText);
	}
	
	public static String translateText(Location loc, Player player, String text) {
		String translateText = text;
		translateText = translateText.replace("&&", "§");
		translateText = translateText.replace("//e", "é");
		translateText = translateText.replace("//E", "É");
		translateText = translateText.replace("%max_player%", Main.getInstance().getServer().getMaxPlayers()+"");
		translateText = translateText.replace("%online_player%", Bukkit.getOnlinePlayers().size()+"");
		if(player==null) return translateText;
		translateText = translateText.replace("%player_name%", player.getName());
		translateText = translateText.replace("%player_gamemode%", player.getGameMode().toString().toLowerCase());
		translateText = translateText.replace("%player_world%", player.getWorld().getName());
		translateText = translateText.replace("%player_coins%", transformMoney(CoinUnit.getCoins(player.getUniqueId())));
		try {translateText = translateText.replace("%player_prefix%", ChatColor.translateAlternateColorCodes('&', RankUnit.getPrefix(player))+"§r");}catch(Exception e) {}
		try {translateText = translateText.replace("%player_nick_name%", Main.getInstance().getDatabaseManager().getNickName(player.getUniqueId()));}catch(Exception e) {}
		translateText = translateText.replace("%player_location%", player.getLocation().getX()+" "+player.getLocation().getY()+" "+player.getLocation().getZ());
		translateText = translateText.replace("%player_rotation%", player.getLocation().getYaw()+" "+player.getLocation().getPitch());
		if(loc==null) return translateText;
		translateText = translateText.replace("%loc_world%", loc.getWorld().getName());
		translateText = translateText.replace("%loc_location%", loc.getX()+" "+loc.getY()+" "+loc.getZ());
		translateText = translateText.replace("%loc_rotation%", loc.getYaw()+" "+loc.getPitch());
		return translateText;
	}
	
	public static String transformMoney(long coins) {
		int r = Integer.valueOf(String.valueOf(coins));
		String v = "";
		if(coins>999999999) {
			v="b";
			r=r/1000000000;
		}else if(coins>999999){
			v="m";
			r=r/1000000;
		}else if(coins>999) {
			v="k";
			r=r/1000;
		}
		return r+v;
	}

	public static String translateConfigText(String pName, String pNick, String configText) {
		String translateText = Main.getInstance().getConfig().getString(configText);
		translateText = translateText.replace("&&", "§");
		translateText = translateText.replace("//e", "é");
		translateText = translateText.replace("//a", "à");
		translateText = translateText.replace("//E", "É");
		translateText = translateText.replace("%online_player%", Main.getInstance().getServer().getOnlinePlayers().size()+"");
		translateText = translateText.replace("%player_name%", pName);
		translateText = translateText.replace("%player_nickname%", pNick);
		return translateText;
	}
	
	public static String translateConfigText(String pName, String pPrefix, String message, String configText) {
		String translateText = Main.getInstance().getConfig().getString(configText);
		translateText = translateText.replace("&&", "§");
		translateText = translateText.replace("//e", "é");
		translateText = translateText.replace("//a", "à");
		translateText = translateText.replace("//E", "É");
		translateText = translateText.replace("%online_player%", Main.getInstance().getServer().getOnlinePlayers().size()+"");
		translateText = translateText.replace("%player_name%", pName);
		translateText = translateText.replace("%player_prefix%", pPrefix);
		translateText = translateText.replace("%message%", message);
		return translateText;
	}

}
