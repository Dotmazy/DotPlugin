package fr.dotmazy.dotplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dotmazy.dotplugin.utils.Permissions;
import fr.dotmazy.dotplugin.utils.Translate;

public class CalcCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()||(sender instanceof Player&&Permissions.hasPermission(((Player)sender).getName(), "dotplugin.calc"))) {
			if(args.length>0) {
				if(test("d", args, sender)) {
					return true;
				}else if(test("mu", args, sender)) {
					return true;
				}else if(test("mo", args, sender)) {
					return true;
				}else if(test("p", args, sender)) {
					return true;
				}else {
					sender.sendMessage("§cErreur dans votre calcul !");
				}
				
			}else {
				sender.sendMessage("§c/calc <calcul>");
				sender.sendMessage("§cp = +");
				sender.sendMessage("§cmo = -");
				sender.sendMessage("§cd = /");
				sender.sendMessage("§cmu = *");
			}
		}else {
			sender.sendMessage(Translate.translateConfigText(null, "commands.error_not_permission"));
		}
		return false;
	}
	
	private boolean test(String calc, String[] args, CommandSender sender) {
		try {
			String[] s;
			s=args[0].split(calc);
			if(s.length>1) {
				sender.sendMessage(args[0].replace("d", "/").replace("mo", "-").replace("mu", "*").replace("p", "+")+" = "+String.valueOf(calc(calc, s)));
				return true;
			}
		}catch(Exception e) {
		}
		return false;
	}
	
	private int calc(String calc, String[] a) {
		int r=Integer.valueOf(a[0]);
		int i = 0;
		if(calc.equals("p")) {
			for(String as:a) {
				if(i>0) r=r+Integer.valueOf(as);
				i++;
			}
			return r;
		}
		if(calc.equals("mo")) {
			for(String as:a) {
				if(i>0) r=r-Integer.valueOf(as);
				i++;
			}
			return r;
		}
		if(calc.equals("mu")) {
			for(String as:a) {
				if(i>0) r=r*Integer.valueOf(as);
				i++;
			}
			return r;
		}
		if(calc.equals("d")) {
			for(String as:a) {
				if(i>0) r=r/Integer.valueOf(as);
				i++;
			}
			return r;
		}
		return 0;
	}
	
	//3*2  -  5+7*6

}
