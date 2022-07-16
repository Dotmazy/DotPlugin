package fr.dotmazy.dotplugin.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.mysql.DatabaseManager;
import fr.dotmazy.dotplugin.mysql.DbConnection;
import fr.dotmazy.dotplugin.utils.Translate;

public class OnPlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage("");
		final UUID uuid = event.getPlayer().getUniqueId();
		final DbConnection dbConnection = Main.getInstance().getDatabaseManager().getDbConnection();
		
		Tablist.createBoard(event.getPlayer());
		Tablist.onTab();
		
		try {Location lobby = new Location(
					Bukkit.getWorld(Main.getInstance().getConfig().getString("donne.lobby.loc.world")),
					Main.getInstance().getConfig().getDouble("donne.lobby.loc.x"),
					Main.getInstance().getConfig().getDouble("donne.lobby.loc.y"),
					Main.getInstance().getConfig().getDouble("donne.lobby.loc.z"),
					(float) Main.getInstance().getConfig().getDouble("donne.lobby.loc.yaw"),
					(float) Main.getInstance().getConfig().getDouble("donne.lobby.loc.pitch")
					);
					event.getPlayer().teleport(lobby);}catch(Exception e) {}
		
		Bukkit.broadcastMessage(Translate.translateConfigText(event.getPlayer(), "config.joinmsg"));
		
		try {
			final Connection connection = dbConnection.getConnection();
			final PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, rank, name FROM players WHERE uuid = ?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				final String rank = resultSet.getString("rank");
				
				final PreparedStatement pS = connection.prepareStatement("UPDATE players SET lastconnection = ?, isConnected = ? WHERE uuid = ?");
				pS.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				pS.setBoolean(2, true);
				pS.setString(3, uuid.toString());
				pS.executeUpdate();
				
				Main.getInstance().getPlayerGrades().put(uuid, rank);
			}else {
				createUserRank(connection, uuid, event.getPlayer().getName());
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createUserRank(Connection connection, UUID uuid, String name) {
		
		try {
			final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players(uuid,name,rank,firstconnection,lastconnection,isConnected) VALUES (?, ?, ?, ?, ?, ?)");
			
			preparedStatement.setString(1, uuid.toString());
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, "Membre");
			preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setBoolean(6, true);
			preparedStatement.executeUpdate();
		
			Main.getInstance().getPlayerGrades().put(uuid, "Membre");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		try {
			final DbConnection dbConnection = Main.getInstance().getDatabaseManager().getDbConnection();
			final Connection connection = dbConnection.getConnection();
			final PreparedStatement pS = connection.prepareStatement("UPDATE players SET isConnected = ? WHERE uuid = ?");
			pS.setBoolean(1, false);
			pS.setString(2, e.getPlayer().getUniqueId().toString());
			pS.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		if(Main.getInstance().banManager.isBanned(player.getUniqueId())) {
			Main.getInstance().banManager.checkDuration(player.getUniqueId());
			
			e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
			e.setKickMessage("§cVous êtes banni !\n " +
							  "\n " +
							  "§6Raison : §f" + Main.getInstance().banManager.getReason(player.getUniqueId()) + "\n " +
							  "\n " +
							  "§aTemp restant : §f" + Main.getInstance().banManager.getTimeLeft(player.getUniqueId()));
		}
	}
	
}
