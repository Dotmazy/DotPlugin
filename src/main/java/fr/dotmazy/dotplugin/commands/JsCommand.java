package fr.dotmazy.dotplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.luaj.vm2.LuaError;

public class JsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            if (args.length == 1) {
                //LuaUtil.runFile(args[0]);
            } else {
                //LuaUtil.runFile("main.lua");
            }
        }catch(LuaError | StackOverflowError e){
            System.err.println("! Stack overflow error !");
        }
        return true;
    }

}