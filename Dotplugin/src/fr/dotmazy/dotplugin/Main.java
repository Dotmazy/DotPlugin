package fr.dotmazy.dotplugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dotmazy.dotplugin.bans.BanManager;
import fr.dotmazy.dotplugin.commands.AdminChatCommand;
import fr.dotmazy.dotplugin.commands.AlertCommand;
import fr.dotmazy.dotplugin.commands.BansCommand;
import fr.dotmazy.dotplugin.commands.CalcCommand;
import fr.dotmazy.dotplugin.commands.CoinsCommand;
import fr.dotmazy.dotplugin.commands.DotReloadCommand;
import fr.dotmazy.dotplugin.commands.FlyCommand;
import fr.dotmazy.dotplugin.commands.FreezeCommand;
import fr.dotmazy.dotplugin.commands.GamemodeCommand;
import fr.dotmazy.dotplugin.commands.HealCommand;
import fr.dotmazy.dotplugin.commands.LobbyCommand;
import fr.dotmazy.dotplugin.commands.MessageCommand;
import fr.dotmazy.dotplugin.commands.ModCommand;
import fr.dotmazy.dotplugin.commands.MusicCommand;
import fr.dotmazy.dotplugin.commands.MuteCommand;
import fr.dotmazy.dotplugin.commands.NickCommand;
import fr.dotmazy.dotplugin.commands.RankCommand;
import fr.dotmazy.dotplugin.commands.ReportCommand;
import fr.dotmazy.dotplugin.commands.ReportViewCommand;
import fr.dotmazy.dotplugin.commands.SetLobbyCommand;
import fr.dotmazy.dotplugin.commands.SetSpawnCommand;
import fr.dotmazy.dotplugin.commands.SpawnCommand;
import fr.dotmazy.dotplugin.listeners.Tablist;
import fr.dotmazy.dotplugin.managers.EventManager;
import fr.dotmazy.dotplugin.managers.PlayerManager;
import fr.dotmazy.dotplugin.mysql.DatabaseManager;

public class Main extends JavaPlugin{
	
	private static Main instance;
	private DatabaseManager databaseManager;
	public BanManager banManager = new BanManager();
	
	public static ArrayList<Player> freeze = new ArrayList<Player>();
	public static ArrayList<Player> mute = new ArrayList<Player>();
	public static ArrayList<Player> adminchat = new ArrayList<Player>();
	public ArrayList<UUID> moderateurs = new ArrayList<>();
	
	public HashMap<UUID, String> playerGrades;
	public HashMap<UUID, PlayerManager> players =  new HashMap<>();
	
	
	public static Main getInstance() {
		return instance;
	}
	
	public final void onEnable() {
		saveDefaultConfig();
		instance = this;
		new EventManager().registers();
		databaseManager = new DatabaseManager();
		playerGrades = new HashMap<>();
		
		databaseManager.update("CREATE TABLE IF NOT EXISTS reports ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "name VARCHAR(255), "
				+ "uuid VARCHAR(255), "
				+ "date VARCHAR(255), "
				+ "auteur VARCHAR(255), "
				+ "raison VARCHAR(255)) ");
		databaseManager.update("CREATE TABLE IF NOT EXISTS players ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "uuid VARCHAR(255), "
				+ "name VARCHAR(255), "
				+ "nickname VARCHAR(255), "
				+ "rank VARCHAR(255), "
				+ "rankend BIGINT, "
				+ "coins BIGINT, "
				+ "isConnected BOOLEAN, "
				+ "firstconnection TIME, "
				+ "lastconnection TIME) ");
		databaseManager.update("CREATE TABLE IF NOT EXISTS ranks ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "power INT, "
				+ "name VARCHAR(255), "
				+ "prefix VARCHAR(255), "
				+ "def BOOLEAN) ");
		databaseManager.update("CREATE TABLE IF NOT EXISTS bans ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "uuid VARCHAR(255), "
				+ "end BIGINT, "
				+ "reason VARCHAR(255))");
		
		Tablist.onTab();
		System.out.println("[DotPlugin] Le plugin vien de s'allumer.");
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("lobby").setExecutor(new LobbyCommand());
		getCommand("setspawn").setExecutor(new SetSpawnCommand());
		getCommand("setlobby").setExecutor(new SetLobbyCommand());
		getCommand("freeze").setExecutor(new FreezeCommand());
		getCommand("mod").setExecutor(new ModCommand());
		getCommand("report").setExecutor(new ReportCommand());
		getCommand("reportview").setExecutor(new ReportViewCommand());
		getCommand("broadcast").setExecutor(new AlertCommand());
		getCommand("mute").setExecutor(new MuteCommand());
		getCommand("adminchat").setExecutor(new AdminChatCommand());
		getCommand("ranks").setExecutor(new RankCommand());
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("drl").setExecutor(new DotReloadCommand());
		getCommand("msg").setExecutor(new MessageCommand());
		getCommand("gamemode").setExecutor(new GamemodeCommand());
		getCommand("gmc").setExecutor(new GamemodeCommand());
		getCommand("gms").setExecutor(new GamemodeCommand());
		getCommand("gmsp").setExecutor(new GamemodeCommand());
		getCommand("gma").setExecutor(new GamemodeCommand());
		getCommand("musics").setExecutor(new MusicCommand());
		getCommand("coin").setExecutor(new CoinsCommand());
		getCommand("feed").setExecutor(new HealCommand());
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("ban").setExecutor(new BansCommand());
		getCommand("unban").setExecutor(new BansCommand());
		getCommand("nick").setExecutor(new NickCommand());
		getCommand("calc").setExecutor(new CalcCommand());
		
		getCommand("freeze").setTabCompleter(new FreezeCommand());
		getCommand("report").setTabCompleter(new ReportCommand());
		getCommand("mute").setTabCompleter(new MuteCommand());
		getCommand("ranks").setTabCompleter(new RankCommand());
		//getCommand("msg").setTabCompleter(new MessageCommand());
		getCommand("gamemode").setTabCompleter(new GamemodeCommand());
		getCommand("gmc").setTabCompleter(new GamemodeCommand());
		getCommand("gms").setTabCompleter(new GamemodeCommand());
		getCommand("gmsp").setTabCompleter(new GamemodeCommand());
		getCommand("gma").setTabCompleter(new GamemodeCommand());
		/*getCommand("musics").setTabCompleter(new MusicCommand());
		getCommand("coin").setTabCompleter(new CoinsCommand());*/
		getCommand("ban").setTabCompleter(new BansCommand());
		getCommand("unban").setTabCompleter(new BansCommand());
		getCommand("nick").setTabCompleter(new NickCommand());
		
		//getServer().getPluginManager().registerEvents(new PluginListener(this), this);
		//ranks.initScoreboard();
		if(!Bukkit.getOnlinePlayers().isEmpty())
			for(Player online:Bukkit.getOnlinePlayers()) {
				Tablist.createBoard(online);
			}
	}
	
	public HashMap<UUID, String> getPlayerGrades() {
		return playerGrades;
	}

	public void onDisable() {
		
		this.databaseManager.close();
		
	}

	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	public Connection getConnection() {
		try {
			return getDatabaseManager().getDbConnection().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
