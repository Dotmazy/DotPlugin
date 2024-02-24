package fr.dotmazy.dotplugin.discord.commands;

import fr.dotmazy.dotplugin.discord.Command;
import fr.dotmazy.dotplugin.util.Database;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.*;

import java.util.List;

public class SetLangCommand extends Command {
    protected SlashCommand getCommand(DiscordApi api) {
        return SlashCommand.with("set-lang","Set your language",
                        List.of(
                                SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING,"language","The language to set", true,
                                        new SlashCommandOptionChoiceBuilder().setName("Fran\u00E7ais").setValue("frensh"),
                                        new SlashCommandOptionChoiceBuilder().setName("English").setValue("english"),
                                        new SlashCommandOptionChoiceBuilder().setName("Espa\u00F1ol").setValue("spanish"),
                                        new SlashCommandOptionChoiceBuilder().setName("\u4E2D\u56FD\u4EBA").setValue("chinese"),
                                        new SlashCommandOptionChoiceBuilder().setName("\u65E5\u672C\u8A9E").setValue("japanese"),
                                        new SlashCommandOptionChoiceBuilder().setName("Deutsch").setValue("german"),
                                        new SlashCommandOptionChoiceBuilder().setName("Esperanto").setValue("esperanto"),
                                        new SlashCommandOptionChoiceBuilder().setName("\u0395\u03BB\u03BB\u03B7\u03BD\u03B9\u03BA\u03AC").setValue("greek"),
                                        new SlashCommandOptionChoiceBuilder().setName("\u0639\u0631\u0628").setValue("arab"))
                        ))
                .createGlobal(api).join();
    }

    protected void onExecute(SlashCommandInteraction interaction, Server server, User user, DiscordApi api, TextChannel channel, List<PermissionType> permissions) {
        String lang = interaction.getArgumentStringValueByName("language").get();
        Database.executeUpdate("UPDATE members SET lang='"+lang+"' WHERE id='"+user.getId()+"'");
    }
}
