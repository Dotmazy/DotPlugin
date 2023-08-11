package fr.dotmazy.dotplugin;

import fr.dotmazy.dotplugin.api.TextApi;
import fr.dotmazy.dotplugin.commands.*;
import fr.dotmazy.dotplugin.configs.ConfigFile;
import fr.dotmazy.dotplugin.listeners.*;
import github.scarsz.discordsrv.DiscordSRV;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class DotPlugin extends JavaPlugin implements Listener {
    private static DotPlugin INSTANCE;
    private final DiscordSRVListener discordSRVListener = new DiscordSRVListener(this);

    /** Don't use this (use api) <p></p>*/
    public static List<String> perms = new ArrayList<>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> mutePlayers = new ArrayList<>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> freezePlayers = new ArrayList<>();
    /** Don't use this (use api) <p></p>*/
    public static Map<Player, ItemStack[]> modPlayers = new HashMap<>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> vanishedPlayers = new ArrayList<>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> adminChatPlayers = new ArrayList<>();
    /** Don't use this (use api) <p></p>*/
    public static HashMap<String,ConfigFile> files = new HashMap<>();

    public void onEnable() {
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(this, this);
        DiscordSRV.api.subscribe(discordSRVListener);

        saveDefaultConfig();
        TextApi.init(this);
        registerConfigFiles();
        registerCommands();
        registerEvents();
        Bukkit.getLogger().addHandler(new LogEvent(this));

        System.out.println(files);
        for (String key : files.get("players").get().getKeys(false)){
            if(files.get("players").get().getBoolean(key+".mute")) mutePlayers.add(Bukkit.getPlayer(UUID.fromString(key)));
            if(files.get("players").get().getBoolean(key+".freeze")) freezePlayers.add(Bukkit.getPlayer(UUID.fromString(key)));
        }
    }

    public void onDisable() {
        DiscordSRV.api.unsubscribe(discordSRVListener);
        saveAll();
    }

    private void registerCommands() {
        registerCommand("guitest", new ExampleGuiCommand());

        registerCommand("rank", new RankCommand(this));
        registerCommand("mute", new MuteCommand(this));
        registerCommand("unmute", new UnmuteCommand(this));
        registerCommand("freeze", new FreezeCommand(this));
        registerCommand("unfreeze", new UnfreezeCommand(this));
        registerCommand("spawn", new SpawnCommand(this));
        registerCommand("setspawn", new SetspawnCommand(this));
        registerCommand("lobby", new LobbyCommand(this));
        registerCommand("setlobby", new SetlobbyCommand(this));
        registerCommand("warp", new WarpCommand(this));
        registerCommand("setwarp", new SetwarpCommand(this));
        registerCommand("home", new HomeCommand(this));
        registerCommand("sethome", new SethomeCommand(this));
        registerCommand("music", new MusicCommand(this));
        registerCommand("gamemode", new GamemodeCommand(this));
        registerCommand("gms", new GmsCommand(this));
        registerCommand("gmc", new GmcCommand(this));
        registerCommand("gma", new GmaCommand(this));
        registerCommand("gmsp", new GmspCommand(this));
        registerCommand("mod", new ModCommand(this));
        registerCommand("vanish", new VanishCommand(this));
        registerCommand("craft", new CraftCommand(this));
        registerCommand("smelt", new SmeltCommand(this));
        registerCommand("enderchest", new EnderChestCommand(this));
        registerCommand("adminchat", new AdminChatCommand(this));
        registerCommand("customitems", new CustomItemsCommand(this));
        registerCommand("display", new DisplayCommand(this));
        registerCommand("hypixelitems", new HypixelItemsCommand(this));
        registerCommand("nick", new NickCommand(this));
    }

    private void registerEvents() {
        registerEvent(new EntityEvents(this));
        registerEvent(new PlayerEvents(this));
        registerEvent(new InventoryEvents());
        registerEvent(new BlockEvents(this));
    }

    private void registerConfigFiles() {
        registerFile("ranks")
                .addDefault("admin.prefix", "&c[Admin] &r")
                .addDefault("admin.suffix", "")
                .addDefault("admin.permissions", new String[]{"*"}).save();
        registerFile("players").save();
        registerFile("worlds").save();
        registerFile("items")
                .addDefault("example.name", "&2Example")
                .addDefault("example.material", "diamond")
                .addDefault("example.lore", new String[]{"&7Example lore 1","&7Example lore 2"})
                .addDefault("example.enchants.sharpness", 3)
                .addDefault("example.name", "&2Example")
                .save();
        registerFile("hypixelitems").save();
    }

    public static void saveAll(){
        for (Map.Entry<String, ConfigFile> entry : files.entrySet()) {
            ConfigFile value = entry.getValue();
            value.save();
        }
    }

    public static boolean reload(){
        try {
            Plugin dotPlugin = Bukkit.getPluginManager().getPlugin("DotPlugin");
            for(ConfigFile file : files.values()){ file.reload(); }
            dotPlugin.reloadConfig();
            dotPlugin.getPluginLoader().disablePlugin(dotPlugin);
            dotPlugin.getPluginLoader().enablePlugin(dotPlugin);
            return true;
        }catch (Exception e){return false;}
    }



    public void registerEvent(Listener event){ Bukkit.getServer().getPluginManager().registerEvents(event, this); }

    private void registerCommand(String name, CommandExecutor executor){
        try{getServer().getPluginCommand(name).setExecutor(executor);}catch(Exception ignore) {}
        try{
            Objects.requireNonNull(getServer().getPluginCommand(name)).setTabCompleter((TabCompleter)executor);}catch(Exception ignored){}
    }

    private ConfigFile registerFile(String fileName){
        ConfigFile file = new ConfigFile(fileName);
        file.setup();
        file.get().options().copyDefaults(true);
        files.put(fileName,file);
        return files.get(fileName);
    }

    public static DotPlugin getInstance(){ return INSTANCE; }

}