package fr.dotmazy.dotplugin.commands;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.Main;
import fr.dotmazy.dotplugin.managers.PlayerManager;
import fr.dotmazy.dotplugin.utils.ItemBuilder;
import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class ModCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender.isOp() || (sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.lobby"))) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Seul un joueur peut executer cette commands !");
				return false;
			}
			
			Player player = (Player) sender;
			
			if(!player.hasPermission("moderation.mod")) {
				player.sendMessage("§cVous n'avez pas la permission d'executer cette commande !");
				return false;
			}
			
			if(PlayerManager.isInModerationMod(player)) {
				PlayerManager pm = PlayerManager.getFromPlayer(player);
				
				Main.getInstance().moderateurs.remove(player.getUniqueId());
				player.sendMessage("§cVous n'êtes à présent plus dans le mode modération");
				pm.giveInventory();
				pm.destroy();
				if(player.getGameMode()!=GameMode.CREATIVE) {
					player.setAllowFlight(false);
					player.setFlying(false);}
				return false;
			}
			
			PlayerManager pm = new PlayerManager(player);
			pm.init();
			
			Main.getInstance().moderateurs.add(player.getUniqueId());
			pm.saveInventory();
			player.sendMessage("§aVous êtes à présent dans le mode modération");
			player.setAllowFlight(true);
			player.setFlying(true);
			
			ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§eVoir l'inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir son inventaire.");
			ItemBuilder reports = new ItemBuilder(Material.BOOK).setName("§6Voir les signalements").setLore("§7Clique droit sur un joueur", "§7pour voir ses signalements.");
			ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§bFreeze").setLore("§7Clique droit sur un joueur", "§7pour le freeze.");
			ItemBuilder kbTester = new ItemBuilder(Material.STICK).setName("§dTest de recul").setLore("§7Clique gauche sur un joueur", "§7pour tester son recul.").addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
			ItemBuilder killer = new ItemBuilder(Material.BLAZE_ROD).setName("§dTueur de joueur").setLore("§7Clique droit sur un joueur", "§7pour le tuer.");
			ItemBuilder tpRandom = new ItemBuilder(Material.ARROW).setName("§aTéléportation aléatoire").setLore("§7Clique droit pour se téléporter", "§7aléatoirement sur un joueur.");
			ItemBuilder vanish = new ItemBuilder(Material.BLAZE_POWDER).setName("§aVanish").setLore("§7Clique droit pour activer/désactiver", "§7le vanish.");
			
			player.getInventory().setItem(0, invSee.toItemStack());
			player.getInventory().setItem(1, reports.toItemStack());
			player.getInventory().setItem(2, freeze.toItemStack());
			player.getInventory().setItem(3, kbTester.toItemStack());
			player.getInventory().setItem(4, killer.toItemStack());
			player.getInventory().setItem(5, tpRandom.toItemStack());
			player.getInventory().setItem(6, vanish.toItemStack());
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		
		return false;
	}

}
