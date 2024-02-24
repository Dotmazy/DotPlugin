package fr.dotmazy.dotplugin.discord;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.InteractionCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    private static SlashCommand command;

    public Command(){
        command = getCommand(DiscordBot.api);
        DiscordBot.api.addInteractionCreateListener(this::onSlashCommandCreate);
    }

    protected abstract void onExecute(SlashCommandInteraction interaction, Server server, User user, DiscordApi api, TextChannel channel, List<PermissionType> permissions);

    protected abstract SlashCommand getCommand(DiscordApi api);

    private void onSlashCommandCreate(InteractionCreateEvent event){
        if(event.getSlashCommandInteraction().isPresent() && event.getSlashCommandInteraction().get().getCommandName().equals(getCommand(DiscordBot.api).getName())){
            SlashCommandInteraction interaction = event.getSlashCommandInteraction().get();
            List<PermissionType> permissions = new ArrayList<>();
            if(interaction.getServer().isPresent()){
                List<Role> roles = interaction.getUser().getRoles(interaction.getServer().get());
                for(Role role : roles)
                    permissions.addAll(role.getAllowedPermissions());
            }
            onExecute(interaction,interaction.getServer().isPresent()?interaction.getServer().get():null,interaction.getUser(),interaction.getApi(),interaction.getChannel().get(),permissions);
        }
    }

    public static SlashCommand getCommand(){
        return command;
    }

}
