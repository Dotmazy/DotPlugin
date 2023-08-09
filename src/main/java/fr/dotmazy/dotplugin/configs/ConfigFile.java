package fr.dotmazy.dotplugin.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigFile {

    private File file;
    private FileConfiguration configFile;
    private final String fileName;

    public ConfigFile(String fileName){
        this.fileName = fileName;
    }

    public void setup(){
        this.file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("DotPlugin")).getDataFolder(),this.fileName+".yml");

        if (!this.file.exists()){
            try {
                this.file.createNewFile();
            } catch (IOException ignored) {}
        }
        this.configFile = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration get(){
        return this.configFile;
    }

    public void save(){
        try {
            this.configFile.save(this.file);
        } catch (IOException ignored) {
            System.out.println("Couldn't save rank file !");
        }
    }

    public void reload(){
        this.configFile = YamlConfiguration.loadConfiguration(this.file);
    }

}
