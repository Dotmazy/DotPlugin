package fr.dotmazy.dotplugin.discord;

import fr.dotmazy.dotplugin.util.Database;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;

public class MemberListener {
    public static void onServerMemberJoin(ServerMemberJoinEvent event) {
        Server server = event.getServer();
        User user = event.getUser();
        Database.executeUpdate("INSERT INTO members(id,name) VALUES ('"+user.getId()+"','"+user.getName()+"')");
        new MessageBuilder()
                .setContent("<@"+user.getId()+"> have join the server, say welcome to him.")
                .send(server.getTextChannelById(1112633867740921918L).get()).join();
    }
}
