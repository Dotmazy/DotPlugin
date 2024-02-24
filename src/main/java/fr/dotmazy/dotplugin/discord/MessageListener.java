package fr.dotmazy.dotplugin.discord;

import fr.dotmazy.dotplugin.DotPlugin;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.MessageDeleteEvent;
import org.javacord.api.event.message.MessageEditEvent;
import org.javacord.api.event.message.MessageReplyEvent;

import java.awt.*;
import java.time.Instant;
import java.util.Optional;

public class MessageListener {

    public static void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageAuthor().getId()!= DiscordBot.api.getClientId()){
            if(event.getChannel().getId()==DotPlugin.getInstance().getConfig().getLong("discordLogChannel")){
                if(event.getMessageContent().equalsIgnoreCase("clear")){
                    try {
                        event.getChannel().bulkDelete(event.getChannel().getMessages(100).get());
                    }catch(Exception ignored){}
                }
                event.getMessage().delete();
                return;
            }
            Optional<Channel> c = DiscordBot.api.getChannelById(DotPlugin.getInstance().getConfig().getLong("discordLogChannel"));
            if(c.isPresent() && c.get().asTextChannel().isPresent()){
                TextChannel channel = c.get().asTextChannel().get();
                channel.sendMessage(new EmbedBuilder().setColor(new Color(30, 196, 70))
                        .setAuthor(event.getMessageAuthor())
                        .setTitle(":envelope_with_arrow: Message Sent (`"+event.getMessageId()+")`")
                        .setDescription("A message has been sent by <@"+event.getMessageAuthor().getId()+">(`"+event.getMessageAuthor().getId()+"`) in <#"+event.getChannel().getId()+">(`"+event.getChannel().getId()+"`)")
                        .addField("**Message:**",event.getMessageContent())
                        .setTimestamp(Instant.now()));
            }
        }
    }

    public static void onMessageDelete(MessageDeleteEvent event){
        if(event.getMessageAuthor().isPresent() && event.getMessageAuthor().get().getId()!=DiscordBot.api.getClientId()){
            Optional<Channel> c = DiscordBot.api.getChannelById(DotPlugin.getInstance().getConfig().getLong("discordLogChannel"));
            if(c.isPresent() && c.get().asTextChannel().isPresent()){
                TextChannel channel = c.get().asTextChannel().get();
                channel.sendMessage(new EmbedBuilder().setColor(new Color(196, 30, 70))
                        .setAuthor(event.getMessageAuthor().get())
                        .setTitle(":wastebasket: Message Delete (`"+event.getMessageId()+")`")
                        .setDescription("A message has been deleted")
                        .addField("**Message:**",event.getMessageContent().isPresent()?event.getMessageContent().get():"unknown")
                        .addField("**Info:**", "Message was sent by <@"+event.getMessageAuthor().get().getId()+">(`"+event.getMessageAuthor().get().getId()+"`) in <#"+event.getChannel().getId()+">(`"+event.getChannel().getId()+"`)")
                        .setTimestamp(Instant.now()));
            }
        }
    }

    public static void onMessageEdit(MessageEditEvent event){
        if(event.getMessageAuthor().getId()!=DiscordBot.api.getClientId()){
            Optional<Channel> c = DiscordBot.api.getChannelById(DotPlugin.getInstance().getConfig().getLong("discordLogChannel"));
            if(c.isPresent() && c.get().asTextChannel().isPresent()){
                TextChannel channel = c.get().asTextChannel().get();
                channel.sendMessage(new EmbedBuilder().setColor(new Color(30, 70, 196))
                        .setAuthor(event.getMessageAuthor())
                        .setTitle(":pencil: Message Edit (`"+event.getMessageId()+")`")
                        .setDescription("A message has been edited by <@"+event.getMessageAuthor().getId()+">(`"+event.getMessageAuthor().getId()+"`) in <#"+event.getChannel().getId()+">(`"+event.getChannel().getId()+"`)")
                        .addField("**Old Message:**",event.getOldMessage().isPresent()?event.getOldMessage().get().getContent():"unknown")
                        .addField("**Edited Message:**",event.getMessageContent())
                        .setTimestamp(Instant.now()));
            }
        }
    }

    public static void onMessageReply(MessageReplyEvent event){
        if(event.getMessageAuthor().getId()!=DiscordBot.api.getClientId()){
            Optional<Channel> c = DiscordBot.api.getChannelById(DotPlugin.getInstance().getConfig().getLong("discordLogChannel"));
            if(c.isPresent() && c.get().asTextChannel().isPresent()){
                TextChannel channel = c.get().asTextChannel().get();
                channel.sendMessage(new EmbedBuilder().setColor(new Color(30, 70, 196))
                        .setAuthor(event.getMessageAuthor())
                        .setTitle(":wastebasket: Message Replied (`"+event.getMessageId()+")`")
                        .setDescription("A message has been replied by <@"+event.getMessageAuthor().getId()+">(`"+event.getMessageAuthor().getId()+"`) in <#"+event.getChannel().getId()+">(`"+event.getChannel().getId()+"`)")
                        .addField("**Message:**",event.getReferencedMessage().getContent())
                        .addField("**Replied Message:**", event.getMessageContent())
                        .setTimestamp(Instant.now()));
            }
        }
    }

}
