package fr.dotmazy.dotplugin.bans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.utils.TimeUnit;

public class BanManager {

	public void ban(UUID uuid, long endInSeconds, String reason) {
		if(isBanned(uuid)) return;
		
		long endToMillis = endInSeconds * 1000;
		long end = endToMillis + System.currentTimeMillis();
		
		if(endInSeconds == -1) {
			end = -1;
		}
		
		try {
			PreparedStatement sts = Main.getInstance().getConnection().prepareStatement("INSERT INTO bans (uuid, end, reason) VALUES (?, ?, ?)");
			sts.setString(1, uuid.toString());
			sts.setLong(2, end);
			sts.setString(3, reason);
			sts.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(Bukkit.getPlayer(uuid) != null) {
			Player target = Bukkit.getPlayer(uuid);
			target.kickPlayer("§cVous avez été banni !\n " +
							  "\n " +
							  "§6Raison : §f" + reason + "\n " +
							  "\n " +
							  "§aTemp restant : §f" + getTimeLeft(uuid));
		}
	}
	
	public void unban(UUID uuid) {
		if(!isBanned(uuid)) return;
		
		try {
			PreparedStatement sts = Main.getInstance().getConnection().prepareStatement("DELETE FROM bans WHERE uuid = ?");
			sts.setString(1, uuid.toString());
			sts.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isBanned(UUID uuid) {
		try {
			PreparedStatement sts = Main.getInstance().getConnection().prepareStatement("SELECT * FROM bans WHERE uuid = ?");
			sts.setString(1, uuid.toString());
			ResultSet rs = sts.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void checkDuration(UUID uuid) {
		if(!isBanned(uuid)) return;
		
		if(getEnd(uuid) == -1) return;
		
		if(getEnd(uuid) < System.currentTimeMillis()) {
			unban(uuid);
		}
	}
	
	public long getEnd(UUID uuid) {
		if(!isBanned(uuid)) return 0;
		
		try {
			PreparedStatement sts = Main.getInstance().getConnection().prepareStatement("SELECT * FROM bans WHERE uuid = ?");
			sts.setString(1, uuid.toString());
			ResultSet rs = sts.executeQuery();
			if(rs.next()) {
				return rs.getLong("end");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getTimeLeft(UUID uuid) {
		if(!isBanned(uuid)) return "§cNon banni";
		
		if(getEnd(uuid)==-1) {
			return "§cPermanent";
		}
		
		long tempsRestant = (getEnd(uuid) - System.currentTimeMillis()) / 1000;
		int mois = 0;
		int jours = 0;
		int heures = 0;
		int minutes = 0;
		int secondes = 0;
		
		while(tempsRestant >= TimeUnit.MOIS.getToSecond()) {
			mois++;
			tempsRestant -= TimeUnit.MOIS.getToSecond();
		}
		
		while(tempsRestant >= TimeUnit.JOUR.getToSecond()) {
			jours++;
			tempsRestant -= TimeUnit.JOUR.getToSecond();
		}
		
		while(tempsRestant >= TimeUnit.HEURE.getToSecond()) {
			heures++;
			tempsRestant -= TimeUnit.HEURE.getToSecond();
		}
		
		while(tempsRestant >= TimeUnit.MINUTE.getToSecond()) {
			minutes++;
			tempsRestant -= TimeUnit.MINUTE.getToSecond();
		}
		
		while(tempsRestant >= TimeUnit.SECONDE.getToSecond()) {
			secondes++;
			tempsRestant -= TimeUnit.SECONDE.getToSecond();
		}
		
		return mois + " " + TimeUnit.MOIS.getName() + "(S) " + jours + " " + TimeUnit.JOUR.getName() + "(S) " + heures + " " + TimeUnit.HEURE.getName() + "(S) " + minutes + " " + TimeUnit.MINUTE.getName() + "(S) " + secondes + " " + TimeUnit.SECONDE.getName() + "(S)";
	}
	
	public String getReason(UUID uuid) {
		if(!isBanned(uuid)) return "§cNon banni";
		
		try {
			PreparedStatement sts = Main.getInstance().getConnection().prepareStatement("SELECT * FROM bans WHERE uuid = ?");
			sts.setString(1, uuid.toString());
			ResultSet rs = sts.executeQuery();
			if(rs.next()) {
				return rs.getString("reason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "§cNon banni";
	}
	
}
