package fr.dotmazy.dotplugin.listeners;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.discord.DiscordBot;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;

import java.util.Optional;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogEvent extends ConsoleHandler {

    private final DotPlugin dotPlugin;

    public LogEvent(DotPlugin dotPlugin){
        this.dotPlugin = dotPlugin;
    }

    public void publish(LogRecord record) {
        Level level = record.getLevel();
        String loggerName = record.getLoggerName();
        String message = record.getMessage();

        if(level != Level.WARNING){
            super.publish(record);
            flush();
        }

        if(DiscordBot.api != null){
            Optional<Channel> c = DiscordBot.api.getChannelById(dotPlugin.getConfig().getLong("logChannel"));
            if(c.isPresent() && c.get().asTextChannel().isPresent()){
                TextChannel channel = c.get().asTextChannel().get();
                //channel.sendMessage("```"+(level==Level.INFO?"javascript\n/*":(level==Level.WARNING?"\n'":""))+"```");
                //channel.sendMessage("level:"+level.toString()+" | loggername:"+loggerName+" | message:"+message);
            }
        }
    }
}