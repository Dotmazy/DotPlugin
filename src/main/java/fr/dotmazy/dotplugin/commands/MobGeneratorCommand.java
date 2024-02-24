package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.gui.MobGeneratorGui;
import fr.dotmazy.dotplugin.util.Api;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class MobGeneratorCommand implements CommandExecutor {

    private final DotPlugin dotPlugin;

    public MobGeneratorCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player)sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.mobgenerator.enable")){
            sender.sendMessage(Api.Text.transformText("commands.commandDisableMessage",options));
            return true;
        }
        if(!(sender instanceof Player player)){
            sender.sendMessage(Api.Text.transformText("commands.onlyPlayerCommandMessage",options));
            return true;
        }
        if(!player.hasPermission("dotplugin.commands.mobgenerator")){
            sender.sendMessage(Api.Text.transformText("commands.mobgenerator",options));
            return true;
        }

        player.openInventory(new MobGeneratorGui().getMainInventory());
        return true;
    }

}
