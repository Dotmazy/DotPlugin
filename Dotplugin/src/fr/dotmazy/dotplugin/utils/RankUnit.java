package fr.dotmazy.dotplugin.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;

public class RankUnit {
	
	public static void createRank(String name, int power, String prefix, boolean def) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("INSERT INTO ranks (power,name,prefix,def) VALUES (?, ?, ?, ?)");
			s.setInt(1, power);
			s.setString(2, name);
			s.setString(3, prefix);
			s.setBoolean(4, def);
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setRankPrefix(String prefix, String name) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("UPDATE ranks SET prefix = ? WHERE name = ?");
			s.setString(1, prefix);
			s.setString(2, name);
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setRankPower(int power, String name) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("UPDATE ranks SET power = ? WHERE name = ?");
			s.setInt(1, power);
			s.setString(2, name);
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteRank(String name) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("DELETE FROM ranks WHERE name = ?");
			s.setString(1, name);
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void joinRank(UUID uuid, String name) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("UPDATE players SET rank = ? WHERE uuid = ?");
			s.setString(1, name);
			s.setString(2, uuid.toString());
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getPower(String name) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM ranks WHERE name = ?");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			if(rs.next()) {
				return rs.getInt("power");
			}
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getPrefix(String name) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM ranks WHERE name = ?");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			if(rs.next()) {
				return rs.getString("prefix");
			}
			return null;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPrefix(Player player) {
		return getPrefix(Main.getInstance().getDatabaseManager().getRank(player.getUniqueId()));
	}
	
	public static Map<String,String> getRanks() {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM ranks");
			ResultSet rs = s.executeQuery();
			Map<String,String> r = new HashMap<>();
			while(rs.next()) {
				r.put(rs.getString("name"),rs.getString("prefix"));
			}
			return r;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getRank() {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM ranks");
			ResultSet rs = s.executeQuery();
			List<String> r = new ArrayList<>();
			while(rs.next()) {
				r.add(rs.getString("name"));
			}
			return r;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean exist(String name) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM ranks WHERE name = ?");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			return rs.next();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
