package fr.dotmazy.dotplugin;

import fr.dotmazy.dotplugin.api.Api;
import fr.dotmazy.dotplugin.api.TextApi;
import fr.dotmazy.dotplugin.commands.*;
import fr.dotmazy.dotplugin.configs.ConfigFile;
import fr.dotmazy.dotplugin.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class DotPlugin extends JavaPlugin {

    /** Don't use this (use api) <p></p>*/
    public static List<String> perms = new ArrayList<String>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> mutePlayers = new ArrayList<Player>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> freezePlayers = new ArrayList<Player>();
    /** Don't use this (use api) <p></p>*/
    public static Map<Player, ItemStack[]> modPlayers = new HashMap<Player, ItemStack[]>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> vanishedPlayers = new ArrayList<Player>();
    /** Don't use this (use api) <p></p>*/
    public static List<Player> adminChatPlayers = new ArrayList<Player>();
    /** Don't use this (use api) <p></p>*/
    public static HashMap<String,ConfigFile> files = new HashMap<String,ConfigFile>();

    @Override
    public void onEnable() {
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

    @Override
    public void onDisable() { saveAll(); }

    private void registerCommands() {
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
        registerFile("ranks",
                new Object[]{"admin.prefix", "&c[Admin] &r"},
                new Object[]{"admin.suffix", ""},
                new Object[]{"admin.permissions", new String[]{"*"}}
        );
        registerFile("players");
        registerFile("worlds");
        registerFile("items",
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.material", "diamond"},
                new Object[]{"example.lore", new String[]{"&7Example lore 1","&7Example lore 2"}},
                new Object[]{"example.enchants.sharpness", 3},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"},
                new Object[]{"example.name", "&2Example"}
        );
        registerFile("hypixelitems");
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
            dotPlugin.reloadConfig();
            dotPlugin.getPluginLoader().disablePlugin(dotPlugin);
            dotPlugin.getPluginLoader().enablePlugin(dotPlugin);
            return true;
        }catch (Exception e){return false;}
    }



    private void registerEvent(Listener event){
        Bukkit.getServer().getPluginManager().registerEvents(event, this);
    }

    private void registerCommand(String name, CommandExecutor executor){

        try{getServer().getPluginCommand(name).setExecutor(executor);}catch(Exception ignore) {}
        try{Objects.requireNonNull(getServer().getPluginCommand(name)).setTabCompleter((TabCompleter)executor);}catch(Exception ignored){}
    }

    private ConfigFile registerFile(String fileName, Object[]... defaults){
        ConfigFile file = new ConfigFile(fileName);
        file.setup();

        for (Object[] def : defaults) {
            String path = (String) def[0];
            Object value = def[1];
            file.get().addDefault(path, value);
        }

        file.get().options().copyDefaults(true);
        file.save();

        files.put(fileName,file);
        return files.get(fileName);
    }

}
