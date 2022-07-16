package fr.dotmazy.dotplugin.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import fr.dotmazy.dotplugin.Main;

public class DatabaseManager {

	private DbConnection dbConnection;
	
	private FileConfiguration conf = Main.getInstance().getConfig();
	
	public DatabaseManager() {
		this.dbConnection = new DbConnection(new DbCredentials(conf.getString("database.host"), conf.getString("database.user"), conf.getString("database.password"), conf.getString("database.databasename"), conf.getInt("database.port")));
		
	}
	
	public void close() {
		try {
			this.dbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public DbConnection getDbConnection() {
		return dbConnection;
	}
	
	public void update(String qry) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(qry);
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean exist(String name) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM players WHERE name = '" + name + "'");
			ResultSet rs = s.executeQuery();
			return rs.next();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean exist(UUID uuid) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM players WHERE uuid = '" + uuid.toString() + "'");
			return s.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public UUID getUUID(String name) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM players WHERE name = '" + name + "'");
			ResultSet rs = s.executeQuery();
			if(rs.next()) {
				return UUID.fromString(rs.getString("uuid"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getRank(UUID uuid) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM players WHERE uuid = '" + uuid.toString() + "'");
			ResultSet rs = s.executeQuery();
			if(rs.next()) {
				return rs.getString("rank");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getNickName(UUID uuid) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM players WHERE uuid = '" + uuid.toString() + "'");
			ResultSet rs = s.executeQuery();
			if(rs.next()) {
				return rs.getString("nickname");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setNickName(UUID uuid, String nickname) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement("UPDATE players SET nickname = ? WHERE uuid = '" + uuid.toString() + "'");
			s.setString(1, nickname);
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected(UUID uuid) {
		try {Connection c = dbConnection.getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM players WHERE uuid = '" + uuid.toString() + "'");
			ResultSet rs = s.executeQuery();
			if(rs.next()) {
				return rs.getBoolean("isConnected");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
