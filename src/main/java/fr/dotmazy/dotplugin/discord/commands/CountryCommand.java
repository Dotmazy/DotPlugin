package fr.dotmazy.dotplugin.discord.commands;

import fr.dotmazy.dotplugin.discord.Command;
import fr.dotmazy.dotplugin.discord.Lang;
import fr.dotmazy.dotplugin.util.ConsoleColors;
import fr.dotmazy.dotplugin.util.Database;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.*;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.*;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

import java.awt.*;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CountryCommand extends Command {
    protected SlashCommand getCommand(DiscordApi api) {
        return SlashCommand.with("country","Create a country",
                        Arrays.asList(
                                SlashCommandOption.createSubcommand("create","Create a country",
                                        Arrays.asList(
                                                SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "Name of the country", true),
                                                SlashCommandOption.create(SlashCommandOptionType.STRING, "description", "Description of the country", true),
                                                SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "type", "Type of the country", true,
                                                        new SlashCommandOptionChoiceBuilder().setName("democratie").setValue("democratie")),
                                                SlashCommandOption.create(SlashCommandOptionType.USER, "owner", "Owner of the country", false),
                                                SlashCommandOption.create(SlashCommandOptionType.STRING, "color", "Color of the country", false)
                                        )),
                                SlashCommandOption.createSubcommand("delete","Delete a country",
                                        Collections.singletonList(SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "Name of the country to delete", false)
                                        )),
                                SlashCommandOption.createSubcommand("add-member","Add member to a country",
                                        Arrays.asList(
                                                SlashCommandOption.create(SlashCommandOptionType.USER, "member", "Member to add to this country", true),
                                                SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "Name of the country to add the member", false)
                                        )),
                                SlashCommandOption.createSubcommand("remove-member","Remove member from a country",
                                        Arrays.asList(
                                                SlashCommandOption.create(SlashCommandOptionType.USER, "member", "Member to remove from this country", true),
                                                SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "Name of the country to remove the member", false)
                                        )),
                                SlashCommandOption.createSubcommand("set-admin","Remove member from a country",
                                        Arrays.asList(
                                                SlashCommandOption.create(SlashCommandOptionType.BOOLEAN, "admin", "Do you want to set the member as admin or not", true),
                                                SlashCommandOption.create(SlashCommandOptionType.USER, "member", "Member to set", true),
                                                SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "Name of the country to set the member", false)
                                        ))
                        ))
                .setEnabledInDms(false)
                .createGlobal(api).join();
    }

    protected void onExecute(SlashCommandInteraction interaction, Server server, User user, DiscordApi api, TextChannel channel, List<PermissionType> permissions) {
        CompletableFuture<InteractionOriginalResponseUpdater> response = interaction.respondLater(true);
        if(interaction.getFullCommandName().equals("country create")){
            String name = interaction.getArgumentStringValueByName("name").get();
            String desc = interaction.getArgumentStringValueByName("description").get();
            String type = interaction.getArgumentStringValueByName("type").get();
            User owner = interaction.getArgumentUserValueByName("owner").isPresent()?interaction.getArgumentUserValueByName("owner").get():interaction.getUser();
            String color = interaction.getArgumentStringValueByName("color").isPresent()?interaction.getArgumentStringValueByName("color").get():"#FFFFFF";
            ResultSet res = Database.executeQuery("SELECT name FROM countries WHERE name='"+name+"'");
            ResultSet res1 = Database.executeQuery("SELECT name FROM countries WHERE ownerId='"+owner.getId()+"'");

            String optional = (interaction.getArgumentUserValueByName("owner").isPresent()?" owner:'"+interaction.getArgumentUserValueByName("owner").get()+"'":"")+
                                (interaction.getArgumentStringValueByName("color").isPresent()?" color:'"+interaction.getArgumentStringValueByName("color").get()+"'":"");
            ConsoleColors.info("&1[Discord Bot] "+user.getName()+" have send: /country create name:'"+name+"' description:'"+desc+"' type:'"+type+"'"+optional);

            if(!(color.length() == 6 || color.length() == 7)){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent(Lang.traduct("invalid-hex",user))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}

                return;
            }

            String hex = color.startsWith("#")?color.substring(1):color;
            int r = Integer.parseInt(hex.substring(0,2),16);
            int g = Integer.parseInt(hex.substring(2,4),16);
            int b = Integer.parseInt(hex.substring(4),16);

            try {
                if(res!=null && res.next()){
                    try {
                        response.get(5,TimeUnit.SECONDS)
                                .setContent("A country with the same name already exist.")
                                .setFlags(MessageFlag.EPHEMERAL)
                                .update().join();
                    }catch (Exception e) {}
                    return;
                }else if(res1!=null && res1.next()){
                    try {
                        response.get(5,TimeUnit.SECONDS)
                                .setContent("You already have a country.")
                                .setFlags(MessageFlag.EPHEMERAL)
                                .update().join();
                    }catch (Exception e) {}
                    return;
                }
            } catch (Exception e) {}

            TextChannel requestChannel = server.getTextChannelById(1144192122371706911L).get();
            new MessageBuilder()
                    .setEmbed(new EmbedBuilder()
                            .setTitle(name)
                            .setDescription("<@"+owner.getId()+"> has request to make the country "+name)
                            .addField("**Description:**",desc)
                            .addField("**Type:**",type)
                            .addField("**Color**:",color)
                            .setColor(new Color(r,g,b))
                            .setAuthor(owner)
                            .setTimestampToNow()
                            .setFooter(String.valueOf(owner.getId())))
                    .addComponents(new ActionRowBuilder()
                            .addComponents(Button.create("accept-country",ButtonStyle.SUCCESS,"Accept Country","\u2705"))
                            .addComponents(Button.create("deny-country",ButtonStyle.DANGER,"Deny Country","\u274C"))
                            .build())
                    .send(requestChannel).join();

            try {
                response.get(5,TimeUnit.SECONDS)
                        .setContent("Successfully send a request to create the country "+name+". (Activate MP Message if you want to receive a notification when your country is accepted if your country is accepted)")
                        .setFlags(MessageFlag.EPHEMERAL)
                        .update().join();
            }catch (Exception e) {}
        }
        if(interaction.getFullCommandName().equals("country delete")){
            int countryId = Database.executeIntegerQuery("SELECT country FROM members WHERE id='"+user.getId()+"'");
            String name = Database.executeStringQuery("SELECT name FROM countries WHERE id="+countryId);

            String optional = (interaction.getArgumentUserValueByName("name").isPresent()?" name:'"+interaction.getArgumentUserValueByName("name").get()+"'":"");
            ConsoleColors.info("&1[Discord Bot] "+user.getName()+" have send: /country delete"+optional);

            if(interaction.getArgumentStringValueByName("name").isPresent())
                if(permissions.contains(PermissionType.ADMINISTRATOR)){
                    name = interaction.getArgumentStringValueByName("name").get();
                }else{
                    try {
                        response.get(5,TimeUnit.SECONDS)
                                .setContent(Lang.traduct("no-permission-remove-country",user))
                                .setFlags(MessageFlag.EPHEMERAL)
                                .update().join();
                    }catch (Exception e) {}
                    return;
                }
            try{
                ResultSet result = Database.executeQuery("SELECT textChannelId, voiceChannelId, roleId, ownerId FROM countries WHERE name='"+name+"'");
                if(result!=null && result.next()){
                    String textChannelId = result.getString(1);
                    String voiceChannelId = result.getString(2);
                    String roleId = result.getString(3);
                    String ownerId = result.getString(4);
                    if(Long.parseLong(ownerId)!=user.getId() && !permissions.contains(PermissionType.ADMINISTRATOR)){
                        try {
                            response.get(5,TimeUnit.SECONDS)
                                    .setContent(Lang.traduct("invalid-country",user))
                                    .setFlags(MessageFlag.EPHEMERAL)
                                    .update().join();
                        }catch (Exception e) {}
                        return;
                    }
                    if(server.getChannelById(textChannelId).isPresent()) server.getChannelById(textChannelId).get().delete().join();
                    if(server.getChannelById(voiceChannelId).isPresent()) server.getChannelById(voiceChannelId).get().delete().join();
                    if(server.getRoleById(roleId).isPresent()) server.getRoleById(roleId).get().delete().join();
                    Database.executeUpdate("UPDATE members SET isAdmin=0, country=-1 WHERE id='"+ownerId+"'");
                }
            }catch(Exception e){}
            Database.executeUpdate("DELETE FROM countries WHERE name='"+name+"'");
            try {
                response.get(5,TimeUnit.SECONDS)
                        .setContent(Lang.traduct("success-delete-country",user).replace("{name}",name))
                        .setFlags(MessageFlag.EPHEMERAL)
                        .update().join();
            }catch (Exception e) {}
        }
        if(interaction.getFullCommandName().equals("country add-member")){
            String userCountry = Database.executeStringQuery("SELECT name FROM countries WHERE ownerId='"+user.getId()+"'");
            User member = interaction.getArgumentUserValueByName("member").get();
            String country = userCountry;

            String optional = (interaction.getArgumentUserValueByName("country").isPresent()?" country:'"+interaction.getArgumentUserValueByName("country").get()+"'":"");
            ConsoleColors.info("&1[Discord Bot] "+user.getName()+" have send: /country add-member member:'"+member.getName()+"'"+optional);

            if(interaction.getArgumentStringValueByName("country").isPresent())
                if(permissions.contains(PermissionType.ADMINISTRATOR)){
                    country = interaction.getArgumentStringValueByName("country").get();
                }else{
                    try {
                        response.get(5,TimeUnit.SECONDS)
                                .setContent(Lang.traduct("no-permission-add-player",user))
                                .setFlags(MessageFlag.EPHEMERAL)
                                .update().join();
                    }catch (Exception e) {}
                    return;
                }
            if(country==null){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent(Lang.traduct("invalid-country",user))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }
            int memberCountry = Database.executeIntegerQuery("SELECT country FROM members WHERE id='"+member.getId()+"'");
            int countryId = Database.executeIntegerQuery("SELECT id FROM countries WHERE name='"+country+"'");
            if(memberCountry==countryId){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent(Lang.traduct("member-already-on-country",user).replace("{member}","<@"+member.getId()+">"))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }else if(memberCountry>-1){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent(Lang.traduct("member-already-on-a-country",user).replace("{member}","<@"+member.getId()+">"))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }

            new MessageBuilder()
                    .setContent(Lang.traduct("user-send-request",member).replace("{user}",user.getName()).replace("{country}",country))
                    .addComponents(new ActionRowBuilder()
                            .addComponents(new ButtonBuilder()
                                    .setCustomId("accept-join-country")
                                    .setStyle(ButtonStyle.SUCCESS)
                                    .setEmoji("\u2705")
                                    .build())
                            .addComponents(new ButtonBuilder()
                                    .setCustomId("deny-join-country")
                                    .setStyle(ButtonStyle.DANGER)
                                    .setEmoji("\u274C")
                                    .build())
                            .build())
                    .send(member).join();

            try {
                response.get(5,TimeUnit.SECONDS)
                        .setContent(Lang.traduct("request-send",user).replace("{member}","<@"+member.getId()+">"))
                        .setFlags(MessageFlag.EPHEMERAL)
                        .update().join();
            }catch (Exception e) {}
        }
        if(interaction.getFullCommandName().equals("country remove-member")){
            User member = interaction.getArgumentUserValueByName("member").get();
            String country = Database.executeStringQuery("SELECT name FROM countries WHERE ownerId='"+user.getId()+"'");

            String optional = (interaction.getArgumentUserValueByName("country").isPresent()?" country:'"+interaction.getArgumentUserValueByName("country").get()+"'":"");
            ConsoleColors.info("&1[Discord Bot] "+user.getName()+" have send: /country remove-member name:'"+member.getName()+"'"+optional);

            if(interaction.getArgumentStringValueByName("country").isPresent())
                if(permissions.contains(PermissionType.ADMINISTRATOR)){
                    country = interaction.getArgumentStringValueByName("country").get();
                }else{
                    try {
                        response.get(5,TimeUnit.SECONDS)
                                .setContent(Lang.traduct("no-permission-remove-players",user))
                                .setFlags(MessageFlag.EPHEMERAL)
                                .update().join();
                    }catch (Exception e) {}
                    return;
                }
            if(country==null){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent("Invalid country.")
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }
            int countryId = Database.executeIntegerQuery("SELECT id FROM countries WHERE name='"+country+"'");
            if(Database.executeIntegerQuery("SELECT country FROM members WHERE id='"+member.getId()+"'")!=countryId){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent(Lang.traduct("member-not-on-country",user).replace("{member}","<@"+member.getId()+">"))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }
            String roleId = Database.executeStringQuery("SELECT roleId FROM countries WHERE name='"+country+"'");
            if(server.getRoleById(roleId).isPresent()) member.removeRole(server.getRoleById(roleId).get()).join();

            Database.executeUpdate("UPDATE members SET country=-1 WHERE id='"+member.getId()+"'");

            try {
                response.get(5,TimeUnit.SECONDS)
                        .setContent(Lang.traduct("member-no-longer-in-country",user).replace("{member}","<@"+member.getId()+">"))
                        .setFlags(MessageFlag.EPHEMERAL)
                        .update().join();
            }catch (Exception e) {}
        }
        if(interaction.getFullCommandName().equals("country set-admin")){
            User member = interaction.getArgumentUserValueByName("member").get();
            boolean admin = interaction.getArgumentBooleanValueByName("admin").get();
            String country = Database.executeStringQuery("SELECT name FROM countries WHERE ownerId='"+user.getId()+"'");

            String optional = (interaction.getArgumentUserValueByName("country").isPresent()?" country:'"+interaction.getArgumentUserValueByName("country").get()+"'":"");
            ConsoleColors.info("&1[Discord Bot] "+user.getName()+" have send: /country set-admin admin:'"+member.getName()+"' member:'"+member.getName()+"'"+optional);

            if(interaction.getArgumentStringValueByName("country").isPresent())
                if(permissions.contains(PermissionType.ADMINISTRATOR)){
                    country = interaction.getArgumentStringValueByName("country").get();
                }else{
                    try {
                        response.get(5,TimeUnit.SECONDS)
                                .setContent(Lang.traduct("no-permission-remove-players",user))
                                .setFlags(MessageFlag.EPHEMERAL)
                                .update().join();
                    }catch (Exception e) {}
                    return;
                }
            if(country==null){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent(Lang.traduct("invalid-country",user))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }
            int countryId = Database.executeIntegerQuery("SELECT id FROM countries WHERE name='"+country+"'");
            if(Database.executeIntegerQuery("SELECT country FROM members WHERE id='"+member.getId()+"'")!=countryId){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent(Lang.traduct("member-not-on-country",user).replace("{member}","<@"+member.getId()+">"))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }else if(Database.executeBooleanQuery("SELECT isAdmin FROM members WHERE id='"+member.getId()+"'")==admin){
                try {
                    response.get(5,TimeUnit.SECONDS)
                            .setContent((admin?Lang.traduct("member-already-admin",user):Lang.traduct("member-already-not-admin",user)).replace("{member}","<@"+member.getId()+">"))
                            .setFlags(MessageFlag.EPHEMERAL)
                            .update().join();
                }catch (Exception e) {}
                return;
            }
            String textChannelId = Database.executeStringQuery("SELECT textChannelId FROM countries WHERE id="+countryId);
            String voiceChannelId = Database.executeStringQuery("SELECT voiceChannelId FROM countries WHERE id="+countryId);
            if(server.getTextChannelById(textChannelId).isPresent()){
                ServerTextChannelUpdater updater = server.getTextChannelById(textChannelId).get().createUpdater();
                if(admin) updater.addPermissionOverwrite(member,Permissions.fromBitmask(539068198737L));
                else updater.removePermissionOverwrite(member);
                updater.update().join();
            }
            if(server.getVoiceChannelById(voiceChannelId).isPresent()){
                ServerVoiceChannelUpdater updater = server.getVoiceChannelById(voiceChannelId).get().createUpdater();
                if(admin) updater.addPermissionOverwrite(member,Permissions.fromBitmask(539068198737L));
                else updater.removePermissionOverwrite(member);
                updater.update().join();
            }

            Database.executeUpdate("UPDATE members SET isAdmin="+(admin?1:0)+" WHERE id='"+member.getId()+"'");
            try {
                response.get(5,TimeUnit.SECONDS)
                        .setContent((admin?Lang.traduct("member-admin",user):Lang.traduct("member-not-admin",user)).replace("{member}","<@"+member.getId()+">"))
                        .setFlags(MessageFlag.EPHEMERAL)
                        .update().join();
            }catch (Exception e) {}
        }
    }
}
