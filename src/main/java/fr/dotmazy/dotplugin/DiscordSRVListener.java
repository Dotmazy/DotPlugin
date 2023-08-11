package fr.dotmazy.dotplugin;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import org.bukkit.plugin.Plugin;

public class DiscordSRVListener {
    private final Plugin plugin;

    public DiscordSRVListener(Plugin plugin){ this.plugin = plugin; }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event){
        System.out.println("--Bot Ready--");
    }
}
