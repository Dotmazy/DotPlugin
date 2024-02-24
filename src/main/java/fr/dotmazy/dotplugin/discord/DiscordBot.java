package fr.dotmazy.dotplugin.discord;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.discord.commands.ClearCommand;
import fr.dotmazy.dotplugin.discord.commands.CountryCommand;
import fr.dotmazy.dotplugin.discord.commands.SetLangCommand;
import fr.dotmazy.dotplugin.util.ConsoleColors;
import org.bukkit.configuration.file.FileConfiguration;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRowBuilder;
import org.javacord.api.entity.message.component.ButtonBuilder;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.listener.GloballyAttachableListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import java.util.ArrayList;
import java.util.List;

public class DiscordBot {
    public static DiscordApi api;

    private static List<ListenerManager<?>> listeners;

    public static void init(DotPlugin dotPlugin) {
        if(api!=null){
            for(ListenerManager<?> listener : listeners)
                api.removeListener((GloballyAttachableListener)listener.getListener());
            listeners.clear();
        }else listeners = new ArrayList<>();
        FileConfiguration config = dotPlugin.getConfig();
        api = new DiscordApiBuilder()
                .setToken(config.getString("discord_bot.token"))
                .setAllIntents()
                .login().join();

        ConsoleColors.info("&1[Discord Bot] Bot ready !");

        if(config.get("discord_bot.activity")!=null && config.getString("discord_bot.activity.type")!=null && config.getString("discord_bot.activity.name")!=null){
            try{
                api.updateActivity(ActivityType.valueOf(config.getString("discord_bot.activity.type").toUpperCase()),config.getString("discord_bot.activity.name"));
                ConsoleColors.info("&1[Discord Bot] Activity set to "+config.getString("discord_bot.activity.type").toLowerCase()+" "+config.getString("discord_bot.activity.name"));
            }catch(Exception e){
                ConsoleColors.info("&c[Discord Bot] Unknown activity type: "+config.getString("discord_bot.activity.type"));
            }
        }

        ConsoleColors.info("&1[Discord Bot] Here is a bot invite: "+api.createBotInvite(Permissions.fromBitmask(8)));

        /*new MessageBuilder()
                .setContent("This is the rules:\n- rule 1\n- rule 2\n- rule 3\n- rule 4")
                .addComponents(new ActionRowBuilder()
                        .addComponents(new ButtonBuilder()
                                .setStyle(ButtonStyle.SUCCESS)
                                .setCustomId("accept-rules")
                                .setEmoji("\u2705")
                                .build())
                        .build())
                .send(api.getTextChannelById(1144314120133414973L).get()).join();*/

        registerCommands();

        listeners.add(api.addMessageCreateListener(MessageListener::onMessageCreate));
        listeners.add(api.addMessageDeleteListener(MessageListener::onMessageDelete));
        listeners.add(api.addMessageEditListener(MessageListener::onMessageEdit));
        listeners.add(api.addMessageReplyListener(MessageListener::onMessageReply));
        listeners.add(api.addInteractionCreateListener(CommandListener::onInteractionCreate));
        listeners.add(api.addAutocompleteCreateListener(CommandListener::onAutocomplete));
        listeners.add(api.addServerMemberJoinListener(MemberListener::onServerMemberJoin));

        System.out.println();
    }

    private static void registerCommands() {
        new ClearCommand();
        new CountryCommand();
        new SetLangCommand();
    }
}
