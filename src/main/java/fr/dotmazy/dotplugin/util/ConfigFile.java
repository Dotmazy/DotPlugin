package fr.dotmazy.dotplugin.util;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigFile {

    public static ConfigFile items = new ConfigFile("items");


    private static List<ConfigFile> configs;

    private File file;
    private FileConfiguration configFile;
    private final String filePath;

    public ConfigFile(String filePath){
        if(configs==null) configs = new ArrayList<>();
        this.filePath = filePath;
        configs.add(this);
    }

    public void setup(){
        try {
            this.file = new File(DotPlugin.getInstance().getDataFolder(),this.filePath+".yml");
            if (!this.file.exists()){
                this.file.createNewFile();

                StringBuilder read = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(DotPlugin.getInstance().getResource(filePath+".yml")));
                String line;
                while ((line = br.readLine()) != null){
                    read.append(line).append("\n");
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
                bw.write(read.toString());
            }
            this.configFile = YamlConfiguration.loadConfiguration(this.file);
        } catch (IOException e) {
            throw new Error("Can't load file "+filePath);
        }
    }

    public FileConfiguration get(){
        return this.configFile;
    }

    public void save(){
        try {
            this.configFile.save(this.file);
        } catch (IOException e) {
            System.out.println("Couldn't save file "+filePath);
        }
    }

    public void reload(){
        this.configFile = YamlConfiguration.loadConfiguration(this.file);
    }

    public static List<ConfigFile> getConfigs(){
        return configs;
    }

}
