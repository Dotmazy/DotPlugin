package fr.dotmazy.dotplugin.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import fr.dotmazy.dotplugin.Main;

public class CoinUnit {

	public static long getCoins(UUID uuid) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("SELECT * FROM players WHERE uuid = ?");
			s.setString(1, uuid.toString());
			ResultSet rs = s.executeQuery();
			if(rs.next()) {
				return rs.getLong("coins");
			}
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void setCoins(UUID uuid, long coins) {
		try {Connection c = Main.getInstance().getConnection();
			PreparedStatement s = c.prepareStatement("UPDATE players SET coins = ? WHERE uuid = ?");
			s.setLong(1, coins);
			s.setString(2, uuid.toString());
			s.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addCoins(UUID uuid, long coins) {
		setCoins(uuid, getCoins(uuid)+coins);
	}
	
	public static void removeCoins(UUID uuid, long coins) {
		setCoins(uuid, getCoins(uuid)-coins);
	}
	
}
