package com.example.examplemod.command;

import com.example.examplemod.gui.ExampleGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ExampleGuiCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player){
            Inventory inv = new ExampleGui().getInventory();
            player.openInventory(inv);
        }

        return true;
    }

}