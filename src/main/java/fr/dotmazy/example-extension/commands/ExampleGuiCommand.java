package fr.dotmazy.example_extension.commands;

import fr.dotmazy.example_extension.Main;
import fr.dotmazy.example_extension.gui.ExampleGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player){
            Inventory inv = new MusicGui().getMainInventory();
            if(inv!=null) player.openInventory(inv);
        }
        return true;
    }

}
