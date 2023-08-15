package fr.dotmazy.dotplugin;

import fr.dotmazy.dotplugin.old.api.TextApi;
import fr.dotmazy.dotplugin.commands.*;
import fr.dotmazy.dotplugin.util.ConfigFile;
import fr.dotmazy.dotplugin.listeners.*;
import fr.dotmazy.dotplugin.util.Api;
import fr.dotmazy.dotplugin.util.Database;
import fr.dotmazy.dotplugin.util.gui.*;
import fr.dotmazy.dotplugin.util.music.PlayedMusic;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class DotPlugin extends JavaPlugin {
    private static DotPlugin INSTANCE;
    //private final DiscordSRVListener discordSRVListener = new DiscordSRVListener(this);

    public static List<Player> mutePlayers = new ArrayList<>();
    public static List<Player> freezePlayers = new ArrayList<>();
    public static Map<Player, ItemStack[]> modPlayers = new HashMap<>();
    public static List<Player> vanishedPlayers = new ArrayList<>();
    public static List<Player> adminChatPlayers = new ArrayList<>();
    public static Map<AbstractGui,TickEvent> tickEvents = new HashMap<>();
    public static Map<Player,PlayedMusic> playMusics = new HashMap<>();
    public static Map<Player,UUID> autoMusicPlayers = new HashMap<>();

    public void onEnable() {
        System.out.println("-----------------------------Enabling DotPlugin-------------------------------");
        INSTANCE = this;
        //DiscordSRV.api.subscribe(discordSRVListener);

        saveDefaultConfig();
        TextApi.init(this);

        registerCommands();
        registerEvents();
        registerTickEvent();

        if(Database.getConnection()!=null && Database.initDatabase()) System.out.println("Database connected and initialized successfully !");

        for(Player player : Bukkit.getOnlinePlayers()) Api.Player.updatePerms(player);
        for(ConfigFile config : ConfigFile.getConfigs()) config.setup();
    }

    public void onDisable() {
        //DiscordSRV.api.unsubscribe(discordSRVListener);
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

        Bukkit.getLogger().addHandler(new LogEvent(this));
    }

    public static void saveAll(){
        for(ConfigFile config : ConfigFile.getConfigs()) config.save();
    }

    public static boolean reload(){
        try {
            for(ConfigFile file : ConfigFile.getConfigs()) file.reload();
            getInstance().reloadConfig();
            getInstance().getPluginLoader().disablePlugin(getInstance());
            getInstance().getPluginLoader().enablePlugin(getInstance());
            return true;
        }catch (Exception e){return false;}
    }

    private void registerTickEvent() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, ()->{
            for(AbstractGui gui : tickEvents.keySet()) {
                TickEvent event = tickEvents.get(gui);
                for(Player player : Bukkit.getOnlinePlayers())
                    if(event.isActive(player))
                        event.getEvent().run(player);
            }
            for(Player player : playMusics.keySet())
                if(playMusics.get(player)!=null){
                    PlayedMusic playedMusic = playMusics.get(player);
                    playMusics.replace(player,playedMusic.addTime(1));
                }
        }, 1, 0);
    }



    public void registerEvent(Listener event){ Bukkit.getServer().getPluginManager().registerEvents(event, this); }

    private void registerCommand(String name, CommandExecutor executor){
        try{getServer().getPluginCommand(name).setExecutor(executor);}catch(Exception ignore) {}
        try{
            Objects.requireNonNull(getServer().getPluginCommand(name)).setTabCompleter((TabCompleter)executor);}catch(Exception ignored){}
    }

    public static DotPlugin getInstance(){
        return INSTANCE;
    }

}