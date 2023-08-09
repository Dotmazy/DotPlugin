package fr.dotmazy.dotplugin.util;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.api.TextApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandUtil implements CommandExecutor, TabCompleter {

    protected final DotPlugin dotPlugin;

    protected CommandUtil(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    protected static String getOption(String option, String... options){
        for (String opt : options)
            if(test(opt,option)) return TextApi.getTranslateConfig("commands.testArgument.message",Map.of("test_arg",opt));
        return "";
    }

    private static boolean test(String option, String testOption){
        List<Character> optionChars = new ArrayList<Character>();
        for(char c : option.toCharArray()){
            optionChars.add(c);
        }
        int count = 0;
        for (char c : testOption.toCharArray()){
            if (optionChars.contains(c)){
                count++;
            }
        }
        return count > Math.round((float) testOption.toCharArray().length / 100 * 50) && !(testOption.length() >= option.length()+5);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
