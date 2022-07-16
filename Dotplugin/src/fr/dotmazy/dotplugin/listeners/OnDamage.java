package fr.dotmazy.dotplugin.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnDamage implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Arrow) {
			Arrow s = (Arrow) e.getDamager();
			
			if(s.getShooter() instanceof Player) {
				@SuppressWarnings("unused")
				Player shooter = (Player) s.getShooter();
				//if(shooter.getItemInHand().getType() == Material.NETHERITE_HOE) {
					//e.setDamage(10.0);
				//}
			}
		}
	}

}
