package fr.dotmazy.example_extension;

import fr.dotmazy.example_extension.commands.*;
import org.bukkit.Bukkit;
import java.util.*;

public class Main extends JavaPlugin {
  
    public void onEnable() {
        System.out.println("Enabling Example DotExtension");

        getServer().getPluginCommand("examplegui").setExecutor(new ExampleGuiCommand());
    }

}
