package fr.dotmazy.dotplugin.discord;

import fr.dotmazy.dotplugin.util.Database;
import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRowBuilder;
import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.entity.message.component.TextInputStyle;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.AutocompleteCreateEvent;
import org.javacord.api.event.interaction.InteractionCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.ModalInteraction;

import java.awt.*;

public class CommandListener {
    public static void onInteractionCreate(InteractionCreateEvent event) {
        if(event.getMessageComponentInteraction().isPresent()){
            MessageComponentInteraction interaction = event.getMessageComponentInteraction().get();
            Server server = interaction.getServer().isPresent()?interaction.getServer().get():null;
            String customId = interaction.getCustomId();
            if(customId.equals("accept-country")){
                Embed embed = interaction.getMessage().getEmbeds().get(0);
                String name = embed.getTitle().get();
                String desc = embed.getFields().get(0).getValue();
                String type = embed.getFields().get(1).getValue();
                Color color = embed.getColor().get();
                User owner = interaction.getServer().get().getMemberById(embed.getFooter().get().getText().get()).get();

                ChannelCategory cat = server.getChannelCategoryById(1143967106669490217L).get();
                Role role = new RoleBuilder(server)
                        .setName(name)
                        .setColor(color)
                        .setDisplaySeparately(false)
                        .create().join();
                ServerTextChannel textChannel = new ServerTextChannelBuilder(server)
                        .setCategory(cat)
                        .setName(name+"-text")
                        .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0))
                        .addPermissionOverwrite(role, Permissions.fromBitmask(521875738177L))
                        .addPermissionOverwrite(owner, Permissions.fromBitmask(539068198737L))
                        .create().join();
                ServerVoiceChannel voiceChannel = new ServerVoiceChannelBuilder(server)
                        .setCategory(cat)
                        .setName(name+" vocal")
                        .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0))
                        .addPermissionOverwrite(role, Permissions.fromBitmask(521875738177L))
                        .addPermissionOverwrite(owner, Permissions.fromBitmask(539068198737L))
                        .create().join();

                owner.addRole(role).join();

                Database.executeUpdate("INSERT INTO countries(name,description,ownerId,ownerUuid,type,color,roleId,textChannelId,voiceChannelId) VALUES ('"+name+"','"+desc+"','"+owner.getId()+"','','"+type+"','"+embed.getFields().get(2).getValue()+"','"+role.getId()+"','"+textChannel.getId()+"','"+voiceChannel.getId()+"')");
                Database.executeUpdate("UPDATE members SET isAdmin=1, country="+Database.executeIntegerQuery("SELECT id FROM countries WHERE name='"+name+"'")+" WHERE id='"+owner.getId()+"'");

                owner.sendMessage("Your country (`"+name+"`) have been accepted.");

                interaction.getMessage().delete().join();
            }
            if(customId.equals("deny-country")){
                Embed embed = interaction.getMessage().getEmbeds().get(0);
                String name = embed.getTitle().get();
                User owner = interaction.getServer().get().getMemberById(embed.getFooter().get().getText().get()).get();

                owner.sendMessage("Your country (`"+name+"`) have been denied.");

                interaction.getMessage().delete().join();
            }
            if(customId.equals("accept-join-country")){
                String[] msg = interaction.getMessage().getContent().split(" ");
                String countryName = msg[msg.length-1].replace("(","").replace(")","").replace("`","").replace(".","");
                int countryId = Database.executeIntegerQuery("SELECT id FROM countries WHERE name='"+countryName+"'");
                Database.executeUpdate("UPDATE members SET country="+countryId+" WHERE id='"+interaction.getUser().getId()+"'");
                String roleId = Database.executeStringQuery("SELECT roleId FROM countries WHERE id='"+countryId+"'");
                if(DiscordBot.api.getRoleById(roleId).isPresent()) interaction.getUser().addRole(DiscordBot.api.getRoleById(roleId).get()).join();
                try {
                    User owner = DiscordBot.api.getUserById(Database.executeStringQuery("SELECT ownerId FROM countries WHERE id="+countryId)).get();
                    new MessageBuilder()
                            .setContent(interaction.getUser().getName()+" have accepted your join request.")
                            .send(owner).join();
                }catch(Exception ignored){}
                interaction.getMessage().delete().join();
            }
            if(customId.equals("deny-join-country")){
                String[] msg = interaction.getMessage().getContent().split(" ");
                String countryName = msg[msg.length-1].replace("(","").replace(")","").replace("`","").replace(".","");
                int countryId = Database.executeIntegerQuery("SELECT id FROM countries WHERE name='"+countryName+"'");
                try {
                    User owner = DiscordBot.api.getUserById(Database.executeStringQuery("SELECT ownerId FROM countries WHERE id="+countryId)).get();
                    new MessageBuilder()
                            .setContent(interaction.getUser().getName()+" have denied your join request.")
                            .send(owner).join();
                }catch(Exception ignored){}
                interaction.getMessage().delete().join();
            }
            if(customId.equals("accept-rules")){
                interaction.respondWithModal("link-account","Link",
                        new ActionRowBuilder()
                                .addComponents(new TextInputBuilder(TextInputStyle.SHORT,"minecraft-name","Minecraft Name")
                                        .setRequired(true)
                                        .setMinimumLength(3)
                                        .setMaximumLength(16)
                                        .build())
                                .build());
            }
        }else if(event.getInteraction().asModalInteraction().isPresent()){
            ModalInteraction interaction = event.getInteraction().asModalInteraction().get();
            String customId = interaction.getCustomId();
            if(customId.equals("link-account")){
                String name = interaction.getTextInputValueByCustomId("minecraft-name").get();
                Database.executeUpdate("INSERT INTO link(memberId,playerUuid) VALUES ('"+interaction.getUser().getId()+"','"+name+"')");
            }
        }
    }

    public static void onAutocomplete(AutocompleteCreateEvent event) {}
}
