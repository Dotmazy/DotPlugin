package fr.dotmazy.dotplugin.listeners;

import fr.dotmazy.dotplugin.DotPlugin;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogEvent extends ConsoleHandler {

    private final DotPlugin dotPlugin;

    public LogEvent(DotPlugin dotPlugin){
        this.dotPlugin = dotPlugin;
    }

    @Override
    public void publish(LogRecord record) {
        Level level = record.getLevel();
        String loggerName = record.getLoggerName();
        String message = record.getMessage();

        //DiscordUtil.sendMessage(DiscordUtil.getTextChannelById(dotPlugin.getConfig().getString("logChannel")),
                //"level:"+level.toString()+" | loggername:"+loggerName+" | message:"+message);

        super.publish(record);
    }

}
