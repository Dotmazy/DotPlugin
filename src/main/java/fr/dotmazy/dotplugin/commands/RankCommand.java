package fr.dotmazy.dotplugin.commands;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.gui.RankGui;
import fr.dotmazy.dotplugin.old.api.PermApi;
import fr.dotmazy.dotplugin.old.api.TextApi;
import fr.dotmazy.dotplugin.util.Rank;
import java.lang.Object;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RankCommand implements CommandExecutor, TabCompleter {

    private final DotPlugin dotPlugin;

    public RankCommand(DotPlugin dotPlugin) {
        this.dotPlugin = dotPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, Object> options = Map.of(
                "player", sender instanceof Player?(Player)sender:null,
                "world", sender instanceof Player?((Player) sender).getWorld():null
        );
        if (!dotPlugin.getConfig().getBoolean("commands.home.enable")){
            sender.sendMessage(TextApi.getTranslateConfig("commands.commandDisableMessage",options));
            return true;
        }
        if (sender instanceof Player && !(((Player)sender).hasPermission("dotplugin.rank"))){
            sender.sendMessage(TextApi.getTranslateConfig("commands.noPermissionMessage",options));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextApi.getTranslateConfig("commands.onlyPlayerCommandMessage",options));
            return true;
        }

        player.openInventory(new RankGui().getMainInventory());

        /*if(args.length < 1){
            sender.sendMessage("Please use this command:\n/rank ["+(!PlayerApi.hasPerm(sender,"dotplugin.ranks.create")?"create":""+",remove,join,list]"));
            return true;
        }
        String option = args[0];
        if(!args[0].equals("list") && !args[0].equals("create") && !args[0].equals("remove") && !args[0].equals("join") && !args[0].equals("modify")){
            sender.sendMessage("Invalid option."+(getOption(option)==null?"":"\nDo you mean "+getOption(option)+"?"));
            return true;
        }

        Player player = null;
        try{player = Bukkit.getPlayer(args[1]);}catch(Exception ignored){}

        switch (option) {
            case "create":
                if (args.length < 2){
                    sender.sendMessage("u00A7cPlease use this command: /rank create <name>");
                    break;
                }
                if (RankApi.getRank(args[1])!=null){
                    sender.sendMessage("u00A7cThis rank already exist");
                    break;
                }
                if (args.length == 2){
                    RankApi.makeRank(args[1]);
                    break;
                }
                String name = args[1];
                String prefix = "";
                String suffix = "";
                int type = 0;
                List<String> permissions = new ArrayList<>();
                for (int i=2; i < args.length; i++) {
                    if (args[i].startsWith("-")){
                        switch (args[i]) {
                            case "-prefix":
                                type = 1;
                                break;
                            case "-suffix":
                                type = 2;
                                break;
                            case "-perms":
                                type = 3;
                                break;
                            default:
                                break;
                        }
                    }else {
                        switch (type) {
                            case 1:
                                prefix += " "+args[i];
                                break;
                            case 2:
                                suffix += " "+args[i];
                                break;
                            case 3:
                                permissions.add(args[i]);
                                break;
                            default:
                                break;
                        }
                    }
                }
                RankApi.makeRank(name,prefix.substring(1),suffix.substring(1),permissions);
                sender.sendMessage("You have successfully created the rank "+name);
                break;
            case "remove":
                if (args.length < 2){
                    sender.sendMessage("u00A7cPlease use this command: /rank remove <name>");
                    break;
                }
                if(!RankApi.removeRank(RankApi.getRank(args[1]))){
                    sender.sendMessage("u00A7cThis rank doesn't exist");
                    break;
                }
                sender.sendMessage("You have successfully removed the rank "+args[1]);
                break;
            case "join":
                if (args.length < 3){
                    sender.sendMessage("u00A7cPlease use this command: /rank join <player> <name>");
                    break;
                }
                if (player == null){
                    sender.sendMessage("u00A7cThis player is invalid or not online");
                    break;
                }
                if(RankApi.getRank(args[2])==null){
                    sender.sendMessage("u00A7cThis rank doesn't exist");
                    break;
                }
                PlayerApi.joinRank(player,RankApi.getRank(args[2]));
                sender.sendMessage(player.getName()+" have successfully join the rank "+args[2]);
                break;
            case "modify":
                if (args.length < 4){
                    sender.sendMessage("u00A7cPlease use this command: /rank modify <name> [name,prefix,suffix,perms,addperm,removeperm] <value(s)>");
                    break;
                }
                if(RankApi.getRank(args[1])==null){
                    sender.sendMessage("u00A7cThis rank doesn't exist");
                }
                Rank rank = RankApi.getRank(args[1]);
                switch (args[2]){
                    case "name":
                        RankApi.setName(RankApi.getRank(args[1]),args[3]);
                        //rank = RankApi.getRank(args[3]);
                        sender.sendMessage("You have successfully change the name of rank "+rank.getName()+"\nHere is an example: "+rank.getName()+" "+ChatColor.translateAlternateColorCodes('&',rank.getPrefix())+"ExampleMember"+ChatColor.translateAlternateColorCodes('&',rank.getSuffix())+": This is an example message !");
                        break;
                    case "prefix":
                        String t2 = "";
                        for (int i = 3; i < args.length; i++) {
                            t2 += " "+args[i];
                        }
                        RankApi.setPrefix(RankApi.getRank(args[1]),t2.substring(1));
                        sender.sendMessage("You have successfully change the prefix of rank "+rank.getName()+"\nHere is an example: "+rank.getName()+" "+ChatColor.translateAlternateColorCodes('&', rank.getPrefix())+"ExampleMember"+ChatColor.translateAlternateColorCodes('&', rank.getSuffix())+": This is an example message !");
                        break;
                    case "suffix":
                        String t3 = "";
                        for (int i = 3; i < args.length; i++) {
                            t3 += " "+args[i];
                        }
                        RankApi.setSuffix(RankApi.getRank(args[1]),t3.substring(1));
                        sender.sendMessage("You have successfully change the suffix of rank "+rank.getName()+"\nHere is an example: "+rank.getName()+" "+ChatColor.translateAlternateColorCodes('&',rank.getPrefix())+"ExampleMember"+ChatColor.translateAlternateColorCodes('&',rank.getSuffix())+": This is an example message !");
                        break;
                    case "perms":
                        List<String> perms = new ArrayList<String>();
                        for (int i = 3; i < args.length; i++) {
                            perms.add(args[i]);
                        }
                        RankApi.setPerms(RankApi.getRank(args[1]),perms);
                        sender.sendMessage("Successfully set perms of rank "+args[1]);
                        break;
                    case "addperm":
                        if (!RankApi.addPerm(RankApi.getRank(args[1]),args[3])){
                            sender.sendMessage("u00A7cThis rank already exist or already have this perm");
                            break;
                        }
                        break;
                    case "removeperm":
                        if (!RankApi.removePerm(RankApi.getRank(args[1]),args[3])){
                            sender.sendMessage("u00A7cThis rank doen't exist or haven't this perm");
                            break;
                        }
                        break;
                    default:
                        sender.sendMessage("u00A7cPlease use this command: /rank modify <name> [name,prefix,suffix,perms,addperm,removeperm] <value(s)>");
                        break;
                }
                break;
            case "list":
                StringBuilder message = new StringBuilder("Here is a list of all ranks:\n");
                for (Rank rankc : RankApi.getRanks()){
                    message
                            .append("u00A7r- ")
                            .append(rankc.getName())
                            .append(" ")
                            .append(ChatColor.translateAlternateColorCodes('&',rankc.getPrefix()))
                            .append("ExampleMember")
                            .append(ChatColor.translateAlternateColorCodes('&',rankc.getSuffix()))
                            .append(": This is an example message !\n");
                }

                sender.sendMessage(message.toString());
                break;
            default: break;
        }*/

        return true;
    }

    private static String getOption(String option){
        if(test("create",option)) return "create";
        if(test("remove",option)) return "remove";
        if(test("join",option)) return "join";
        if(test("list",option)) return "list";
        if(test("modify",option)) return "modify";
        return null;
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
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<String>();
        if(args.length==1){
            if ("create".startsWith(args[0])) list.add("create");
            if ("remove".startsWith(args[0])) list.add("remove");
            if ("modify".startsWith(args[0])) list.add("modify");
            if ("join".startsWith(args[0])) list.add("join");
            if ("list".startsWith(args[0])) list.add("list");
        }else if(args.length==2){
            switch (args[0]){
                case "modify":
                case "remove":
                    //for (Rank rank : RankApi.getRanks()){
                        //if (rank.getName().startsWith(args[1])) list.add(rank.getName());
                    //}
                    break;
                case "join":
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player.getName().startsWith(args[1])) list.add(player.getName());
                    }
                    break;
                default: break;
            }
        }else if(args.length==3){
            switch (args[0]) {
                case "join":
                    //for (Rank rank : RankApi.getRanks()) {
                        //if (rank.getName().startsWith(args[1])) list.add(rank.getName());
                    //}
                    break;
                case "create":
                    if ("-perms".startsWith(args[2])) list.add("-perms");
                    if ("-prefix".startsWith(args[2])) list.add("-prefix");
                    if ("-suffix".startsWith(args[2])) list.add("-suffix");
                    break;
                case "modify":
                    if ("name".startsWith(args[2])) list.add("name");
                    if ("prefix".startsWith(args[2])) list.add("prefix");
                    if ("suffix".startsWith(args[2])) list.add("suffix");
                    if ("perms".startsWith(args[2])) list.add("perms");
                    break;
                default: break;
            }
        }else if(args.length > 3 && args[0].equalsIgnoreCase("create")){
            if ("-perms".startsWith(args[3])) list.add("-perms");
            if ("-prefix".startsWith(args[3])) list.add("-prefix");
            if ("-suffix".startsWith(args[3])) list.add("-suffix");
        }else if(args.length > 3 && args[0].equalsIgnoreCase("modify")){
            switch (args[2]) {
                case "prefix":
                    //if(RankApi.getRank(args[1]) != null && Objects.requireNonNull(RankApi.getRank(args[1])).getPrefix().startsWith(args[3])) list.add(Objects.requireNonNull(RankApi.getRank(args[1])).getPrefix());
                    break;
                case "suffix":
                    //if(RankApi.getRank(args[1]) != null && Objects.requireNonNull(RankApi.getRank(args[1])).getSuffix().startsWith(args[3])) list.add(Objects.requireNonNull(RankApi.getRank(args[1])).getSuffix());
                    break;
                case "addperm":
                    //for(String perm : PermApi.getPermissions()){
                        //if (perm.startsWith(args[3])) list.add(perm);
                    //}
                    break;
                default: break;
            }
        }
        return list;
    }
}
