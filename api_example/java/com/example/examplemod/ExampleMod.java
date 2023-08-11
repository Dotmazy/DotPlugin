package com.example.examplemod;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.example.examplemod.commands.ExampleGuiCommand;

public class DotPlugin extends JavaPlugin {

    public void onEnable() {
        getServer().getPluginCommand("examplegui").setExecutor(new ExampleGuiCommand());
    }

}