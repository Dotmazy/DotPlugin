package fr.dotmazy.dotplugin;

import fr.dotmazy.dotplugin.api.DotPluginAddon;
import fr.dotmazy.dotplugin.discord.DiscordBot;
import fr.dotmazy.dotplugin.old.api.TextApi;
import fr.dotmazy.dotplugin.commands.*;
import fr.dotmazy.dotplugin.util.*;
import fr.dotmazy.dotplugin.listeners.*;
import fr.dotmazy.dotplugin.util.gui.*;
import fr.dotmazy.dotplugin.util.music.Music;
import fr.dotmazy.dotplugin.util.music.PlayedMusic;

import java.util.Objects;

import fr.dotmazy.dotplugin.util.raw.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Function;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DotPlugin extends JavaPlugin {
    private static DotPlugin INSTANCE;

    public static final List<Player> mutePlayers = new ArrayList<>();
    public static final List<Player> freezePlayers = new ArrayList<>();
    public static final Map<Player, ItemStack[]> modPlayers = new HashMap<>();
    public static final List<Player> vanishedPlayers = new ArrayList<>();
    public static final List<Player> adminChatPlayers = new ArrayList<>();
    public static final Map<AbstractGui,TickEvent> tickEvents = new HashMap<>();
    public static final Map<Player,PlayedMusic> playMusics = new HashMap<>();
    public static final List<Player> loginPlayers = new ArrayList<>();;
    public static final Map<Player, ChatInputFunction> chatInputPlayers = new HashMap<>();
    public static final Map<Player, Playlist> startedPlaylists = new HashMap<>();

    public void onEnable() {
        System.out.println("-----------------------------Enabling DotPlugin-------------------------------");
        INSTANCE = this;

        HttpServerUtils.startHTTPServer();

        saveDefaultConfig();
        TextApi.init(this);
        DiscordBot.init(this);
        Database.init();
        Music.init();

        registerCommands();
        registerEvents();
        registerTickEvent();
        registerConfig();

        System.out.println(Bukkit.getServer().getName());

        Bukkit.getPluginManager().registerEvents(new DotPluginAddon(),this);
        System.out.println("EVENT REGISTRED");
    }

    public void onDisable() {
        saveAll();
        HttpServerUtils.stopServer();
        System.out.println(Database.disconnect());
    }



    private void registerCommands() {
        registerCommand("guitest", new ExampleGuiCommand());
        registerCommand("lua", new LuaCommand());
        registerCommand("js", new JsCommand());
        registerCommand("login", new LoginCommand());
        registerCommand("mobgenerator", new MobGeneratorCommand(this));

        registerCommand("database", new DatabaseCommand(this));
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

        LogManager.getLogManager().reset();

        Logger.getLogger("Bukkit").addHandler(new LogEvent(this));
    }

    private void registerConfig() {
        for(ConfigFile config : ConfigFile.getConfigs()) config.setup();
        for(Player player : Bukkit.getOnlinePlayers()) {
            //fr.dotmazy.dotplugin.util.Player.fromPlayer(player).loadAutoMusics(false);
            Api.Player.updatePerms(player);
        }
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
                        event.getEvent().run(fr.dotmazy.dotplugin.util.Player.valueOf(player));
            }
            for(Player player : playMusics.keySet())
                if(playMusics.get(player)!=null){
                    fr.dotmazy.dotplugin.util.Player pl = fr.dotmazy.dotplugin.util.Player.valueOf(player);
                    PlayedMusic playedMusic = playMusics.get(player);
                    playMusics.replace(player,playedMusic.addTime(1));
                    if(playedMusic.getTime()%10==0) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(playedMusic.getMusic().getName()+": "+playedMusic.getFormattedTime()+" ("+playedMusic.getPercentage()+")"));
                    if(playedMusic.isFinished(playedMusic.getMusic()))
                        DotPlugin.playMusics.remove(player);
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