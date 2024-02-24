package fr.dotmazy.dotplugin.discord.commands;

import fr.dotmazy.dotplugin.discord.Command;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.*;

import java.util.Arrays;
import java.util.List;

public class ClearCommand extends Command {
    protected SlashCommand getCommand(DiscordApi api) {
        return SlashCommand.with("clear","Clear messages",
                        Arrays.asList(SlashCommandOption.create(SlashCommandOptionType.DECIMAL, "number", "Number of messages to delete", true)))
                .createGlobal(api).join();
    }

    protected void onExecute(SlashCommandInteraction interaction, Server server, User user, DiscordApi api, TextChannel channel, List<PermissionType> permissions) {
        try {
            int number = interaction.getArgumentByName("number").get().getDecimalValue().get().intValue();
            if(number < 101 && number > 1){
                channel.bulkDelete(channel.getMessages(number).get());
            }else if(number == 1) channel.deleteMessages(channel.getMessages(1).get().getNewestMessage().get());
            else{
                interaction.createImmediateResponder()
                        .setContent("You can only delete 100 message at once.")
                        .setFlags(MessageFlag.EPHEMERAL)
                        .respond();
                return;
            }
            interaction.createImmediateResponder()
                    .setContent("Successfully deleted "+number+" messages.")
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();
        } catch (Exception e) {
            interaction.createImmediateResponder()
                    .setContent("An error occurred when trying to delete messages.")
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();
        }
    }
}
